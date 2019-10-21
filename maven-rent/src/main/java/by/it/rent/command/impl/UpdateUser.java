package by.it.rent.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.it.rent.bean.User;
import by.it.rent.command.Command;
import by.it.rent.controller.RequestParameterName;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.UserDAO;

public class UpdateUser implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name;
		String surname;
		String phone;
		String mail;
		String address;
		String passport;
		String driverLicense;
		int idUser;
		
		idUser = Integer.parseInt(request.getParameter(RequestParameterName.ID_USER));
		name = request.getParameter(RequestParameterName.NAME);
		surname = request.getParameter(RequestParameterName.SURNAME);
		phone =request.getParameter(RequestParameterName.PHONE);
		mail = request.getParameter(RequestParameterName.MAIL);
		address = request.getParameter(RequestParameterName.ADDRESS);
		passport = request.getParameter(RequestParameterName.PASSPORT);
		driverLicense = request.getParameter(RequestParameterName.DRIVER_LICENSE);
		String page = request.getParameter("page");
		
		User user = new User();
		user.setIdUser(idUser);
		user.setName(name);
		user.setSurname(surname);
		user.setPhone(phone);
		user.setMail(mail);
		user.setAddress(address);
		user.setPassport(passport);
		user.setDriverLicense(driverLicense);
		
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		
		try {
			userDAO.updateUser(user);
			
			
			request.getSession(false).setAttribute("editMessage", "������� ������� ���������");
			
			if (page!=null) {
				response.sendRedirect("controller?command=showuser");
			} else {
			response.sendRedirect("controller?command=userdata");}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
	}

}
