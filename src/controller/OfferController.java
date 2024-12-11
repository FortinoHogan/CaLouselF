package controller;

import java.sql.SQLException;

import model.Offer;
import util.Connect;

public class OfferController {
	
//	Memanggil method di Offer untuk check latest offer
	public static String checkLatestOfferPrice(String itemId) {
		
		return Offer.checkLatestOfferPrice(itemId);
		
	}
//	Memanggil method di Offer untuk check offer price validation	
	public static String checkOfferPriceValidation(String itemId, String offerPrice) {
		
		return Offer.checkOfferPriceValidation(itemId, offerPrice);
		
	}
//	Memanggil method di Offer untuk update offer
	public static void updateOffer(String userId, String itemId, String offerPrice) {
		
		Offer.updateOffer(userId, itemId, offerPrice);
		
	}

}
