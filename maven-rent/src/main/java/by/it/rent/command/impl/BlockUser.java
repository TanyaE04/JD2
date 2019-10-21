package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.command.Command;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.UserDAO;

public class BlockUser implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
		String statusNow = request.getParameter(RequestParameterName.USER_STATUS);
		String status = null;
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		
		try {
			switch (statusNow) {
			case "block":
				status=null;
				break;
			case "null":
				status="block";
				break;
			default:
	            break;
			}
			userDAO.changeStatus(idUser, status);
			response.sendRedirect("controller?command=showuser");
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
	}

}