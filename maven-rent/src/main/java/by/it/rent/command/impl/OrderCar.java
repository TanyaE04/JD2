package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.bean.Car;
import by.it.rent.bean.Order;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.service.CarService;
import by.it.rent.service.OrderService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;
import by.it.rent.service.UserService;

public class OrderCar implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		 
		String dateRent;
		String dateReturn;
		String passport;
		String driverLicense;
		int idCar;
		int idUser;
		
		dateRent = request.getParameter(RequestParameterName.DATE_RENT);
		dateReturn = request.getParameter(RequestParameterName.DATE_RETURN);
		passport = request.getParameter(RequestParameterName.PASSPORT);
		driverLicense = request.getParameter(RequestParameterName.DRIVER_LICENSE);
		idCar=Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
		idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
				
		Order newOrder = new Order ();
		newOrder.setDateRent(dateRent);
		newOrder.setDateReturn(dateReturn);
		newOrder.setIdCar(idCar);
		newOrder.setIdUsers(idUser);
		
		Order order;
		OrderService orderService = ServiceProvider.getInstance().getOrderService();
		UserService userService = ServiceProvider.getInstance().getUserService();
		CarService carService = ServiceProvider.getInstance().getCarService();
		try {
			int idRefusal=1;		
			order=orderService.orderCar(newOrder, idRefusal);
			if (passport != null && driverLicense !=null) {
				userService.addDetails(passport, driverLicense, idUser);
			}
			if (order==null) {
				Car car = carService.findCarByID(newOrder.getIdCar());
				request.setAttribute ("infocar", car);
				request.setAttribute("messageAlreadyRent", "������ ���������� �� ��������� ���� ���������");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ORDER_PAGE);
				dispatcher.forward(request, response);
			} else {
				request.getSession(false).setAttribute("order", order);
				response.sendRedirect("controller?command=showcar");
			}
			
		} catch(ServiceException e) {
			System.err.println(e);//log
			
		}
		
		
	}

}