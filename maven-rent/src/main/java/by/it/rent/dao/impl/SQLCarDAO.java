package by.it.rent.dao.impl;

import by.it.rent.dao.CarDAO; 
import by.it.rent.dao.DAOException;
import by.it.rent.dao.PoolConnection;

import by.it.rent.bean.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.it.rent.dao.PoolConnection.getInstance;

public class SQLCarDAO implements CarDAO {
	Logger log= LogManager.getLogger(SQLCarDAO.class.getName());
    private static PoolConnection pc;
    private static final String INSERT_CAR ="INSERT INTO car VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_CAR_BY_SEARCH= "SELECT * FROM car WHERE (concat (brand, model, year, gearbox, color, status)  like ? and  concat (brand, model, year, gearbox, color, status)  like ?);";
    private static final String SELECT_CAR_BY_ID= "SELECT * FROM car WHERE id_car=?";
    private static final String SELECT_ALL_CARS= "SELECT * FROM car";
    private static final String UPDATE_CAR_STATUS="UPDATE car SET status=? WHERE id_car=?";
    private static final String UPDATE_CAR_PRICE="UPDATE car SET price=? WHERE id_car=?";

    static {
        try {
            pc = getInstance();
        } catch (RuntimeException e) {
            throw new RuntimeException (e);
        }
    }

    @Override
    public void addCar(Car car) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(INSERT_CAR);
            connection.setAutoCommit(false);
            prst.setString(1, car.getBrand());
            prst.setString(2, car.getModel());
            prst.setInt(3, car.getYear());
            prst.setString(4, car.getGearbox());
            prst.setString(5, car.getColor());
            prst.setDouble(6, car.getPrice());
            prst.setString(7, car.getStatus());
            prst.setInt(8, car.getIdClass());
            prst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DAOException (ex);
            }
            throw new DAOException("ERROR: adding car", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
    }

    @Override
    public void changeStatus(int idCar, String status) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(UPDATE_CAR_STATUS);
            prst.setString(1, status);
            prst.setInt(2,idCar);
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERROR: change car's status", e);
        } catch (InterruptedException e) {
        	throw new DAOException(e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
    }

    @Override
    public List<Car> findCar(String...pattern) throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        List <Car> list = new ArrayList <Car>();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_CAR_BY_SEARCH);
            prst.setString(1, pattern[0]);
            if (pattern.length>1) {
            	prst.setString(2, pattern[1]);
            } else {
            	prst.setString(2, "%%");
            }
            ResultSet result = prst.executeQuery();
            while (result.next()) {
                Car car = new Car();
                car.setIdCar(result.getInt(1));
                car.setBrand(result.getString(2));
                car.setModel(result.getString(3));
                car.setYear(result.getInt(4));
                car.setGearbox(result.getString(5));
                car.setColor(result.getString(6));
                car.setPrice(result.getDouble(7));
                car.setStatus(result.getString(8));
                int idClass=result.getInt(9);
                car.setIdClass(idClass);
                list.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR: find car by pattern", e);
        } catch (InterruptedException e) {
        	throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
        return list;
    }
    
    @Override
    public List<Car> showAllCars() throws DAOException {
        Connection connection = null;
        PreparedStatement prst = null;
        List <Car> list = new ArrayList <Car>();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_ALL_CARS);
            ResultSet result = prst.executeQuery();
            while (result.next()) {
                Car car = new Car();
                car.setIdCar(result.getInt(1));
                car.setBrand(result.getString(2));
                car.setModel(result.getString(3));
                car.setYear(result.getInt(4));
                car.setGearbox(result.getString(5));
                car.setColor(result.getString(6));
                car.setPrice(result.getDouble(7));
                car.setStatus(result.getString(8));
                int idClass=result.getInt(9);
                car.setIdClass(idClass);
                list.add(car);
            }
        } catch (SQLException e) {
            throw new DAOException("ERROR: show all cars", e);
        } catch (InterruptedException e) {
            throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
        return list;
    }

	@Override
	public Car findCarByID(int idCar) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        Car car = new Car();
        try {
            connection = pc.take();
            prst = connection.prepareStatement(SELECT_CAR_BY_ID);
            prst.setInt(1, idCar);
            ResultSet result = prst.executeQuery();
            result.next();
            car.setIdCar(result.getInt(1));
            car.setBrand(result.getString(2));
            car.setModel(result.getString(3));
            car.setYear(result.getInt(4));
            car.setGearbox(result.getString(5));
            car.setColor(result.getString(6));
            car.setPrice(result.getDouble(7));
            car.setStatus(result.getString(8));
            car.setIdClass(result.getInt(9));
               
        } catch (SQLException e) {
            throw new DAOException("ERROR: find car by ID", e);
        } catch (InterruptedException e) {
            throw new DAOException (e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
        return car;
    }

	@Override
	public void changePrice(int idCar, double price) throws DAOException {
		Connection connection = null;
        PreparedStatement prst = null;
        try {
            connection = pc.take();
            prst = connection.prepareStatement(UPDATE_CAR_STATUS);
            prst.setDouble(1, price);
            prst.setInt(2,idCar);
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("ERROR: change car's price", e);
        } catch (InterruptedException e) {
        	throw new DAOException(e);
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                	log.debug("This is a DEBUG-message in SQLCarDAO");
                }
            }
            pc.release(connection);
        }
		
	}

	
	
}
