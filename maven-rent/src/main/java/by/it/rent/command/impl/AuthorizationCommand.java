 package by.it.rent.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
					request.setAttribute("error", "ѕользователь с таким логином и паролем удален или заблокирован");
					goToPage = JSPPages.INDEX_PAGE;
				}
				else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				if (user.getIdRole()==1) {
					goToPage = JSPPages.ADMIN_AUTH_PAGE;
					
				} else {
					list = carService.showAllCars();
					request.setAttribute("cars", list);
					goToPage = JSPPages.USER_AUTH_PAGE;}
				}
			} else {
				request.setAttribute("error", "Ќеверные логин или пароль");
				goToPage = JSPPages.INDEX_PAGE;
			}
			
			request.setAttribute("user", user);	
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
			dispatcher.forward(request, response);
			
		}catch(ServiceException e) {
			System.err.println(e);//log
			
		}
		
	}

}
