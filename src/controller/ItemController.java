package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import util.Connect;

public class ItemController {

	private static Connect con = Connect.getInstance();
	
	public static void uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
		
	}
	
	public static void editItem(String itemId, String itemName, String itemCategory, String itemSize, String itemPrice) {
		
	}
	
	public static void deleteItem(String itemId) {
		
	}
	
	public static void browseItem(String itemName) {
		
	}
	
	public static ArrayList<Item> viewItem() {
		
		String query = "SELECT * FROM Item";
		ArrayList<Item> items = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
			    String itemId = con.res.getString("Item_id");
			    String itemName= con.res.getString("Item_name");
			    String itemSize = con.res.getString("Item_size");
			    String itemPrice = con.res.getString("Item_price");
			    String itemCategory = con.res.getString("Item_category");
			    String itemStatus = con.res.getString("Item_status");
			    String itemWishlist = con.res.getString("Item_wishlist");
			    String itemOfferStatus = con.res.getString("Item_offer_status");
			    
			    items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
		
	}
	
	public static void checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {
		
	}
	
	public static void viewRequestedItem(String itemId, String itemStatus) {
		
	}
	
	public static void offerPrice(String itemId, String itemPrice) {
		
	}
	
	public static void acceptOffer(String itemId) {
		
	}
	
	public static void declineOffer(String itemId) {
		
	}

	public static void approveItem(String itemId) {
		
	}

	public static void declineItem(String itemId) {
		
	}
	
	public static void viewAcceptedItem(String itemId) {
		
	}
	
	public static void viewOfferItem(String userId) {
		
	}
	
}
