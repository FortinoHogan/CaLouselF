package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Offer;
import util.Connect;

public class OfferController {

	private static Connect con = Connect.getInstance();
	
	public static String checkLatestOfferPrice(String itemId) {
		String query = "SELECT Offer_price FROM offer WHERE Item_id = "+ itemId +" ORDER BY Offer_price DESC";
		String latestOfferPrice = "";
		try {
		latestOfferPrice = con.res.getString("Offer_price");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return latestOfferPrice;
	}
	
	public static String checkOfferPriceValidation(String itemId, String offerPrice) {
		String latestOfferPrice = OfferController.checkLatestOfferPrice(itemId);
		if (offerPrice.isEmpty()) {
			return "Offer price cannot be empty";
		} else if (Integer.parseInt(offerPrice) > 0){
			return "Offer price must be more than 0";
		} else if (Integer.parseInt("latestOfferPrice") > Integer.parseInt(offerPrice)) {
			return "Offer price cannot be lower than the highest offer.";
		}
		else {
			return "";
		}
	}
	
	public static void createOffer(String userId, String itemId, String offerPrice) {

		String query = "INSERT INTO Transaction(Offer_id, User_id, Item_id, Offer_price, Offer_status) " + "VALUES ('"
				+ generateOfferId() + "', '" + userId + "', '" + itemId +  "', '" + offerPrice + "', 'Pending')";

		con.execUpdate(query);
	}
	
	public static void updateOffer(String userId, String itemId, String offerPrice) {
		String query = "UPDATE offer SET User_id = " + userId + ", Offer_price = " + offerPrice + " WHERE Item_id = " + itemId;
		con.execUpdate(query);
	}

	private static String generateOfferId() {

		String lastOfferId = null;
		String newOfferId = "OF001";

		String query = "SELECT Offer_id FROM Offer ORDER BY Offer_id DESC LIMIT 1";
		try {
			con.res = con.execQuery(query);
			if (con.res.next()) {
				lastOfferId = con.res.getString("Transaction_id");
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
