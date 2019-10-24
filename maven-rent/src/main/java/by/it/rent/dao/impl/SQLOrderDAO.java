package by.it.rent.dao.impl;

import by.it.rent.dao.DAOException;
import by.it.rent.dao.OrderDAO;
import by.it.rent.dao.PoolConnection;


import by.it.rent.bean.Order;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.it.rent.dao.PoolConnection.getInstance;

public class SQLOrderDAO implements OrderDAO {
	Logger log= LogManager.getLogger(SQLOrderDAO.class.getName());
    private static PoolConnection pc;
    private static final String INSERT_ORDER = "INSERT INTO car_order VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ORDER_BY_ID_TOTAL = "SELECT total FROM car_order WHERE id_order=?";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM car_order WHERE id_order=?";
    private static final String SELECT_ORDERS_BY_ID_USER = "SELECT * FROM car_order LEFT JOIN car on car_order.id_car=car.id_car LEFT JOIN damage on car_order.id_order=damage.id_order where id_users=?";
    private static final String SELECT_ALL_ORDERS_DONE = "SELECT * FROM car_order LEFT JOIN orderstatus ON car_order.id_order=orderstatus.id_order WHERE car_order.date_return<current_date()";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM car_order WHERE NOT car_order.date_return<current_date()";
    private static final String SELECT_ORDERS_LIMIT = "SELECT * FROM car_order LIMIT ?,?";
    private static final String SELECT_CAR_BY_IDCAR_PRICE = "SELECT price FROM car WHERE id_car=?";
    private static final String UPDATE_ORDER_DAYS = "UPDATE car_order SET real_days=?";
    private static final String UPDATE_ORDER_TOTAL = "UPDATE car_order SET total=?";
    private static final String UPDATE_STATUS = "UPDATE orderstatus SET status=? WHERE id_order=?";
    private static final String SELECT_DAMAGE_BY_IDORDER_SUM = "SELECT sum FROM damage WHERE id_order=?";

    static {
        try {
            pc = getInstance();
        } catch (RuntimeException e) {
            throw new RuntimeException (e);
        }
    }

