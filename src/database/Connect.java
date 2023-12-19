package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "mysticgrills"; // Name of the database, subject to modification as needed
	private final String HOST = "localhost:3306";
	private final String CONECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	private static Connection con;
	private Statement st;
	private static Connect connect;
	private ResultSet rs;

    // Initializes the database connection by loading the JDBC driver and establishing a connection
    // If unsuccessful, prints an error message and terminates the system
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			con = DriverManager.getConnection(CONECTION, USERNAME, PASSWORD); 
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to connect the database, the system is terminated!");
			System.exit(0);
		}
	}
	
	// Manages the database connection using a singleton pattern
	public static synchronized Connect getConnection() {
		return connect = (connect == null) ? new Connect() : connect;
	}
    
    // Executes a query on the database and returns the result set
	public ResultSet executeQuery(String query) {
			try {
				st = con.createStatement();
				rs = st.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return rs;
	}
    
    // Prepares a statement for execution
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}
    
    // Prepares a statement for execution, specifying that it should return generated keys
	public PreparedStatement prepareStatement(String query, int returnGeneratedKeys) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query, returnGeneratedKeys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    // Executes an update on the database using a prepared statement
	public void executeUpdate(PreparedStatement ps) {
	    try {
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
