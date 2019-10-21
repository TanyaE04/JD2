package by.it.rent.service;

import java.util.List;

import by.it.rent.bean.Car;


public interface CarService {
	void  addCar (Car car) throws ServiceException;
    void changeStatus(int idCar, String status) throws ServiceException;
    List<Car> findCar (String ...pattern) throws ServiceException; 
    Car findCarByID (int idCar) throws ServiceException; 
    List<Car> showAllCars() throws ServiceException; 
}
