package view.buyer;

import java.util.ArrayList;

import client.SceneManager;
import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;
import model.Page;

public class BuyerHomePage extends Page{

	private SceneManager sceneManager;
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp, purchaseConfirmationBp, detailBp;
	private GridPane gp;
	private ScrollPane sp;
	private FlowPane fp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNavItem, wishlistNavItem, historyNavItem;

	private Label titleLbl, errorLbl, confirmLbl, nameLbl, priceLbl, categoryLbl, sizeLbl, nameTxtLbl, priceTxtLbl, categoryTxtLbl, sizeTxtLbl;
	private Button offerBtn, confirmBtn, cancelBtn, buyBtn, addToWishlistBtn, confirmDetailBtn, cancelDetailBtn;
	
	private TableView<Item> table;	
	private Item detail;
	
	private Popup purchaseConfirmation, detailPopup;
	
	private String userId;
	
	public BuyerHomePage(Stage stage, String userId) {
		
		sceneManager = new SceneManager(stage);
		this.userId = userId;
		this.stage = stage;
		initPage();
		initTable();
		setAlignment();
		setHandler();
		
	}
	
	@Override
	public void initPage() {
		
		layoutBp = new BorderPane();
		navbarBp = new BorderPane();
		titleBp = new BorderPane();
		bottomBp = new BorderPane();
		purchaseConfirmationBp = new BorderPane();
		detailBp = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		fp = new FlowPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		homeNavItem = new MenuItem("Home");
		wishlistNavItem = new MenuItem("Wishlist");
		historyNavItem = new MenuItem("Purchase History");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, wishlistNavItem, historyNavItem);
		
		titleLbl = new Label("Home Page - Buyer");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		confirmLbl = new Label("Are you sure want to purchase this item?");
		confirmLbl.setFont(new Font(18));
		nameLbl = new Label("Item Name");
		priceLbl = new Label("Item Price");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		nameTxtLbl = new Label("");
		priceTxtLbl = new Label("");
		categoryTxtLbl = new Label("");
		sizeTxtLbl = new Label("");
		
		offerBtn = new Button("Make Offer");
		confirmBtn = new Button("Confirm");
		cancelBtn = new Button("Cancel");
		buyBtn = new Button("Buy");
		addToWishlistBtn = new Button("Add to wishlist");
		confirmDetailBtn = new Button("Confirm");
		cancelDetailBtn = new Button("Cancel");
		
		table = new TableView<Item>();
		