    @Override
    public int order(Order newOrder, int idRefusal) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        ResultSet result=null;
        int idOrder;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_CAR_BY_IDCAR_PRICE);
            prst.setInt(1,  newOrder.getIdCar());
            result = prst.executeQuery();
            result.next();
            double price = result.getDouble("price");
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    throw new DAOException (e);
                }
            }
            connection.setAutoCommit(false);
            prst = connection.prepareStatement(INSERT_ORDER);
            prst.setInt(1, newOrder.getIdCar());
            String dateRent=newOrder.getDateRent();
            String dateReturn=newOrder.getDateReturn();
            prst.setString(2, dateRent);
            int days=countDays (dateRent, dateReturn);
            prst.setInt(3, days);
            prst.setString(4,dateReturn);
            prst.setInt(5, days);
            double total=price*days;
            prst.setDouble(6, total);
            prst.setInt(7, idRefusal );
            prst.setInt(8, newOrder.getIdUsers());
            prst.executeUpdate();
            prst = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            result = prst.executeQuery();
            result.next();
            idOrder = result.getInt(1);
            connection.commit();
        } catch (SQLException | InterruptedException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException (ex);
            }
            throw new DAOException("ERROR: create order", e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return idOrder;
    }

    @Override
    public double bill(int idOrder, int realDays) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        double bill = 0;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ORDER_BY_ID_TOTAL);
            prst.setInt(1, idOrder);
            ResultSet result = prst.executeQuery();
            result.next();
            int idCar = result.getInt("id_car");
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
            prst = connection.prepareStatement(SELECT_CAR_BY_IDCAR_PRICE);
            prst.setInt(1, idCar);
            ResultSet res = prst.executeQuery();
            result.next();
            double price = res.getDouble("price");
            bill = (price * realDays) + damage(idOrder);
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	throw new DAOException (e);
                }
            }
            prst=connection.prepareStatement(UPDATE_ORDER_DAYS);
            prst.setInt(1,realDays);
            prst.executeUpdate();
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	throw new DAOException (e);
                }
            }
            prst=connection.prepareStatement(UPDATE_ORDER_TOTAL);
            prst.setDouble(1,bill);
            prst.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new DAOException("ERROR: order bill", e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return bill;
    }

    @Override
    public double damage(int idOrder) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        double sum = 0;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_DAMAGE_BY_IDORDER_SUM);
            prst.setInt(1, idOrder);
            ResultSet result = prst.executeQuery();
            if(result.next()){
            sum = result.getDouble("sum");}
        } catch (SQLException | InterruptedException e) {
            throw new DAOException("ERROR: add damage", e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return sum;
    }

    @Override
    public void addRefusal(int idRefusal, String[] cause) {

    }
    
    private static int countDays (String dateRent, String dataReturn){
        LocalDate start=LocalDate.parse(dateRent);
        LocalDate finish=LocalDate.parse(dataReturn);
        Period period=Period.between(start,finish);
        return period.getDays();
    }

	@Override
	public Order findOrder(int idOrder) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        ResultSet result = null;
        Order order = new Order();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ORDER_BY_ID);
            prst.setInt(1, idOrder);
            result = prst.executeQuery();
            result.next();
            order.setIdOrder(idOrder);
            order.setIdCar(result.getInt(2));
            order.setDateRent(result.getString(3));
            order.setDays(result.getInt(4));
            order.setDateReturn(result.getString(5));
            order.setRealDays (result.getInt(6));
            order.setTotal (result.getDouble(7));
            order.setIdRefusal(result.getInt(8));
            order.setIdUsers (result.getInt(9));
        } catch (SQLException e) {
            throw new DAOException("ERROR: find order by ID", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
		return order;
	}

	@Override
	public List<Order> showAllOrders() throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        List <Order> list = new ArrayList <Order>();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ALL_ORDERS_DONE);
            ResultSet result = prst.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getInt(1));
                order.setIdCar(result.getInt(2));
                order.setDateRent(result.getString(3));
                order.setDays(result.getInt(4));
                order.setDateReturn(result.getString(5));
                order.setRealDays (result.getInt(6));
                order.setTotal (result.getDouble(7));
                order.setIdRefusal(result.getInt(8));
                order.setIdUsers (result.getInt(9));
                if (result.getString(11)==null) {
                	order.setStatus("undone");
                }
                list.add(order);
            }
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	throw new DAOException (e);
                }
            }
            prst = connection.prepareStatement(SELECT_ALL_ORDERS);
            result = prst.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getInt(1));
                order.setIdCar(result.getInt(2));
                order.setDateRent(result.getString(3));
                order.setDays(result.getInt(4));
                order.setDateReturn(result.getString(5));
                order.setRealDays (result.getInt(6));
                order.setTotal (result.getDouble(7));
                order.setIdRefusal(result.getInt(8));
                order.setIdUsers (result.getInt(9));
                list.add(order);}
        } catch (SQLException e) {
            throw new DAOException("ERROR: show all orders", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return list;
	}
	
	
	public List<Order> showAllOrders(int position, int count) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        List <Order> list = new ArrayList <Order>();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ORDERS_LIMIT);
            prst.setInt(1,  position);
            prst.setInt(2,  count);
            ResultSet result = prst.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getInt(1));
                order.setIdCar(result.getInt(2));
                order.setDateRent(result.getString(3));
                order.setDays(result.getInt(4));
                order.setDateReturn(result.getString(5));
                order.setRealDays (result.getInt(6));
                order.setTotal (result.getDouble(7));
                order.setIdRefusal(result.getInt(8));
                order.setIdUsers (result.getInt(9));
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR: find orders limit", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return list;
	}

	@Override
	public List<Order> showUserOrders(int idUser) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        List <Order> list = new ArrayList <Order>();
        ResultSet result = null;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ORDERS_BY_ID_USER);
            prst.setInt(1, idUser );
            result = prst.executeQuery();
            result.next();
            while (result.next()) {
                Order order = new Order();
                order.setIdOrder(result.getInt(1));
                order.setIdCar(result.getInt(2));
                order.setDateRent(result.getString(3));
                order.setDays(result.getInt(4));
                order.setDateReturn(result.getString(5));
                order.setRealDays (result.getInt(6));
                order.setTotal (result.getDouble(7));
                order.setIdRefusal(result.getInt(8));
                order.setIdUsers (result.getInt(9));
                order.setMarkCar(result.getString(11) + " "+ result.getString(12));
                order.setDamage(result.getString(20));
                order.setDamageSum(result.getDouble(21));
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR: find user's orders", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
        return list;
	}

	@Override
	public void changeStatus(int idOrder) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        try {
            connection = pc.take();
            prst=connection.prepareStatement(UPDATE_STATUS);
            prst.setString(1,"done");
            prst.setInt(2,idOrder);
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERROR: change order status", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLOrderDAO");
                }
            }
            pc.release(connection);
        }
	}
}
