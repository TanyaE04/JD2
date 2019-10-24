package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;

public class LogOut implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession(false).invalidate();
		request.getRequestDispatcher(JSPPages.INDEX_PAGE).forward(request, response);
	}

}
