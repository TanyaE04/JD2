package by.it.rent.dao;

import java.util.List;

import by.it.rent.bean.Order;



public interface OrderDAO {
    int order (Order newOrder, int idRefusal) throws DAOException;
    Order findOrder (int idOrder) throws DAOException;
    List <Order> showAllOrders() throws DAOException;
    double bill (int idOrder, double damage) throws DAOException;
    double damage (int idOrder) throws DAOException;
    void updateOrder (Order order) throws DAOException;
    void addDamage (int idOrder, String description, double sum) throws DAOException;
    void  addRefusal(int idRefusal, String [] cause);
	List <Order> showAllOrders(int position, int count) throws DAOException;
	List <Order> showUserOrders(int idUser) throws DAOException;
	void changeStatus (int idOrder, String status) throws DAOException;
	void changeRefusal (int idCar, int idRefusal) throws DAOException;
}
