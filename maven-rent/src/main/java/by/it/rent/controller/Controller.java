package by.it.rent.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.command.Command;
import by.it.rent.command.CommandHelper;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final CommandHelper helper = CommandHelper.getInstance();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandName;
		Command command;
	
		if (request.getParameter(RequestParameterName.LOCAL) != null) {
			request.getSession(true).setAttribute("local", request.getParameter(RequestParameterName.LOCAL));
			String page = request.getParameter("page");
			response.sendRedirect(page);
			
		} else {
			commandName = request.getParameter(RequestParameterName.COMMAND_NAME);
			command = helper.getCommand(commandName);

			command.execute(request, response);
		}
	}

}
