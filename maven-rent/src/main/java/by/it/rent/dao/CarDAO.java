package by.it.rent.dao;


import java.util.List;

import by.it.rent.bean.Car;

public interface CarDAO {
    void  addCar (Car car) throws DAOException;
    void changeStatus(int idCar, String status) throws DAOException;
    Car findCarByID (int idCar) throws DAOException;
    List<Car> findCar (String...search) throws DAOException;
    List<Car> showAllCars() throws DAOException; 
    void changePrice (int idCar, double price) throws DAOException;
}
