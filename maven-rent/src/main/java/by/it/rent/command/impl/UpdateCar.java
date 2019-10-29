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
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;

public class UpdateCar implements Command {
	Logger log= LogManager.getLogger(UpdateCar.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idCar = Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
		CarDAO carDAO = DAOProvider.INSTANCE.getCarDAO();
		Car car = new Car();
			try {
				car = carDAO.findCarByID(idCar);
				request.setAttribute(RequestParameterName.CAR, car);	
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.EDIT_CAR_PAGE);
				dispatcher.forward(request, response);
			} catch (DAOException e) {
				log.debug("This is a DEBUG-message in EditUser");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
	}
}
