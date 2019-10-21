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

import static by.it.rent.dao.PoolConnection.getInstance;

public class SQLCarDAO implements CarDAO {
    private static PoolConnection pc;
    private static final String INSERT_CAR ="INSERT INTO car VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_CAR_BY_SEARCH= "SELECT * FROM car WHERE (concat (brand, model, year, gearbox, color, status)  like ? and  concat (brand, model, year, gearbox, color, status)  like ?);";
    private static final String SELECT_CAR_BY_ID= "SELECT * FROM car WHERE id_car=?";
    private static final String SELECT_ALL_CARS= "SELECT * FROM car";
    //private static final String SELECT_CLASS_BY_ID ="SELECT class FROM class WHERE id_class=?";
    private static final String UPDATE_CAR_STATUS="UPDATE car SET status=? WHERE id_car=?";

    static {
        try {
            pc = getInstance();
        } catch (RuntimeException e) {
            e.printStackTrace();
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
                ex.printStackTrace();
            }
            throw new DAOException("Ошибка при добавлении авто", e);
        } catch (InterruptedException e) {
            System.err.println();
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    System.err.println(e);
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
            throw new DAOException("Ошибка при изменеии статуса", e);
        } catch (InterruptedException e) {
            System.err.println();
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    System.err.println(e);
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
            throw new DAOException("ошибка при поиске авто по марке", e);
        } catch (InterruptedException e) {
            System.err.println();
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    System.err.println(e);
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
            throw new DAOException("ошибка при поиске авто", e);
        } catch (InterruptedException e) {
            System.err.println();
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    System.err.println(e);
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
            throw new DAOException("ошибка при поиске авто по id", e);
        } catch (InterruptedException e) {
            System.err.println();
        } finally {
            if (prst != null) {
                try {
                    prst.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
            pc.release(connection);
        }
        return car;
    }
	
}
