package by.it.rent.dao;

import by.it.rent.dao.impl.SQLCarDAO;
import by.it.rent.dao.impl.SQLOrderDAO;
import by.it.rent.dao.impl.SQLUserDAO;

public enum  DAOProvider {
    INSTANCE;
    private UserDAO userDAO=new SQLUserDAO();
    private CarDAO carDAO=new SQLCarDAO ();
    private OrderDAO orderDAO = new SQLOrderDAO ();
    
    public UserDAO getUserDAO() {
        return userDAO;
    }
	public CarDAO getCarDAO() {
		return carDAO;
	}
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
}
