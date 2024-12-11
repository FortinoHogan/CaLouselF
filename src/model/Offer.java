package model;

import java.sql.SQLException;

import util.Connect;

public class Offer {
	private String offerId, userId, itemId, offerPrice, offerStatus;

	public Offer(String offerId, String userId, String itemId, String offerPrice, String offerStatus) {
		super();
		this.offerId = offerId;
		this.userId = userId;
		this.itemId = itemId;
		this.offerPrice = offerPrice;
		this.offerStatus = offerStatus;
	}

	public String getOfferId() {
		return offerId;
	}

	public String getUserId() {
		return userId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}
//	Memanggil instance database	
	private static Connect con = Connect.getInstance();

//	Method untuk mengambil offer price dari suatu item
	public static String checkLatestOfferPrice(String itemId) {
		
		String query = "SELECT Offer_price FROM Offer WHERE Item_id = '" + itemId + "'";
		String latestOfferPrice = null;
		try {
			con.res = con.execQuery(query);
			if(con.res.next()) {
				latestOfferPrice = con.res.getString("Offer_price");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return latestOfferPrice;
		
	}
//	Method untuk validasi offer price 
	public static String checkOfferPriceValidation(String itemId, String offerPrice) {
		
		String latestOfferPrice = checkLatestOfferPrice(itemId);
		if (offerPrice.isEmpty()) {
			return "Offer price cannot be empty";
		} else if (Integer.parseInt(offerPrice) <= 0){
			return "Offer price must be more than 0";
		} else if (latestOfferPrice != null && Integer.parseInt(latestOfferPrice) >= Integer.parseInt(offerPrice)) {
			return "Offer price cannot be lower than the highest offer";
		} 
		
		return "";
	}
//	Method untuk update offer price suatu item
	public static void updateOffer(String userId, String itemId, String offerPrice) {
		
		String query = "UPDATE Offer SET User_id = '" + userId + "', Offer_price = '" + offerPrice + "' WHERE Item_id = '" + itemId  + "'";
		
		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_offer_status = 'Offering' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}

}
