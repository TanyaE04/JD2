 package by.it.rent.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import by.it.rent.service.UserService;

public class AuthorizationCommand implements Command {
	Logger log = LogManager.getLogger(AuthorizationCommand.class.getName());
	private static final String INCORRECT_LOGIN = "error.incorrect.login";
	private static final String USER_BLOCK = "error.user.blocked";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String login;
		String password;
		
		login = request.getParameter(RequestParameterName.LOGIN);
		password = request.getParameter(RequestParameterName.PASSWORD);
		
		UserService userService = ServiceProvider.getInstance().getUserService();
		CarService carService=ServiceProvider.getInstance().getCarService();
		
		List <Car> list = new ArrayList <Car> ();

		
		User user;
		String goToPage; 
		try {
			user = userService.authorization(login, password);
			if(user != null) {
				if (user.getStatus()!=null) {
					request.setAttribute(RequestParameterName.ERROR, USER_BLOCK);
					goToPage = JSPPages.INDEX_PAGE;
				}
				else {
				HttpSession session = request.getSession(true);
				session.setAttribute(RequestParameterName.USER, user);
				if (user.getIdRole()==1) {
					goToPage = JSPPages.ADMIN_AUTH_PAGE;
					
				} else {
					list = carService.showAllCars();
					request.setAttribute(RequestParameterName.CARS, list);
					goToPage = JSPPages.USER_AUTH_PAGE;}
				}
			} else {
				request.setAttribute(RequestParameterName.ERROR, INCORRECT_LOGIN);
				goToPage = JSPPages.INDEX_PAGE;
			}
			request.setAttribute(RequestParameterName.USER, user);	
		}catch(ServiceException e) {
			log.debug("This is a DEBUG-message in AuthorizationCommand");
			goToPage = JSPPages.ERROR_PAGE;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
		dispatcher.forward(request, response);
	}

}
