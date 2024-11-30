package controller;

import java.sql.SQLException;
import util.Connect;

public class OfferController {

	private static Connect con = Connect.getInstance();
	
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
	
	public static void updateOffer(String userId, String itemId, String offerPrice) {
		
		String query = "UPDATE Offer SET User_id = '" + userId + "', Offer_price = '" + offerPrice + "' WHERE Item_id = '" + itemId  + "'";
		
		con.execUpdate(query);
		
		String queryIt = String.format("UPDATE Item SET Item_offer_status = 'Offering' WHERE Item_id = '%s'", itemId);
		
		con.execUpdate(queryIt);
		
	}

}
