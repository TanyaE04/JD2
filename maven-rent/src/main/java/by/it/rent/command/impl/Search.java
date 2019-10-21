package by.it.rent.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.bean.Car;
import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;

import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;

public class Search implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		 
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
				request.setAttribute("messageNotFound", "По Вашему запросу ничего не найдено");
			}
			request.setAttribute("cars", list);
			User user = (User) request.getSession(false).getAttribute("user");
			if(user!=null && user.getIdRole()==1) {
				goToPage=JSPPages.AD_CARS_PAGE;
			}
			else {goToPage=JSPPages.USER_AUTH_PAGE;}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
			dispatcher.forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}

}
