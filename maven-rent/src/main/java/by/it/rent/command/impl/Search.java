package by.it.rent.command.impl;

import java.io.IOException;
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

public class Search implements Command{
	Logger log = LogManager.getLogger(Search.class.getName());
	private static final String NOT_FOUND = "message.not.found";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String search;
			List <Car> list;
			String goToPage;
		
			search = request.getParameter(RequestParameterName.SEARCH);
			String [] pattern = search.split(" ");
		
			for (int i=0; i < pattern.length; i++) {
				StringBuilder sb = new StringBuilder ();
				sb.append('%');
				sb.append(pattern [i]);
				sb.append('%');
				pattern [i] = sb.toString();
			}
		
			CarService carService = ServiceProvider.getInstance().getCarService();
			try {
				list = carService.findCar(pattern);
				if (list.isEmpty()) {
					request.setAttribute(RequestParameterName.MESSAGE, NOT_FOUND);
				}
				request.setAttribute(RequestParameterName.CARS, list);
				User user = (User) request.getSession(false).getAttribute(RequestParameterName.USER);
				if(user!=null && user.getIdRole()==1) {
					goToPage=JSPPages.AD_CARS_PAGE;
				}
				else {goToPage=JSPPages.USER_AUTH_PAGE;}
			} catch (ServiceException e) {
				log.debug("This is a DEBUG-message in Search");
				goToPage = JSPPages.ERROR_PAGE;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
			dispatcher.forward(request, response);		
		} else
			request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
