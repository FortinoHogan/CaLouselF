package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import model.Transaction;
import model.TransactionHistory;
import util.Connect;

public class TransactionController {

	private static Connect con = Connect.getInstance();
	
	public static void purchaseItem(String userId, String itemId) {
		
		String transactionId = generateTransactionId();
		
		createTransaction(transactionId, userId, itemId);
		
		String queryDel = String.format("DELETE FROM Whislist WHERE Item_id LIKE '%s'", itemId);
		
		con.execUpdate(queryDel);
		
	}
	
	public static ArrayList<TransactionHistory> viewHistory(String userId){
		
		String query = String.format("SELECT t.Transaction_id, i.Item_name, i.Item_Category, i.Item_size, i.Item_price FROM Transaction t \n"
				+ "JOIN Item i ON i.Item_id = t.Item_id WHERE t.User_id LIKE '%s'", userId);
		ArrayList<TransactionHistory> history = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String transactionId = con.res.getString("Transaction_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("Item_price");
				String itemCategory = con.res.getString("Item_category");
				
				history.add(new TransactionHistory(transactionId, itemName, itemCategory, itemSize, itemPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return history;
		
	}
	
	public static void createTransaction(String transactionId, String userId, String itemId) {
		
		String query = "INSERT INTO Transaction(Transaction_id, User_id, Item_id) " + "VALUES ('"+ transactionId +"', '"+ userId +"', '"+ itemId +"')";
		
		con.execUpdate(query);
		
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
