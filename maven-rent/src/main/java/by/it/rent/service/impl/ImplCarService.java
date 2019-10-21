package by.it.rent.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.it.rent.bean.Car;
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;

public class ImplCarService implements CarService {

	@Override
	public void addCar(Car car) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStatus(int idCar, String status) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Car> findCar(String...pattern) throws ServiceException {
		CarDAO carDAO=DAOProvider.INSTANCE.getCarDAO();
		List <Car> list=new ArrayList<Car>();
		try {
			list=carDAO.findCar(pattern);
		} catch (DAOException e) {
			throw new ServiceException (e);
		}
		return list;
	}


	@Override
	public List<Car> showAllCars() throws ServiceException {
		CarDAO carDAO=DAOProvider.INSTANCE.getCarDAO();
		List <Car> list=new ArrayList<Car>();
		try {
			list=carDAO.showAllCars();
		} catch (DAOException e) {
			throw new ServiceException (e);
		}
		return list;
	}

	@Override
	public Car findCarByID(int idCar) throws ServiceException {
		CarDAO carDAO=DAOProvider.INSTANCE.getCarDAO();
		Car car;
		try {
			car=carDAO.findCarByID(idCar);
		} catch (DAOException e) {
			throw new ServiceException (e);
		}
		return car;
	}

}
