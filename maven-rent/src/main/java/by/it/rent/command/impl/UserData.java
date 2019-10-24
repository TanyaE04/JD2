package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.UserDAO;


public class UserData implements Command{
	Logger log = LogManager.getLogger(UserData.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		int idUser;
        HttpSession session = request.getSession(false);
        User u = (User) session.getAttribute(RequestParameterName.USER);
        idUser = u.getIdUser();
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		User user = new User();
			try {
				user = userDAO.showUserById(idUser);
				request.setAttribute(RequestParameterName.USER, user);	
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.USER_DATA_PAGE);
				dispatcher.forward(request, response);
			} catch (DAOException e) {
				log.debug("This is a DEBUG-message in UserData");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
			
	}

}
