package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import model.Transaction;
import model.TransactionHistory;
import util.Connect;

public class TransactionController {

//	Memanggil method di Transaction untuk purchase item
	public static void purchaseItem(String userId, String itemId) {
		
		Transaction.purchaseItem(userId, itemId);
		
	}
//	Memanggil method di Transaction untuk view history	
	public static ArrayList<TransactionHistory> viewHistory(String userId){
		
		return Transaction.viewHistory(userId);
		
	}
//	Memanggil method di Transaction untuk create transaction
	public static void createTransaction(String transactionId, String userId, String itemId) {
		
		Transaction.createTransaction(transactionId, userId, itemId);
		
	}

}
