package by.it.rent.service.impl;

import java.util.List;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.UserDAO;
import by.it.rent.service.ServiceException;
import by.it.rent.service.UserService;
import by.it.rent.service.validator.UserValidator;

public class ImplUserService implements UserService {

	private static final UserValidator validator = UserValidator.getInstance();

	@Override
	public User authorization(String login, String password) throws ServiceException {
		User user = null;
		if (!validator.check(login, password)) {
			user = null;
		}
		else {
			UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
			try {
				user = userDAO.authorization(login, password);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return user;
	}

	@Override
	public User registration(NewUser newUser) throws ServiceException {
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		String login=newUser.getLogin();
		User user = new User();
		try {
			if (userDAO.checkLogin(login)) {
				user=null;
			} else {
				int idUser = userDAO.registration(newUser);
				user = userDAO.showUserById(idUser);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public List<User> showAllUsers() throws ServiceException {
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		List<User> userList;
		try {
			userList = userDAO.showAllUsers();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return userList;
	}

	@Override
	public void addDetails(String passport, String driverLicense, int idUser) throws ServiceException {
		UserDAO userDAO = DAOProvider.INSTANCE.getUserDAO();
		try {
			userDAO.addDetails(passport, driverLicense, idUser);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
