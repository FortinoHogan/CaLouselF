package model;

import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Item {

	private String itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus;

	public Item(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory,
			String itemStatus, String itemWishlist, String itemOfferStatus) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public void setItemWishlist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}
//	Memanggil instance database
	private static Connect con = Connect.getInstance();

//	Method untuk upload item ke database 
	public static void uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice, String userId) {

		String query = "INSERT INTO Item " + "VALUES ('" + generateItemId() + "', '" + itemName + "', '" + itemSize
				+ "', '" + itemPrice + "', '" + itemCategory + "', '" + "Pending" + "', '" + "0" + "', '" + "No offer"
				+ "', '" + userId + "')";

		con.execUpdate(query);

	}
//	Method untuk update item
	public static void editItem(String itemId, String itemName, String itemCategory, String itemSize,
			String itemPrice) {

		String query = String.format(
				"UPDATE Item\n" + "SET Item_name = '%s', Item_category = '%s', Item_size = '%s', Item_price = '%s'\n"
						+ "WHERE Item_id = '%s'",
				itemName, itemCategory, itemSize, itemPrice, itemId);

		con.execUpdate(query);

	}
//	Method untuk delete item
	public static void deleteItem(String itemId) {

		String queryTr = String.format("DELETE FROM Transaction WHERE Item_id = '%s'", itemId);
		con.execUpdate(queryTr);
		
		String queryOf = String.format("DELETE FROM Offer WHERE Item_id = '%s'", itemId);
		con.execUpdate(queryOf);
		
		String queryWi = String.format("DELETE FROM Wishlist WHERE Item_id = '%s'", itemId);
		con.execUpdate(queryWi);
		
		String query = String.format("DELETE FROM Item WHERE Item_id = '%s'", itemId);
		con.execUpdate(query);
	}
//	Method untuk get item searching
	public static ArrayList<Item> browseItem(String _itemName) {

		String query = "SELECT * FROM Item WHERE Item_status LIKE 'Approved' AND LOWER(Item_name) LIKE LOWER('%" + _itemName + "%')";
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
//	Method untuk view item yang sudah approved
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
//	Method untuk validasi upload item
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
//	Method untuk view item yang masih pending
	public static Item viewRequestedItem(String _itemId, String _itemStatus) {

		String query = String.format("SELECT * FROM Item WHERE Item_id LIKE '%s'", _itemId);
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
//	Method untuk offer price
	public static void offerPrice(String itemId, String itemPrice, String userId) {

		String query = "INSERT INTO Offer(Offer_id, User_id, Item_id, Offer_price, Offer_status) " + "VALUES ('"
				+ generateOfferId() + "', '" + userId + "', '" + itemId +  "', '" + itemPrice + "', 'Pending')";

		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_offer_status = 'Offering' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}
//	Method untuk menerima offer
	public static void acceptOffer(String itemId) {

		String query = String.format("UPDATE Offer SET Offer_status = 'Accepted' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
			
		
	}
//	Method untuk menolak offer
	public static void declineOffer(String itemId) {

		String query = String.format("DELETE FROM Offer WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_offer_status = 'No Offer' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}
//	Method untuk approve item yang masih pending
	public static void approveItem(String itemId) {

		String query = String.format("UPDATE Item SET Item_status = 'Approved' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
		
	}
//	Method untuk decline item yang masih pending
	public static void declineItem(String itemId) {

		String query = String.format("DELETE FROM Item WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(query);
		
	}
//	Method untuk view accepted item
	public static Item viewAcceptedItem(String _itemId) {
		
		String query = String.format("SELECT * FROM Item WHERE Item_id LIKE '%s'", _itemId);
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
//	Method untuk view offer item
	public static ArrayList<OfferDetail> viewOfferItem(String userId) {

		String query = String.format("SELECT o.Offer_id, o.User_id, o.Item_id, o.Offer_price, i.Item_name, i.Item_category, i.Item_size, i.Item_price\n"
									+ "FROM Offer o "
									+ "JOIN ITEM i ON o.Item_id = i.Item_id "
									+ "WHERE i.User_id = '%s' "
									+ "AND o.Offer_status = 'Pending'"
									, userId);
		
		ArrayList<OfferDetail> offers = new ArrayList<OfferDetail>();
		
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String itemId = con.res.getString("o.Item_id");
				String buyerId = con.res.getString("o.User_id");
				String itemName = con.res.getString("i.Item_name");
				String itemSize = con.res.getString("i.Item_size");
				String itemPrice = con.res.getString("i.Item_price");
				String itemCategory = con.res.getString("i.Item_category");
				String offerPrice = con.res.getString("o.Offer_price");
				String offerId = con.res.getString("o.Offer_id");

				offers.add(new OfferDetail(offerId, buyerId, itemId, itemName, itemCategory, itemSize, itemPrice, offerPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return offers;
		
	}
//	Method untuk validasi suatu input adalah angka
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
//	Method untuk generate item id
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
//	Method untuk mengambil item seorang seller
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
//	Method untuk mengambil item yang masih pending
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
//	Method untuk generate offer id
	private static String generateOfferId() {

		String lastOfferId = null;
		String newOfferId = "OF001";

		String query = "SELECT Offer_id FROM Offer ORDER BY Offer_id DESC LIMIT 1";
		try {
			con.res = con.execQuery(query);
			if (con.res.next()) {
				lastOfferId = con.res.getString("Offer_id");
			}

			if (lastOfferId != null) {
				String numericPart = lastOfferId.substring(2);
				int newIdNumber = Integer.parseInt(numericPart) + 1;
				newOfferId = "OF" + String.format("%03d", newIdNumber);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newOfferId;

	}
	
}
