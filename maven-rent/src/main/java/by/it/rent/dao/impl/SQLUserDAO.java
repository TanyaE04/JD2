package by.it.rent.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.it.rent.bean.NewUser;
import by.it.rent.bean.User;
import by.it.rent.dao.DAOException;
import by.it.rent.dao.PoolConnection;

import by.it.rent.dao.UserDAO;

public class SQLUserDAO implements UserDAO {
	Logger log= LogManager.getLogger(SQLUserDAO.class.getName());
	private static PoolConnection pc;
	private static final String SELECT_USERS_BY_LOGIN = "SELECT * FROM users LEFT JOIN userstatus ON users.id_user=userstatus.id_user LEFT JOIN details ON users.id_user=details.id_user WHERE login=? AND password=?";
	private static final String SELECT_USERS_BY_LOG = "SELECT * FROM users WHERE login=?";
	private static final String SELECT_USERS_BY_ID = "SELECT * FROM users LEFT JOIN userstatus ON users.id_user=userstatus.id_user WHERE users.id_user=?";
	private static final String SELECT_ALL_USERS = "SELECT * FROM users LEFT JOIN details ON users.id_user=details.id_user LEFT JOIN userstatus ON users.id_user=userstatus.id_user";
	private static final String SELECT_DETAILS_BY_IDUSER = "SELECT driver_license, passport FROM details WHERE id_user=?";
	private static final String INSERT_USERS = "INSERT INTO users VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, '0')";
	private static final String INSERT_DETAILS = "INSERT INTO details VALUES (?, ?, ?)";
	private static final String INSERT_USER_STATUS = "INSERT INTO userstatus VALUES (?, ?)";
	private static final String UPDATE_USERS_ROLE = "UPDATE users SET id_role=? WHERE id_user=?";
	private static final String UPDATE_USERS_STATUS = "UPDATE userstatus SET status=? WHERE id_user=?";
	private static final String UPDATE_USERS_DEBT = "UPDATE users SET debt=? WHERE id_user=?";
	private static final String UPDATE_USER = "UPDATE users SET surname=?, name=?, phone_number=?, mail=?, adress=? WHERE id_user=?";
	private static final String UPDATE_DETAILS = "UPDATE details SET driver_license=?, passport=? WHERE id_user=?";
	private static final String ADMIN = "Администратор";
	private static final String USER = "Пользователь";
			

	static {
		try {
			pc = PoolConnection.getInstance();
		} catch (RuntimeException e) {
			throw new RuntimeException (e);
		}
	}

