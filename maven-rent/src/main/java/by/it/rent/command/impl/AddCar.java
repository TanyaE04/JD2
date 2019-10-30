package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.Car;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;

public class AddCar implements Command {

	Logger log = LogManager.getLogger(RegistrationCommand.class.getName());
	private static final String ADD_CAR = "message.add.car";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String brand;
			String model;
			int year;
			String gearbox;
			String color;
			Double price;
			int idClass;

			brand = request.getParameter(RequestParameterName.BRAND);
			model = request.getParameter(RequestParameterName.MODEL);
			year = Integer.parseInt (request.getParameter(RequestParameterName.YEAR));
			gearbox = request.getParameter(RequestParameterName.GEARBOX);
			color = request.getParameter(RequestParameterName.COLOR);
			price = Double.parseDouble(request.getParameter(RequestParameterName.PRICE));
			idClass = Integer.parseInt(request.getParameter(RequestParameterName.CAR_ClASS));

			CarService carService = ServiceProvider.getInstance().getCarService();

			Car car = new Car ();
			car.setBrand(brand);
			car.setModel(model);
			car.setColor(color);
			car.setGearbox(gearbox);
			car.setIdClass(idClass);
			car.setPrice(price);
			car.setYear(year);

			try {
				carService.addCar(car);
				session.setAttribute(RequestParameterName.MESSAGE, ADD_CAR);
				response.sendRedirect("controller?command=showcar");
			} catch (ServiceException e) {
				log.debug("This is a DEBUG-message in RegistrationCommand");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
		} else
			request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}
}
