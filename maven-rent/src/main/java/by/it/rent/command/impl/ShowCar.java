package by.it.rent.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.Car;
import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;


public class ShowCar implements Command {
	Logger log = LogManager.getLogger(ShowCar.class.getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		CarService carService=ServiceProvider.getInstance().getCarService();
		
		List <Car> list;
		String goToPage;
		User user;
		
		try {
			list = carService.showAllCars();
			request.setAttribute(RequestParameterName.CARS, list);
			user = (User) request.getSession(false).getAttribute(RequestParameterName.USER);
			if(user!=null && user.getIdRole()==1) {
				goToPage=JSPPages.AD_CARS_PAGE;
			}
			else {goToPage=JSPPages.USER_AUTH_PAGE;}
		}catch(ServiceException e) {
			log.debug("This is a DEBUG-message in ShowCar");
			goToPage = JSPPages.ERROR_PAGE;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
		
		
	}

}
