package by.it.rent.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.bean.Car;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;

public class FilterSearch implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String volkswagen = request.getParameter("volkswagen");
		String audi = request.getParameter("audi");
		String bmw = request.getParameter("bmw");
		String skoda = request.getParameter("skoda");
		String toyota = request.getParameter("toyota");
		String gearbox = request.getParameter("gearbox");
		
		CarService carService = ServiceProvider.getInstance().getCarService();
		List <Car> list = new ArrayList <Car> ();
		
		String [] pattern; 
		String [] mark = {volkswagen, audi, bmw, skoda, toyota};
		List <String> marks = new ArrayList<>();
		for (String m:mark) {
			if (m!= null) marks.add(m);
		}
		
		boolean isGearbox = false;
		if (gearbox!=null) {
			gearbox = builder (gearbox);
			isGearbox = true;
		}
		
		if (marks.isEmpty()) {
			System.out.print("null");
			if (isGearbox) {
				pattern = new String [1];
				pattern [0] = gearbox;
				 try {
					 List <Car> listOne = carService.findCar(pattern);
					 list.addAll(listOne);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		} else {
			for (String m:marks) {
				m = builder (m);
				if (isGearbox) {
					pattern = new String [2];
					pattern [0] = m;
					pattern [1] = gearbox;
				} else {
					pattern = new String [1];
					pattern [0] = m;
				}
				 try {
					 List <Car> listOne = carService.findCar(pattern);
					 list.addAll(listOne);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		}
		if (list.isEmpty()) {
			request.getSession(false).setAttribute("messageNotFound", "�� ������ ������� ������ �� �������");
		}
		
		request.setAttribute("cars", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.USER_AUTH_PAGE);
		dispatcher.forward(request, response);
		
	}
	
	private String builder (String param) {
		StringBuilder sb = new StringBuilder ();
		sb.append('%');
		sb.append(param);
		sb.append('%');
		param = sb.toString();
		return param;
		
	}

}
