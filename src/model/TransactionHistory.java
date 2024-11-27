package model;

public class TransactionHistory {

	private String transactionId, itemName, itemCategory, itemSize, itemPrice;

	public TransactionHistory(String transactionId, String itemName, String itemCategory, String itemSize,
			String itemPrice) {
		super();
		this.transactionId = transactionId;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
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
	
	

}
