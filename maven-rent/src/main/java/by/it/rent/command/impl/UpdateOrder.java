package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.Order;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;


public class UpdateOrder implements Command {
	Logger log= LogManager.getLogger(UpdateOrder.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		Order order = new Order();
			try {
				order = orderDAO.findOrder(idOrder);
				request.setAttribute(RequestParameterName.ORDER, order);	
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.EDIT_ORDER_PAGE);
				dispatcher.forward(request, response);
			} catch (DAOException e) {
				log.debug("This is a DEBUG-message in EditUser");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
		
	}

}
