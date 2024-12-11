package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import model.OfferDetail;
import util.Connect;

public class ItemController {

//	Memanggil method di model Item untuk upload item
	public static void uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice, String userId) {

		Item.uploadItem(itemName, itemCategory, itemSize, itemPrice, userId);

	}
//	Memanggil method di model Item untuk edit item
	public static void editItem(String itemId, String itemName, String itemCategory, String itemSize,
			String itemPrice) {

		Item.editItem(itemId, itemName, itemCategory, itemSize, itemPrice);

	}
//	Memanggil method di model Item untuk delete item
	public static void deleteItem(String itemId) {

		Item.deleteItem(itemId);
		
	}
//	Memanggil method di Item untuk browse item
	public static ArrayList<Item> browseItem(String _itemName) {

		return Item.browseItem(_itemName);
		
	}
//	Memanggil method di Item untuk view item
	public static ArrayList<Item> viewItem() {

		return Item.viewItem();

	}
//	Memanggil method di Item untuk check item validation 
	public static String checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {

		return Item.checkItemValidation(itemName, itemCategory, itemSize, itemPrice);

	}
//	Memanggil method di Item untuk view requested item
	public static Item viewRequestedItem(String _itemId, String _itemStatus) {

		return Item.viewRequestedItem(_itemId, _itemStatus);
		
	}
//	Memanggil method di Item untuk offer price
	public static void offerPrice(String itemId, String itemPrice, String userId) {

		Item.offerPrice(itemId, itemPrice, userId);
		
	}
//	Memanggil method di Item untuk accept offer
	public static void acceptOffer(String itemId) {

		Item.acceptOffer(itemId);
			
	}
//	Memanggil method di Item untuk decline offer
	public static void declineOffer(String itemId) {

		Item.declineItem(itemId);
		
	}
//	Memanggil method di Item untuk approve item
	public static void approveItem(String itemId) {

		Item.approveItem(itemId);
		
	}
//	Memanggil method di Item untuk decline item
	public static void declineItem(String itemId) {

		Item.declineItem(itemId);
		
	}
//	Memanggil method di Item untuk view accepted item
	public static Item viewAcceptedItem(String _itemId) {
		
		return Item.viewAcceptedItem(_itemId);

	}
//	Memanggil method di Item untuk view offer item
	public static ArrayList<OfferDetail> viewOfferItem(String userId) {

		return Item.viewOfferItem(userId);
		
	}
//	Memanggil method di Item untuk get items by user id
	public static ArrayList<Item> getItemsByUserId(String userId) {

		return Item.getItemsByUserId(userId);

	}
//	Memanggil method di Item untuk get pending items
	public static ArrayList<Item> getPendingItems(){
		
		return Item.getPendingItems();
		
	}
//	Method untuk validasi reason untuk decline harus diisi
	public static String validateDecline(String reason) {
		
		if(reason.isEmpty()) {
			return "Reason cannot be empty";
		} else {
			return "";
		}
		
	}
	
}
