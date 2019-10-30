package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.Order;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

public class EditOrder implements Command{
	Logger log = LogManager.getLogger(EditOrder.class.getName());
	private static final String EDIT_MESSAGE = "message.edit.save";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String dateRent;
			String dateReturn;
			int idOrder;
			int idCar;
		
			dateRent = request.getParameter(RequestParameterName.DATE_RENT);
			dateReturn = request.getParameter(RequestParameterName.DATE_RETURN);
			idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
			idCar=Integer.parseInt(request.getParameter(RequestParameterName.ID_CAR));
		
			Order order = new Order ();
			order.setDateRent(dateRent);
			order.setDateReturn(dateReturn);
			order.setIdOrder(idOrder);
			order.setIdCar(idCar);
			OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
			try {
				orderDAO.updateOrder(order);
				session.setAttribute(RequestParameterName.MESSAGE, EDIT_MESSAGE);
				response.sendRedirect("controller?command=showorder");
			} catch(DAOException e) {
				log.debug("This is a DEBUG-message in EditOrder", e);
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
		} else
			request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
