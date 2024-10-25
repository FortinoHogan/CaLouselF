package controller;

import java.sql.SQLException;

import model.User;
import util.Connect;

public class UserController {
	
	private static Connect con = Connect.getInstance();

	public static String login(String username, String password) {
		
		if(username.equals("admin") && password.equals("admin")) return "";
		
		if (username.isEmpty()) {
            return "Username cannot be empty";
		} else if (password.isEmpty()) {
			return "Password cannot be empty";
		} else if (!validateLogin(username, password)) {
			System.out.println(validateLogin(username, password));
			return "Wrong credential";
		} else {
			return "";
		}
	}

	public static void register(String userId, String username, String password, String phoneNumber, String address, String role) {
		
		String query = "INSERT INTO User " + "VALUES ('"+ userId +"', '"+ username +"', '"+ password +"', '"+ phoneNumber +"', '"+ address +"', '"+ role +"')";
		
		System.out.println(query);
		con.execUpdate(query);
	}

	public static String checkAccountValidtion(String username, String password, String phoneNum, String address, String role) {
		
		if (username.isEmpty()) {
            return "Username cannot be empty";
        } else if (username.length() < 3) {
            return "Username must be at least 3 characters long";
        } else if (getUserByUsername(username) != null) {
            return "Username already exists";
        } else if (password.isEmpty()) {
            return "Password cannot be empty";
        } else if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        } else if (!hasSpecialCharacter(password)) {
            return "Password must include special characters (!, @, #, $, %, ^, &, *)";
        } else if (!isValidPhoneNumber(phoneNum)) {
            return "Phone Number must contain +62 and be at least 10 numbers long";
        } else if (address.isEmpty()) {
            return "Address cannot be empty";
        } else if (role == null || role.isEmpty()) {
            return "You must pick a role (Seller or Buyer)";
        } else {
        	register(generateUserId(), username, password, phoneNum, address, role);
        	return "";        	
        }
		
	}
	
	private static boolean hasSpecialCharacter(String password) {
        
		String specialCharacters = "!@#$%^&*";

        for (int i = 0; i < password.length(); i++) {
            if (specialCharacters.indexOf(password.charAt(i)) >= 0) {
                return true;
            }
        }
        return false;
        
    }
	
	private static boolean isValidPhoneNumber(String phoneNum) {
	    
		if (!phoneNum.startsWith("+62")) {
	        return false;
	    }

	    String numberWithoutPrefix = phoneNum.substring(3);
	    if (numberWithoutPrefix.length() < 9) {
	        return false;
	    }

	    for (char c : numberWithoutPrefix.toCharArray()) {
	        if (!Character.isDigit(c)) {
	            return false;
	        }
	    }

	    return true;
	    
	}
	
	private static String generateUserId() {
		
		String lastUserId = null;
	    String newUserId = "US001";
	    
	    String query = "SELECT userId FROM User ORDER BY userId DESC LIMIT 1";
	    try {
	        con.res = con.execQuery(query);
	        if (con.res.next()) {
	            lastUserId = con.res.getString("User_id");
	        }

	        if (lastUserId != null) {
	            String numericPart = lastUserId.substring(2);
	            int newIdNumber = Integer.parseInt(numericPart) + 1;
	            newUserId = "US" + String.format("%03d", newIdNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return newUserId;
	    
	}
	
	public static User getUserByUsername(String username) {
		
		String query = "SELECT * FROM User WHERE Username LIKE '%" + username + "%'";
		User user = null;
		con.res = con.execQuery(query);
		
		try {
			if (con.res.next()) {
			    String userId = con.res.getString("User_id");
			    String password = con.res.getString("Password");
			    String phoneNumber = con.res.getString("Phone_Number");
			    String address = con.res.getString("Address");
			    String role = con.res.getString("Role");

			    user = new User(userId, username, password, phoneNumber, address, role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return user;
				
	}
	
	private static boolean validateLogin(String username, String password) {
		
		User user = getUserByUsername(username);
		
		if (user != null) {
	        return user.getPassword().equals(password);
	    }
	    return false;
		
	}
	
}
