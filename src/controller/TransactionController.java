package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Transaction;
import util.Connect;

public class TransactionController {

	private static Connect con = Connect.getInstance();
	
	public static void purchaseItem(String userId, String itemId) {
		
		String query = "INSERT INTO Transaction(Transaction_id, User_id, Item_id) " + "VALUES ('"+ generateTransactionId() +"', '"+ userId +"', '"+ itemId +"')";
		
		con.execUpdate(query);
		
	}
	
	public static ArrayList<Transaction> viewHistory(String userId){
		
		return null;
		
	}
	
	public static void createTransaction(String transactionId) {
		
	}
	
	private static String generateTransactionId() {
		
		String lastTransactionId = null;
	    String newTransactionId = "TR001";
	    
	    String query = "SELECT Transaction_id FROM Transaction ORDER BY Transaction_id DESC LIMIT 1";
	    try {
	        con.res = con.execQuery(query);
	        if (con.res.next()) {
	            lastTransactionId = con.res.getString("Transaction_id");
	        }

	        if (lastTransactionId != null) {
	            String numericPart = lastTransactionId.substring(2);
	            int newIdNumber = Integer.parseInt(numericPart) + 1;
	            newTransactionId = "TR" + String.format("%03d", newIdNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return newTransactionId;
		
	}
}
