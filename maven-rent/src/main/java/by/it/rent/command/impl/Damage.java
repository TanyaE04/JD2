package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

public class Damage implements Command{
	Logger log = LogManager.getLogger(Damage.class.getName());
	private static final String EDIT_MESSAGE = "message.edit.save";
	private static final String REPAIR = "в ремонте";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idCar;
		int idOrder;
		String description;
		double sum;
		
		idCar=Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
		idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
		description = request.getParameter(RequestParameterName.DESCRIPTION);
		sum = Double.parseDouble(request.getParameter(RequestParameterName.SUM));
		
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		CarDAO carDAO = DAOProvider.INSTANCE.getCarDAO();
		try {
			orderDAO.addDamage(idOrder, description, sum);
			orderDAO.changeRefusal(idCar, 2);
			carDAO.changeStatus(idCar, REPAIR);
			request.getSession(false).setAttribute(RequestParameterName.MESSAGE, EDIT_MESSAGE);
			response.sendRedirect("controller?command=showorder");
		} catch(DAOException e) {
			log.debug("This is a DEBUG-message in EditOrder");
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
			dispatcher.forward(request, response);
		}
		
	}

}
