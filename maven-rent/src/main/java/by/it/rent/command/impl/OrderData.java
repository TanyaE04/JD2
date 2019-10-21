package by.it.rent.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.it.rent.bean.Order;
import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;

import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.OrderDAO;


public class OrderData implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		int idUser;
		// idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
		HttpSession session = request.getSession(false);
		if (session != null) {
			User u = (User) session.getAttribute("user");
			idUser = u.getIdUser();
			OrderDAO orderDAO = DAOProvider.INSTANCE.getOrderDAO();

			List<Order> orderList;

			try {
				orderList = orderDAO.showUserOrders(idUser);
				request.setAttribute("orders", orderList);

				RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ORDER_DATA_PAGE);
				dispatcher.forward(request, response);
			} catch (DAOException e) {

				e.printStackTrace();
			}
		} else
			request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);

	}

}
