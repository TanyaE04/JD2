package by.it.rent.service;

import java.util.List;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;


public interface UserService {

	User authorization(String login, String password) throws ServiceException;
	User registration (NewUser newUser) throws ServiceException; 
	List<User> showAllUsers() throws ServiceException;
	void addDetails (String passport, String driverLicense, int idUser) throws ServiceException;
}
