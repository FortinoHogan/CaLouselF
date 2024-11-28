package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import util.Connect;

public class ItemController {

	private static Connect con = Connect.getInstance();

	public static void uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {

		String query = "INSERT INTO Item " + "VALUES ('" + generateItemId() + "', '" + itemName + "', '" + itemSize
				+ "', '" + itemPrice + "', '" + itemCategory + "', '" + "Pending" + "', '" + "-" + "', '" + "No offer"
				+ "')";

		con.execUpdate(query);

	}

	public static void editItem(String itemId, String itemName, String itemCategory, String itemSize,
			String itemPrice) {

		String query = String.format(
				"UPDATE Item\n" + "SET Item_name = '%s', Item_category = '%s', Item_size = '%s', Item_price = '%s'\n"
						+ "WHERE Item_id = '%s'",
				itemName, itemCategory, itemSize, itemPrice, itemId);

		con.execUpdate(query);

	}

	public static void deleteItem(String itemId) {

		String query = String.format("DELETE FROM Item WHERE Item_id = '%s'", itemId);

		con.execUpdate(query);
	}

	public static void browseItem(String itemName) {

	}

	public static ArrayList<Item> viewItem() {

		String query = "SELECT * FROM Item WHERE Item_status LIKE 'Approved'";
		ArrayList<Item> items = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String itemId = con.res.getString("Item_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("Item_price");
				String itemCategory = con.res.getString("Item_category");
				String itemStatus = con.res.getString("Item_status");
				String itemWishlist = con.res.getString("Item_wishlist");
				String itemOfferStatus = con.res.getString("Item_offer_status");

				items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist,
						itemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;

	}

	public static String checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {

		if (itemName.isEmpty()) {
			return "Item name cannot be empty";
		} else if (itemName.length() < 3) {
			return "Item name must be at least 3 characters long";
		} else if (itemCategory.isEmpty()) {
			return "Item category cannot be empty";
		} else if (itemCategory.length() < 3) {
			return "Item category must be at least 3 characters long";
		} else if (itemSize.isEmpty()) {
			return "Item size cannot be empty";
		} else if (itemPrice.isEmpty()) {
			return "Item price cannot be empty";
		} else if (!isNumber(itemPrice)) {
			return "Item price must be in number and cannot be 0";
		} else {
			return "";
		}

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

		String query = String.format("UPDATE Item SET Item_status = 'Approved' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
		
	}

	public static void declineItem(String itemId) {

		String query = String.format("DELETE FROM Item WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
		
	}

	public static void viewAcceptedItem(String itemId) {

	}

	public static void viewOfferItem(String userId) {

	}

	private static boolean isNumber(String price) {

		if (price.equals("0") || price.startsWith("0"))
			return false;

		for (char c : price.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;

	}

	private static String generateItemId() {

		String lastItemId = null;
		String newItemId = "IT001";

		String query = "SELECT Item_id FROM Item ORDER BY Item_id DESC LIMIT 1";
		try {
			con.res = con.execQuery(query);
			if (con.res.next()) {
				lastItemId = con.res.getString("Item_id");
			}

			if (lastItemId != null) {
				String numericPart = lastItemId.substring(2);
				int newIdNumber = Integer.parseInt(numericPart) + 1;
				newItemId = "IT" + String.format("%03d", newIdNumber);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newItemId;

	}

	public static ArrayList<Item> getItemsByUserId(String userId) {

		String query = String.format("SELECT * FROM Item WHERE Item_status LIKE 'Approved' AND User_id LIKE '%s'",
				userId);
		ArrayList<Item> items = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String itemId = con.res.getString("Item_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("Item_price");
				String itemCategory = con.res.getString("Item_category");
				String itemStatus = con.res.getString("Item_status");
				String itemWishlist = con.res.getString("Item_wishlist");
				String itemOfferStatus = con.res.getString("Item_offer_status");

				items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist,
						itemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;

	}
	
	public static ArrayList<Item> getPendingItems(){
		
		String query = "SELECT * FROM Item WHERE Item_status LIKE 'Pending'";
		ArrayList<Item> items = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String itemId = con.res.getString("Item_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("Item_price");
				String itemCategory = con.res.getString("Item_category");
				String itemStatus = con.res.getString("Item_status");
				String itemWishlist = con.res.getString("Item_wishlist");
				String itemOfferStatus = con.res.getString("Item_offer_status");

				items.add(new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist,
						itemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
		
	}

	public static Item getItem(String item_Id) {

		String query = String.format("SELECT * FROM Item WHERE Item_id LIKE '%s'", item_Id);
		Item item = null;
		con.res = con.execQuery(query);
		try {
			String itemId = con.res.getString("Item_id");
			String itemName = con.res.getString("Item_name");
			String itemSize = con.res.getString("Item_size");
			String itemPrice = con.res.getString("Item_price");
			String itemCategory = con.res.getString("Item_category");
			String itemStatus = con.res.getString("Item_status");
			String itemWishlist = con.res.getString("Item_wishlist");
			String itemOfferStatus = con.res.getString("Item_offer_status");

			item = new Item(itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist,
					itemOfferStatus);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return item;

	}
	
	public static String validateDecline(String reason) {
		
		if(reason.isEmpty()) {
			return "Reason cannot be empty";
		} else {
			return "";
		}
		
	}
}
