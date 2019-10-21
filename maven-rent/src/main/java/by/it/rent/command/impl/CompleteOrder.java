package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.command.Command;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;
import by.it.rent.dao.PoolConnection;

public class CompleteOrder implements Command{
	Logger log= LogManager.getLogger(PoolConnection.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
		
		OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
		
		try {
			orderDAO.changeStatus(idOrder);;
			response.sendRedirect("controller?command=showorder");
		} catch (DAOException e) {
			log.debug("This is a DEBUG-message");
		}
	}

}
