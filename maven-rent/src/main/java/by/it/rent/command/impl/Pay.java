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
import by.it.rent.dao.UserDAO;

public class Pay implements Command {
	Logger log= LogManager.getLogger(CompleteOrder.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		try {
			userDAO.changeDebt(idUser, 0);
			response.sendRedirect("controller?command=orderdata");
		} catch (DAOException e) {
			log.debug("This is a DEBUG-message in CompleteOrder");
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
			dispatcher.forward(request, response);
		}
	}
}
