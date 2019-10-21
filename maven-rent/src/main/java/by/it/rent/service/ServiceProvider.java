package by.it.rent.service;

import by.it.rent.service.impl.ImplCarService;
import by.it.rent.service.impl.ImplOrderService;
import by.it.rent.service.impl.ImplUserService;

public class ServiceProvider {
private static final ServiceProvider instance = new ServiceProvider();
	
	private UserService userService = new ImplUserService ();
	private CarService carService = new ImplCarService ();
	private OrderService orderService = new ImplOrderService ();
	
	public static ServiceProvider getInstance() {
		return instance;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public CarService getCarService() {
		return carService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

}
