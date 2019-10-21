package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.JSPPages;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.service.ServiceException;
import by.it.rent.service.ServiceProvider;
import by.it.rent.service.UserService;

public class RegistrationCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name;
		String surname;
		String login;
		String password;
		String phone;
		String mail;
		String address;

		name = request.getParameter(RequestParameterName.NAME);
		surname = request.getParameter(RequestParameterName.SURNAME);
		login = request.getParameter(RequestParameterName.LOGIN);
		password = request.getParameter(RequestParameterName.PASSWORD);
		phone = request.getParameter(RequestParameterName.PHONE);
		mail = request.getParameter(RequestParameterName.MAIL);
		address = request.getParameter(RequestParameterName.ADDRESS);

		UserService userService = ServiceProvider.getInstance().getUserService();

		NewUser newUser = new NewUser();
		newUser.setName(name);
		newUser.setSurname(surname);
		newUser.setLogin(login);
		newUser.setPassword(password);
		newUser.setAddress(address);
		newUser.setMail(mail);
		newUser.setPhone(phone);

		User user;
		String goToPage;
		try {

			user = userService.registration(newUser);
			if (user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				goToPage = JSPPages.USER_AUTH_PAGE;
			} else {
				request.setAttribute("errorLoginExist", "����� ����� ��� ����������");
				goToPage = JSPPages.REGISTRATION_PAGE;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(goToPage);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			System.err.println(e);// log

		}

	}

}
