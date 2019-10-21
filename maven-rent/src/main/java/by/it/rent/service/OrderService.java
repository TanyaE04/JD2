package by.it.rent.service;

import java.util.List;

import by.it.rent.bean.Order;



public interface OrderService {
	
	 	Order  orderCar (Order newOrder, int idRefusal) throws ServiceException;
	    double bill (int idOrder, int realDays) throws ServiceException;
	    double damage (int idOrder) throws ServiceException;
	    void  addRefusal(int idRefusal, String [] cause);
	    List<Order> showAllOrders() throws ServiceException;
		List<Order> showAllOrders(int position, int count) throws ServiceException;

}
