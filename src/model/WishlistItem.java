package model;

public class WishlistItem {
	private String wishlistId, userId, itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist,
			itemOfferStatus;

	public WishlistItem(String wishlistId, String userId, String itemId, String itemName, String itemSize,
			String itemPrice, String itemCategory, String itemStatus, String itemWishlist, String itemOfferStatus) {
		super();
		this.wishlistId = wishlistId;
		this.userId = userId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus;
	}

	public String getWishlistId() {
		return wishlistId;
	}

	public String getUserId() {
		return userId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

}
