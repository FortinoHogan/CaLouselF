package controller;

import java.sql.SQLException;

import model.User;
import util.Connect;

public class UserController {

//	Memanggil method di User untuk login	
	public static String login(String username, String password) {
		
		return User.login(username, password);
		
	}
//	Memanggil method di User untuk register
	public static void register(String username, String password, String phoneNumber, String address, String role) {
		
		User.register(username, password, phoneNumber, address, role);
		
	}
//	Memanggil method di User untuk check account validation
	public static String checkAccountValidtion(String username, String password, String phoneNum, String address, String role) {
		
		return User.checkAccountValidtion(username, password, phoneNum, address, role);
		
	}
//	Memanggil method di User untuk get user by username
	public static User getUserByUsername(String username) {
		
		return User.getUserByUsername(username);
		
	}
}
