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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;
import model.Page;
import model.Wishlist;
import model.WishlistItem;

public class WishlistPage extends Page{
	private SceneManager sceneManager;
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNavItem, wishlistNavItem, historyNavItem;

	private Label titleLbl, errorLbl;
	private Button removeBtn;
	
	private TableView<WishlistItem> table;	
	
	private String userId;
	
	public WishlistPage(Stage stage, String userId) {
		
		sceneManager = new SceneManager(stage);
		this.userId = userId;
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
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		homeNavItem = new MenuItem("Home");
		wishlistNavItem = new MenuItem("Wishlist");
		historyNavItem = new MenuItem("Purchase History");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, wishlistNavItem, historyNavItem);
		
		titleLbl = new Label("Wishlist");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		
		removeBtn = new Button("Remove form wishlist");
		
		table = new TableView<WishlistItem>();
	}
	
	public void initTable() {
		
		TableColumn<WishlistItem, String>  nameCol = new TableColumn<WishlistItem, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemName"));
		nameCol.setMinWidth((width/1.065)/4);
		
		TableColumn<WishlistItem, String>  categoryCol = new TableColumn<WishlistItem, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemCategory"));
		categoryCol.setMinWidth((width/1.065)/4);
		
		TableColumn<WishlistItem, String>  sizeCol = new TableColumn<WishlistItem, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemSize"));
		sizeCol.setMinWidth((width/1.065)/4);
		
		TableColumn<WishlistItem, String>  priceCol = new TableColumn<WishlistItem, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<WishlistItem, String>("itemPrice"));
		priceCol.setMinWidth((width/1.065)/4);

		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);

        refreshTable();
	}
	
	public void refreshTable() {

		table.getItems().clear();
		
		ArrayList<WishlistItem> wishlist = WishlistController.viewWishlist(null, userId);
//		ArrayList<Item> items = new ArrayList<>();
//		for (Wishlist wish : wishlist) {
//			items.add(ItemController.getItemsByItemId(wish.getItemId()));
//		}
		
		for (WishlistItem wish : wishlist) {
			table.getItems().add(wish);
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
		
		VBox bottomLayout = new VBox(height/17.5);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(errorLbl, removeBtn);
	    
		bottomBp.setCenter(bottomLayout);
		bottomBp.setPadding(new Insets(height / 17.54, width / 15.36, height / 17.54, width / 15.36));
	}
		
	@Override
	public void setHandler() {
	
		removeBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("buyer-homepage", userId));
		wishlistNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("wishlist-page", userId));
		historyNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("purchase-history-page", userId));
		
	}


	@Override
	public void handlePage(ActionEvent e) {
		
		WishlistItem wishlist = null;
		TableSelectionModel<WishlistItem> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.SINGLE);
		wishlist = tsm.getSelectedItem();
		
		if(wishlist != null) {
			if (e.getSource() == removeBtn) {
				WishlistController.removeWishlist(wishlist.getWishlistId());
				refreshTable();
				errorLbl.setText("Item Removed From Wishlist");
				errorLbl.setTextFill(Color.GREEN);
			}
		} else if(wishlist == null) {
			errorLbl.setText("Item Not Selected");
			errorLbl.setTextFill(Color.RED);
		}
	}
	
	@Override
	public Scene createPageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
}
