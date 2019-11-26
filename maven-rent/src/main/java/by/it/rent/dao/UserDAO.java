package by.it.rent.dao;

import java.util.List;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;

/**
 *  The user of this interface can update, create and get data about Users from Database.
 * @author Tanya Livanivich
 * @version 1.0
 */

public interface UserDAO {

	/**
	 * Contribute getting data about user by its login and password
	 * @param login   	User login entered during authorization 
	 * @param password 	User password entered during authorization
	 * @return 			User object if user with such login and password exists, otherwise returns null.
	 * @throws DAOException
	 */
    User authorization (String login, String password) throws DAOException;
    /**
     * Contribute create new user in database
     * @param newUser	User object consists of data entered during registration
     * @return			Unique identification number of new user
     * @throws DAOException
     */
    int registration (NewUser newUser) throws DAOException;
    /**
     * Contribute finding user by its id
     * @param idUser	Unique identification number of user
     * @return			User object if user with such id exists
     * @throws DAOException
     */
    User showUserById (int idUser) throws DAOException;
    /**
     * Contribute finding of all users from database  
     * @return 			List of all users from database
     * @throws DAOException
     */
    List <User> showAllUsers () throws DAOException;
    /**
     * Contribute change user role (admin or user)
     * @param idUser	Unique identification number of user
     * @param idRole	Unique number of role (1 - admin or 2 - user)
     * @throws DAOException
     */
    void changeRole(int idUser, int idRole) throws DAOException;
    /**
     * Checking if some user already has such login or not
     * @param login		User login entered during registration 
     * @return			True if such login exists, otherwise false
     * @throws DAOException
     */
    boolean checkLogin (String login) throws DAOException;
    /**
     * Creating user's data such as driver's license and passport
     * @param passport		User passport
     * @param driverLicense	User drever's license
     * @param idUser		Unique identification number of user
     * @throws DAOException
     */
    void addDetails (String passport, String driverLicense, int idUser) throws DAOException;
    /**
     * Contribute updating any user data
     * @param user 		User object contains of new data
     * @throws DAOException
     */
    void updateUser (User user) throws DAOException;
    /**
     * Changing user's status (block or unblock)
     * @param idUser	Unique identification number of user
     * @param status	User status (block or null)
     * @throws DAOException
     */
    void changeStatus(int idUser, String status) throws DAOException;
    /**
     * 
     * @param idUser	Unique identification number of user
     * @param bill		Bill is a total sum of order (includes sum of damage)
     * @throws DAOException
     */
    void changeDebt (int idUser, double bill) throws DAOException;
}
