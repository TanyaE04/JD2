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
import by.it.rent.dao.CarDAO;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;
import by.it.rent.dao.UserDAO;


public class CompleteOrder implements Command{
	Logger log= LogManager.getLogger(CompleteOrder.class.getName());
	private static final String FREE = "в наличии";
	private static final String DONE = "done";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {	
			int idOrder = Integer.parseInt(request.getParameter(RequestParameterName.ID_ORDER));
			OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();
			CarDAO carDAO = DAOProvider.INSTANCE.getCarDAO();
			UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
			try {
				int idUser = orderDAO.findOrder(idOrder).getIdUsers();
				orderDAO.changeStatus(idOrder, DONE);
				double damage = orderDAO.damage(idOrder);
				if (damage==0) {
					carDAO.changeStatus(orderDAO.findOrder(idOrder).getIdCar(), FREE);}
				userDAO.changeDebt(idUser, orderDAO.bill(idOrder, damage));
				response.sendRedirect("controller?command=showorder");
			} catch (DAOException e) {
				log.debug("This is a DEBUG-message in CompleteOrder");
				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
				dispatcher.forward(request, response);
			}
		} else
			request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
