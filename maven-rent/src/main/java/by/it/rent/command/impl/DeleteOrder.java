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
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

public class DeleteOrder implements Command{
	Logger log= LogManager.getLogger(CompleteOrder.class.getName());
	private static final String DELETED = "deleted";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
		String page = request.getParameter(RequestParameterName.PAGE);
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		try {
			orderDAO.changeStatus(idOrder, DELETED);
			response.sendRedirect(page);
		} catch (DAOException e) {
			log.debug("This is a DEBUG-message in CompleteOrder");
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
			dispatcher.forward(request, response);
		}
	}

}