		purchaseConfirmation = new Popup();
		detailPopup = new Popup();
	}
	
	public void initTable() {
		
		TableColumn<Item, String>  nameCol = new TableColumn<Item, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  categoryCol = new TableColumn<Item, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		categoryCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  sizeCol = new TableColumn<Item, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		sizeCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  priceCol = new TableColumn<Item, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
		priceCol.setMinWidth((width/1.065)/4);
		
		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
		
		ArrayList<Item> items = ItemController.viewItem();
		
		for(Item item : items) {
			table.getItems().add(item);
		}
		
	}
	
	@Override
	public void setAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(titleBp);
		titleBp.setCenter(titleLbl);
		layoutBp.setTop(navbarBp);
		layoutBp.setCenter(table);
		layoutBp.setBottom(bottomBp);
	    
		titleBp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		table.setMaxWidth(width/1.065);
		table.setMaxHeight(height/2);
		
		fp.getChildren().add(confirmBtn);
		fp.getChildren().add(cancelBtn);
		fp.setHgap(20);
		
		purchaseConfirmationBp.setMinWidth(width/30);
		purchaseConfirmationBp.setMinHeight(height/15);
		purchaseConfirmation.getContent().add(purchaseConfirmationBp);
		
		purchaseConfirmationBp.setTop(confirmLbl);
		purchaseConfirmationBp.setCenter(fp);
		purchaseConfirmationBp.setStyle(" -fx-background-color: gray;");
		
		VBox bottomLayout = new VBox(height / 17.5);
		HBox buttonLayout = new HBox(width / 20);
		bottomLayout.setAlignment(Pos.CENTER);
		buttonLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(errorLbl, buttonLayout);
		buttonLayout.getChildren().addAll(buyBtn, addToWishlistBtn);
	    
		bottomBp.setCenter(bottomLayout);
		bottomBp.setPadding(new Insets(height / 17.54, width / 15.36, height / 17.54, width / 15.36));
		
		confirmLbl.setStyle("-fx-text-fill: white;");
		fp.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(buyBtn, Pos.CENTER);
		BorderPane.setAlignment(confirmLbl, Pos.CENTER);
		BorderPane.setAlignment(purchaseConfirmationBp, Pos.CENTER);
		BorderPane.setMargin(confirmLbl, new Insets(0,0,20,0));
		purchaseConfirmationBp.setPadding(new Insets(20));
		
		detailBp.setMinWidth(200);
		detailBp.setMinHeight(150);
		detailPopup.getContent().add(detailBp);
		detailBp.setStyle(" -fx-background-color: gray;");
		detailBp.setCenter(sp);
		sp.setContent(gp);
		
		gp.add(nameLbl, 0, 0);
		gp.add(nameTxtLbl, 1, 0);
		gp.add(categoryLbl, 0, 1);
		gp.add(categoryTxtLbl, 1, 1);
		gp.add(sizeLbl, 0, 2);
		gp.add(sizeTxtLbl, 1, 2);
		gp.add(priceLbl, 0, 3);
		gp.add(priceTxtLbl, 1, 3);
		
		gp.setHgap(10);
		gp.setVgap(10);
		
		HBox detailButtonLayout = new HBox(width/20);
		detailButtonLayout.setAlignment(Pos.CENTER);
		detailButtonLayout.getChildren().addAll(cancelDetailBtn, confirmDetailBtn);
		BorderPane.setMargin(buttonLayout, new Insets(20,0,0,0));
		detailBp.setBottom(detailButtonLayout);
	}
		
	@Override
	public void setHandler() {
		
		addToWishlistBtn.setOnAction(this::handlePage);
		buyBtn.setOnAction(this::handlePage);
		confirmBtn.setOnAction(this::handlePage);
		cancelBtn.setOnAction(this::handlePage);
		confirmDetailBtn.setOnAction(this::handlePage);
		cancelDetailBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("buyer-homepage", userId));
		wishlistNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("wishlist-page", userId));
		historyNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("purchase-history-page", userId));
		
	}


	@Override
	public void handlePage(ActionEvent e) {
		
		Item item = null;
		TableSelectionModel<Item> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.SINGLE);
		item = tsm.getSelectedItem();
		
		if(item != null) {
			if(e.getSource() == addToWishlistBtn) {
				detailPopup.show(stage);
				detail = ItemController.viewAcceptedItem(item.getItemId());
				nameTxtLbl.setText(item.getItemName());
				priceTxtLbl.setText(item.getItemPrice());
				categoryTxtLbl.setText(item.getItemCategory());
				sizeTxtLbl.setText(item.getItemSize());
			} else if(e.getSource() == confirmDetailBtn) {
				WishlistController.addWishlist(item.getItemId(), userId);
				errorLbl.setText("Added to wishlist");
				errorLbl.setTextFill(Color.GREEN);
				detailPopup.hide();
			} else if(e.getSource() == cancelDetailBtn) {
				detailPopup.hide();
			}
			else if(e.getSource() == buyBtn) {
				purchaseConfirmation.show(stage);
			} else if (e.getSource() == confirmBtn) {
				TransactionController.purchaseItem(userId, item.getItemId());
				errorLbl.setText("Purchase Success");
				errorLbl.setTextFill(Color.GREEN);
				purchaseConfirmation.hide();
			} else if (e.getSource() == cancelBtn) {
				purchaseConfirmation.hide();
			}
		} else if(item == null) {
			errorLbl.setText("Item Not Selected");
			errorLbl.setTextFill(Color.RED);
		}
	}
	
	@Override
	public Scene createPageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
}
