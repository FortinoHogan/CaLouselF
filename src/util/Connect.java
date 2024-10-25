package util;

import java.sql.*;

public class Connect {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "calouself";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	public ResultSet res;
	public ResultSetMetaData resm;
	
	
	private Connection con;
	private Statement st;
	private static Connect instance;
	
	public static Connect getInstance() {
		if(instance == null) {
			return new Connect();
		}
		return instance;
	}
	
	private Connect() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
	
		try {
			res = st.executeQuery(query);
			resm = res.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}
	
}
