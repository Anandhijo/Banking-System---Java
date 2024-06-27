package dbconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Dbconnect {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/banking","root","Kuttyma@24");
			System.out.println("DBconnected");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
