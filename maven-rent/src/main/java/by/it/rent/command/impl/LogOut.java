package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;

public class LogOut implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session != null) {
		session.invalidate();}
		request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
