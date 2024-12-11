package model;

import java.sql.SQLException;
import java.util.ArrayList;

import util.Connect;

public class Wishlist {

	private String wishlistId, itemId, userId;

	public Wishlist(String wishlistId, String itemId, String userId) {
		super();
		this.wishlistId = wishlistId;
		this.itemId = itemId;
		this.userId = userId;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public void setWishlistId(String wishlistId) {
		this.wishlistId = wishlistId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
//	Memanggil instance database
	private static Connect con = Connect.getInstance();

//	Method untuk mengambil wishlist seorang buyer
	public static ArrayList<WishlistItem> viewWishlist(String wishlist_Id, String user_Id) {
		
		String query = String.format(
				"SELECT Wishlist_id, w.User_id, w.Item_id, Item_name, Item_size, Item_price, Item_category, Item_status, Item_wishlist, Item_offer_status "
						+ "FROM Wishlist w " + "JOIN Item i ON w.Item_id = i.Item_id " + "WHERE w.User_id = '%s'",
				user_Id);
		ArrayList<WishlistItem> wishlistItem = new ArrayList<>();
		con.res = con.execQuery(query);
		try {
			while (con.res.next()) {
				String wishlistId = con.res.getString("Wishlist_id");
				String userId = con.res.getString("User_id");
				String itemId = con.res.getString("Item_id");
				String itemName = con.res.getString("Item_name");
				String itemSize = con.res.getString("Item_size");
				String itemPrice = con.res.getString("Item_price");
				String itemCategory = con.res.getString("Item_category");
				String itemStatus = con.res.getString("Item_status");
				String itemWishlist = con.res.getString("Item_wishlist");
				String itemOfferStatus = con.res.getString("Item_offer_status");

				wishlistItem.add(new WishlistItem(wishlistId, userId, itemId, itemName, itemSize, itemPrice,
						itemCategory, itemStatus, itemWishlist, itemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wishlistItem;
		
	}
//	Method untuk menambahkan Item ke Wishlist seorang buyer
	public static void addWishlist(String itemId, String userId) {
		
		String query = "INSERT INTO Wishlist VALUES ('" + generateWishlistId() + "', '" + itemId + "', '" + userId + "')";
		
		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_wishlist = (SELECT COUNT(*) FROM Wishlist WHERE Item_id = '%s') WHERE Item_id = '%s'", itemId, itemId);
		
		con.execUpdate(queryIt);
	}
//	Method untuk menghapus Item dari Wishlist seorang buyer
	public static void removeWishlist(String wishlistId) {
		
		String query = String.format("DELETE FROM Wishlist WHERE Wishlist_id = '%s'", wishlistId);

		con.execUpdate(query);
		
		String queryIt = String.format(
		        "UPDATE Item SET Item_wishlist = CAST(Item_wishlist AS INT) - 1 " +
		        "WHERE Item_id = (SELECT Item_id FROM Wishlist WHERE Wishlist_id = '%s')",
		        wishlistId
		    );
		
		con.execUpdate(queryIt);
		
	}
//	Method untuk generate wishlist id
	private static String generateWishlistId() {

		String lastWishlistId = null;
		String newWishlistId = "WI001";

		String query = "SELECT Wishlist_id FROM Wishlist ORDER BY Wishlist_id DESC LIMIT 1";
		try {
			con.res = con.execQuery(query);
			if (con.res.next()) {
				lastWishlistId = con.res.getString("Wishlist_id");
			}

			if (lastWishlistId != null) {
				String numericPart = lastWishlistId.substring(2);
				int newIdNumber = Integer.parseInt(numericPart) + 1;
				newWishlistId = "WI" + String.format("%03d", newIdNumber);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return newWishlistId;

	}
}
