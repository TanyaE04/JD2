package by.it.rent.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.DAOProvider;
import by.it.rent.dao.PoolConnection;
import by.it.rent.dao.UserDAO;

public class TestSQLUserDAO {
	
	private UserDAO userDAO;
	
	@Before
	public void initTest () {
		PoolConnection.setUrl("jdbc:mysql://127.0.0.1/test_rent?useSSL=false");
		userDAO = DAOProvider.INSTANCE.getUserDAO();
	}

	@Test
	public void testAuthorization() throws DAOException {
		User user = new User ();
		user.setSurname("ivanov");
		String login = "1";
		String password = "1234";
		User userActual = userDAO.authorization(login, password);
		userActual.getSurname();
		assertEquals(user.getSurname(), userActual.getSurname());
	}
	
	@Test
	public void testAuthorizationNull() throws DAOException {
		String login = "1";
		String password = "1111";
		User userActual = userDAO.authorization(login, password);
		assertNull (userActual);
	}

	@Test
	public void testRegistration() throws DAOException {
		NewUser newUser = new NewUser ();
		newUser.setName ("aaaaa");
		newUser.setSurname ("bbbbb");
		newUser.setPhone ("8025-222-22-22");
		newUser.setLogin ("petya");
		newUser.setPassword ("1111");
		newUser.setMail ("p@mail.ru");
		newUser.setAddress ("Galo, 20-20");
		User actual = userDAO.registration(newUser);
		assertEquals (newUser.getName(), actual.getName());
	}
	
	
	@Test
	public void testChangeRole() throws DAOException {
		userDAO.changeRole(1, 2);
		User actual = userDAO.showUserById(1);
		assertEquals (2, actual.getIdRole());
	}

	@Test
	public void testShowAllUsers() throws DAOException {
		List <User> list = userDAO.showAllUsers();
		int actual = list.size();
		assertTrue (3==actual);
	}

	@Test
	public void testCheckLogin() throws DAOException {
		boolean actual = userDAO.checkLogin("1");
		assertTrue (actual);
	}
	
	@Test
	public void testCheckLoginNotExist() throws DAOException {
		boolean actual = userDAO.checkLogin("45");
		assertFalse (actual);
	}

	@Test
	public void testShowUserById() throws DAOException {
		User actual = userDAO.showUserById(1);
		assertEquals ("ivan", actual.getName());
	}
	
	@Test
	public void testShowUserByIdNoSuch() throws DAOException {
		User actual = userDAO.showUserById(0);
		assertNull (actual);
	}

	@Test (expected = DAOException.class)
	public void testAddDetailsFail () throws DAOException {
		String passport = "AA3632211";
		String driverLicense = "0AA122556";
		int idUser = 0;
		userDAO.addDetails(passport, driverLicense, idUser);
	}


	@Test 
	public void testUpdateUser() throws DAOException {
		User user = new User ();
		user.setIdUser(35);
		user.setName ("david");
		user.setSurname ("petrov");
		user.setPhone ("8025-222-22-22");
		user.setMail ("p@mail.ru");
		user.setAddress ("Galo, 20-20");
		userDAO.updateUser(user);
		User actual = userDAO.showUserById(35);
		assertEquals ("david", actual.getName());
	}

	@Test
	public void testChangeStatus() throws DAOException {
		String status = "block";
		userDAO.changeStatus(1, status);
		User actual = userDAO.showUserById(1);
		assertEquals (status, actual.getStatus());
	}
}