	@Override
	public User authorization(String login, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		User user = new User();
		try {
			connection = pc.take();
			prst = connection.prepareStatement(SELECT_USERS_BY_LOGIN);
			prst.setString(1, login);
			String pas = hashing(password);
			prst.setString(2, pas);
			rs = prst.executeQuery();
			if (rs.next()) {
				user.setIdUser(rs.getInt(1));
				user.setIdRole(rs.getInt(2));
				user.setSurname(rs.getString(3));
				user.setName(rs.getString(4));
				user.setDebt(rs.getDouble(10));
				user.setStatus(rs.getString(12));
				user.setDriverLicense(rs.getString(13));
				user.setPassport(rs.getString(14));
			} else
				user = null;
		} catch (SQLException e) {
			throw new DAOException("ERROR: authorization", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		return user;
	}

	@Override
	public int registration(NewUser newUser) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		int idUser;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(INSERT_USERS);
			connection.setAutoCommit(false);
			prst.setInt(1, 2);
			prst.setString(2, (newUser.getSurname()));
			prst.setString(3, (newUser.getName()));
			prst.setString(4, (newUser.getPhone()));
			String login = newUser.getLogin();
			prst.setString(5, login);
			String password = hashing(newUser.getPassword());
			prst.setString(6, password);
			prst.setString(7, (newUser.getMail()));
			prst.setString(8, (newUser.getAddress()));
			prst.executeUpdate();
			connection.commit();
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					throw new DAOException (e);
				}
			}
			prst = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            rs = prst.executeQuery();
            rs.next();
            idUser = rs.getInt(1);
			addDetails(null, null, idUser);
			addUserStatus (idUser, null);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException (e1);
			}
			throw new DAOException("ERROR: registration", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		return idUser;
	}

	private static String hashing(String password) {
		StringBuilder stringBuilder = new StringBuilder(password);
		stringBuilder.insert(3, 34);
		stringBuilder.insert(1, 'B');
		stringBuilder.insert(2, 'f');
		stringBuilder.append("eEff");
		String hashPas = stringBuilder.toString();
		return hashPas;
	}

	public void changeRole(int idUsers, int idRole) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			connection.setAutoCommit(false);
			prst = connection.prepareStatement(UPDATE_USERS_ROLE);
			prst.setInt(1, idRole);
			prst.setInt(2, idUsers);
			prst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException (e1);
			}
			throw new DAOException("ERROR: change role", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
	}

	@Override
	public List<User> showAllUsers() throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		List<User> list = new ArrayList<User>();
		try {
			connection = pc.take();
			prst = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet result = prst.executeQuery();
			while (result.next()) {
				User user = new User();
				int idUser = result.getInt(1);
				user.setIdUser(idUser);
				int idRole = result.getInt(2);
				user.setIdRole(idRole);
				if (idRole == 1) {
					user.setRoleName(ADMIN);
				} else {
					user.setRoleName(USER);
				}
				user.setSurname(result.getString(3));
				user.setName(result.getString(4));
				user.setPhone(result.getString(5));
				user.setMail(result.getString(8));
				user.setAddress(result.getString(9));
				user.setDebt(result.getDouble(10));
				user.setDriverLicense(result.getString(11));
                user.setPassport(result.getString(12));
                user.setStatus(result.getString(15));
				list.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException("ERROR: find all users", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		return list;
	}
	
	public boolean checkLogin (String login) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(SELECT_USERS_BY_LOG);
			prst.setString(1, login);
			rs = prst.executeQuery();
			if (rs.next()) {
				return true;
			} 
		} catch (SQLException e) {
			throw new DAOException("ERROR: check login", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		return false;
	}

	@Override
	public User showUserById(int idUser) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		User user = new User();
		try {
			connection = pc.take();
			prst = connection.prepareStatement(SELECT_USERS_BY_ID);
			prst.setInt(1, idUser);
			rs = prst.executeQuery();
			if (rs.next()) {
				idUser = rs.getInt(1);
				user.setIdUser(rs.getInt(1));
				user.setIdRole(rs.getInt(2));
				user.setSurname(rs.getString(3));
				user.setName(rs.getString(4));
				user.setPhone(rs.getString(5));
				user.setMail(rs.getString(8));
				user.setAddress (rs.getString(9));
				user.setDebt(rs.getDouble(10));
				user.setStatus(rs.getString(12));
				if (prst != null) {
					try {
						prst.close();
					} catch (SQLException e) {
						throw new DAOException (e);
					}
				}
				prst = connection.prepareStatement(SELECT_DETAILS_BY_IDUSER);
				prst.setInt(1, idUser);
				rs = prst.executeQuery();
				if (rs.next()) {
					user.setDriverLicense(rs.getString(1));
					user.setPassport(rs.getString(2));
				}
			} else
				user = null;
		} catch (SQLException e) {
			throw new DAOException("ERROR: find user by ID", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		return user;
	}

	@Override
	public void addDetails(String passport, String driverLicense, int idUser) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(INSERT_DETAILS);
			connection.setAutoCommit(false);
			prst.setString(1, passport);
			prst.setString(2, driverLicense);
			prst.setInt(3, idUser);
			prst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException (e1);
			}
			throw new DAOException("ERROR: add details", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
	}
	
	public void addUserStatus(int idUser, String status) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(INSERT_USER_STATUS);
			connection.setAutoCommit(false);
			prst.setInt(1, idUser);
			prst.setString(2, status);
			prst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException (e1);
			}
			throw new DAOException("ERROR: add user status", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
	}

	@Override
	public void updateUser(User user) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			connection.setAutoCommit(false);
			prst = connection.prepareStatement(UPDATE_USER);
			prst.setString(1, user.getSurname());
			prst.setString(2, user.getName());
			prst.setString(3, user.getPhone());
			prst.setString(4, user.getMail());
			prst.setString(5, user.getAddress());
			prst.setInt(6, user.getIdUser());
			prst.executeUpdate();
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					throw new DAOException (e);
				}
			}
			prst = connection.prepareStatement(UPDATE_DETAILS);
			prst.setString(1, user.getDriverLicense());
			prst.setString(2, user.getPassport());
			prst.setInt(3, user.getIdUser());
			prst.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException (e1);
			}
			throw new DAOException("ERROR: update user", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
	}

	@Override
	public void changeStatus(int idUser, String status) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(UPDATE_USERS_STATUS);
			prst.setInt(2, idUser);
			prst.setString(1, status);
			prst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERROR: change user status", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
	}

	@Override
	public void changeDebt(int idUser, double bill) throws DAOException {
		Connection connection = null;
		PreparedStatement prst = null;
		try {
			connection = pc.take();
			prst = connection.prepareStatement(UPDATE_USERS_DEBT);
			prst.setInt(2, idUser);
			prst.setDouble(1, bill);
			prst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERROR: change user debt", e);
		} catch (InterruptedException e) {
			throw new DAOException (e);
		} finally {
			if (prst != null) {
				try {
					prst.close();
				} catch (SQLException e) {
					log.debug("This is a DEBUG-message in SQLUserDAO");
				}
			}
			pc.release(connection);
		}
		
	}	
		

}