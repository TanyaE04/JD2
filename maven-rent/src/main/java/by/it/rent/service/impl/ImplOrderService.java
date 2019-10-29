 package by.it.rent.service.impl;

import java.util.List;

import by.it.rent.bean.Order;
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

import by.it.rent.service.OrderService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.validator.OrderValidator;

public class ImplOrderService implements OrderService {
	private static final String RENT = "арендован";
	private static final OrderValidator validator = OrderValidator.getInstance();
	
	@Override
	public Order orderCar(Order newOrder, int idRefusal) throws ServiceException {
		Order order = new Order ();
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		CarDAO carDAO = DAOProvider.INSTANCE.getCarDAO();
		int idCar = newOrder.getIdCar();
		String dateRent = newOrder.getDateRent();
		try {
			String status = carDAO.findCarByID(idCar).getStatus();
			System.out.println(status);
			if (validator.check(status, dateRent)) {
				int idOrder = orderDAO.order(newOrder, idRefusal);
				order = orderDAO.findOrder(idOrder);
				status = RENT + " до " + order.getDateReturn();
				carDAO.changeStatus(idCar, status);
			} else {
				order = null;
			}
		} catch (DAOException e) {
			throw new ServiceException (e);
		}
		return order;
	}

	@Override
	public double bill(int idOrder, int realDays) throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double damage(int idOrder) throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addRefusal(int idRefusal, String[] cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> showAllOrders() throws ServiceException {
		OrderDAO orderDAO =DAOProvider.INSTANCE.getOrderDAO();
		List <Order> orderList;
		try {
			orderList = orderDAO.showAllOrders();
		} catch (DAOException e) {
			throw new ServiceException(e);			
		}
		return orderList;
	}
	
	public List<Order> showAllOrders(int position, int count) throws ServiceException {
		OrderDAO orderDAO =DAOProvider.INSTANCE.getOrderDAO();
		List <Order> orderList;
		try {
			orderList = orderDAO.showAllOrders(position, count);
		} catch (DAOException e) {
			throw new ServiceException(e);			
		}
		return orderList;
	}

}
