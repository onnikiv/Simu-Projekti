package simu.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for managing the MariaDB database connection.
 * Provides methods to establish and terminate the connection.
 */

public class MariaDbConnection {

    private static Connection conn = null;

    /**
     * Establishes and returns a connection to the MariaDB database.
     * @return the database connection
     */

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/menu?user=root&password=root");
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
            return conn;
        } else {
            return conn;
        }
    }

    /**
     * Terminates the database connection.
     */

    public static void terminate() {
        try {
            getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}