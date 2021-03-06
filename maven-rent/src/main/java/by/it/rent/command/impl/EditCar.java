package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

public class EditCar implements Command{
	Logger log = LogManager.getLogger(EditCar.class.getName());
	private static final String EDIT_MESSAGE = "message.edit.save";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			int idCar;
			double price;
			String status;
		
			idCar=Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
			price=Double.parseDouble(request.getParameter(RequestParameterName.PRICE));
			status=request.getParameter(RequestParameterName.STATUS);
			OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
			CarDAO carDAO = DAOProvider.INSTANCE.getCarDAO();
			try {
				if (price!=0) {
					carDAO.changePrice(idCar, price);
				}
				if (status!=null) {
					carDAO.changeStatus(idCar, status);
					orderDAO.changeRefusal(idCar, 1);
				}
				session.setAttribute(RequestParameterName.MESSAGE, EDIT_MESSAGE);
				response.sendRedirect("controller?command=showcar");
			} catch(DAOException e) {
				log.debug("This is a DEBUG-message in EditOrder", e);
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
		} else
		request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
