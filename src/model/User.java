package model;

import java.sql.SQLException;

import util.Connect;

public class User {

	private String userId, username, password, phoneNumber, address, role;
	
	public User(String userId, String username, String password, String phoneNumber, String address, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
//	Memanggil instance database
	private static Connect con = Connect.getInstance();

//	Method untuk validasi login user
	public static String login(String username, String password) {
		
		if(username.equals("admin") && password.equals("admin")) return "admin";
		
		if (username.isEmpty()) {
            return "Username cannot be empty";
		} else if (password.isEmpty()) {
			return "Password cannot be empty";
		} else if (!validateLogin(username, password)) {
			return "Wrong credential";
		} else {
			return "";
		}
	}
//	Method untuk register user
	public static void register(String username, String password, String phoneNumber, String address, String role) {
		
		String query = "INSERT INTO User " + "VALUES ('"+ generateUserId() +"', '"+ username +"', '"+ password +"', '"+ phoneNumber +"', '"+ address +"', '"+ role +"')";
		
		con.execUpdate(query);
	}
//	Method untuk cek validasi register user
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
        	return "";        	
        }
		
	}
//	Method untuk cek password memiliki special character
	private static boolean hasSpecialCharacter(String password) {
        
		String specialCharacters = "!@#$%^&*";

        for (int i = 0; i < password.length(); i++) {
            if (specialCharacters.indexOf(password.charAt(i)) >= 0) {
                return true;
            }
        }
        return false;
        
    }
//	Method untuk cek validasi phone number
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
//	Method untuk generate user id
	private static String generateUserId() {
		
		String lastUserId = null;
	    String newUserId = "US001";
	    
	    String query = "SELECT User_id FROM User ORDER BY User_id DESC LIMIT 1";
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
//	Method untuk mengambil User berdasarkan username
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
//	Method untuk validasi login antara username dan password apakah match
	private static boolean validateLogin(String username, String password) {
		
		User user = getUserByUsername(username);
		
		if (user != null) {
	        return user.getPassword().equals(password);
	    }
	    return false;
		
	}
	
}
