package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.command.Command;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;

public class CompleteOrder implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
		
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		
		try {
			orderDAO.changeStatus(idOrder);;
			response.sendRedirect("controller?command=showorder");
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
