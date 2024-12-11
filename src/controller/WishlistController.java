package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Wishlist;
import model.WishlistItem;
import util.Connect;

public class WishlistController {

//	Memanggil method di Wishlist untuk view wishlist
	public static ArrayList<WishlistItem> viewWishlist(String wishlist_Id, String user_Id) {
		
		return Wishlist.viewWishlist(wishlist_Id, user_Id);
		
	}
//	Memanggil method di Wishlist untuk add wishlist
	public static void addWishlist(String itemId, String userId) {
		
		Wishlist.addWishlist(itemId, userId);
		
	}
//	Memanggil method di Wishlist untuk remove wishlist
	public static void removeWishlist(String wishlistId) {
		
		Wishlist.removeWishlist(wishlistId);
		
	}
	
}
