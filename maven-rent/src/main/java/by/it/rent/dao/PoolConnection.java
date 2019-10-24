package by.it.rent.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoolConnection {
	Logger log= LogManager.getLogger(PoolConnection.class.getName());

    private static volatile PoolConnection instance;
    private BlockingQueue<Connection> listCon=new ArrayBlockingQueue <Connection>(5);
    
    private static String url = "jdbc:mysql://127.0.0.1/car rent?useSSL=false";

    public static PoolConnection getInstance() {
        PoolConnection localInstance = instance;
        if (localInstance == null) {
            synchronized (PoolConnection.class) {
                localInstance = instance;
                if (localInstance == null) instance = localInstance = new PoolConnection();
            }
        }
        return localInstance;
    }


    private PoolConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < 5; i++) {
                Connection connection = DriverManager.getConnection(url, "root", "1111");
                listCon.add(connection);
            }
        }catch (SQLException e) {
            throw new RuntimeException ("pool", e);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException ("class not found", e);
        }
    }

    public Connection take() throws InterruptedException {
        Connection con = listCon.take();
        return con;
    }

    public void release(Connection con) throws DAOException  {
        if (con != null) {
            try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DAOException ("Pool connection");
			}
            try {
				listCon.put(con);
			} catch (InterruptedException e) {
				throw new DAOException ("Pool connection (interrupted)");
			}
        }
        else {
            log.debug("This is a DEBUG-message in pool (release)");
        }
    }
    public void closeCon (){
        for (Connection connection : listCon) {
            try {
                if (connection!=null)
                connection.close();
            } catch (SQLException e){
            	log.debug("This is a DEBUG-message in poool (closeconnection)");
            }
        }
    }

	public static void setUrl(String url1) {
		url = url1;
	}
}
