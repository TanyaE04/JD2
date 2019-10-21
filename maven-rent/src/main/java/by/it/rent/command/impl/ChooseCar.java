package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.Car;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.PoolConnection;
import by.it.rent.service.CarService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;


public class ChooseCar implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Logger log= LogManager.getLogger(PoolConnection.class.getName());
		int idCar;
		idCar=Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
		
		CarService carService = ServiceProvider.getInstance().getCarService();
		Car car = new Car ();
		try {
			car = carService.findCarByID(idCar);
			
			request.setAttribute("infocar", car);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ORDER_PAGE);
			dispatcher.forward(request, response);
		}catch(ServiceException e) {
			log.debug("This is a DEBUG-message");
			
		}
		
	}

}
