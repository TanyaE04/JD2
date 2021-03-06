package by.it.rent.command.impl;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

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
import by.it.rent.service.OrderService;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;


public class ShowOrder implements Command {
	Logger log = LogManager.getLogger(ShowOrder.class.getName());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		OrderService orderService=ServiceProvider.getInstance().getOrderService();
		
		String pos = request.getParameter(RequestParameterName.PAGE_POSITION);
		List <Order> orderList;
		Order order = new Order ();
		try {
			orderList = orderService.showAllOrders();
			int pages = (int) Math.ceil((orderList.size()/10.0));
			List <Integer> countOfPages = new ArrayList <Integer> (pages);
			for (int i=0; i<pages; i++) {
				countOfPages.add(i);
			}
			request.setAttribute(RequestParameterName.COUNTS, countOfPages);
			if (pos!=null) {
				int position = Integer.parseInt(pos);
				int count = 10;
				int end = position*count+count;
				if (end<orderList.size()-1) {
					orderList = orderList.subList(position*count, end);
				} else {
					order = orderList.get(orderList.size()-1);
					orderList = orderList.subList(position*count, orderList.size()-1);
					orderList.add(order);
				}
			}
			
			request.setAttribute(RequestParameterName.ORDERS, orderList);
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.AD_ORDERS_PAGE);
			dispatcher.forward(request, response);
		}catch(ServiceException e) {
			log.debug("This is a DEBUG-message in ShowOrder");
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPages.ERROR_PAGE);
			dispatcher.forward(request, response);			
		}
	}
}
