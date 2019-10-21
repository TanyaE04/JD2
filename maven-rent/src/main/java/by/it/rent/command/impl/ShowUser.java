package by.it.rent.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;

import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;
import by.it.rent.service.UserService;

public class ShowUser implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		UserService userService=ServiceProvider.getInstance().getUserService();
		
		List <User> userList;

		try {
			userList = userService.showAllUsers();
			request.setAttribute("users", userList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.AD_USERS_PAGE);
			dispatcher.forward(request, response);
			
		}catch(ServiceException e) {
			System.err.println(e);//log
			
		}
		
	}

}
