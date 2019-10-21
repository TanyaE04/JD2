package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.PoolConnection;
import by.it.rent.dao.UserDAO;

public class EditUser implements Command {
	Logger log= LogManager.getLogger(PoolConnection.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		User user = new User();
			try {
				user = userDAO.showUserById(idUser);
				request.setAttribute("user", user);	
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.EDIT_USER_PAGE);
				dispatcher.forward(request, response);
			} catch (DAOException e) {
				log.debug("This is a DEBUG-message");
			}
	}

}
