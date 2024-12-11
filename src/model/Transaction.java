package model;

import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Transaction {

	private String userId, itemId, transactionId;

	public Transaction(String userId, String itemId, String transactionId) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
//	Memanggil instance database
	private static Connect con = Connect.getInstance();
	
//	Method untuk membuat transaksi jika buyer purchase item
	public static void purchaseItem(String userId, String itemId) {
		
		String transactionId = generateTransactionId();
		
		createTransaction(transactionId, userId, itemId);
		
		String queryDel = String.format("DELETE FROM Wishlist WHERE Item_id LIKE '%s'", itemId);
		
		con.execUpdate(queryDel);
		
		String queryIt = String.format("UPDATE Item SET Item_wishlist = '0' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}
//	Method untuk melihat history dari transaksi suatu buyer
	public static ArrayList<TransactionHistory> viewHistory(String userId){
		
		String query = String.format(
		        "SELECT t.Transaction_id, i.Item_name, i.Item_category, i.Item_size, " +
		        "CASE " +
		        "   WHEN o.Offer_price IS NOT NULL THEN o.Offer_price " +
		        "   ELSE i.Item_price " +
		        "END AS final_price " +
		        "FROM Transaction t " +
		        "JOIN Item i ON i.Item_id = t.Item_id " +
		        "LEFT JOIN Offer o ON o.Item_id = t.Item_id AND o.User_id = t.User_id " +
		        "WHERE t.User_id LIKE '%s'", 
		        userId
		    );
		ArrayList<TransactionHistory> history = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String transactionId = con.res.getString("Transaction_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("final_price");
				String itemCategory = con.res.getString("Item_category");
				
				history.add(new TransactionHistory(transactionId, itemName, itemCategory, itemSize, itemPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return history;
		
	}
//	Method untuk memasukkan data transaksi ke databsse
	public static void createTransaction(String transactionId, String userId, String itemId) {
		
		String query = "INSERT INTO Transaction(Transaction_id, User_id, Item_id) " + "VALUES ('"+ transactionId +"', '"+ userId +"', '"+ itemId +"')";
		
		con.execUpdate(query);
		
	}
//	Method untuk membuat transaction id
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
