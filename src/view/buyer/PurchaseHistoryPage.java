package view.buyer;

import java.util.ArrayList;

import client.SceneManager;
import controller.ItemController;
import controller.TransactionController;
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
import model.TransactionHistory;

public class PurchaseHistoryPage extends Page{
	private SceneManager sceneManager;
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
	
	private TableView<TransactionHistory> table;
	
	private String userId;
	
	public PurchaseHistoryPage(Stage stage, String userId) {
		
		this.userId = userId;	
		sceneManager = new SceneManager(stage);
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
		
		titleLbl = new Label("Purchase History");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		
		table = new TableView<TransactionHistory>();
	}
	
	public void initTable() {

	    TableColumn<TransactionHistory, String> transactionIdCol = new TableColumn<>("Transaction ID");
	    transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
	    transactionIdCol.setMinWidth((width / 1.065) / 5);

	    TableColumn<TransactionHistory, String> nameCol = new TableColumn<>("Name");
	    nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
	    nameCol.setMinWidth((width / 1.065) / 5);

	    TableColumn<TransactionHistory, String> categoryCol = new TableColumn<>("Category");
	    categoryCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
	    categoryCol.setMinWidth((width / 1.065) / 5);

	    TableColumn<TransactionHistory, String> sizeCol = new TableColumn<>("Size");
	    sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
	    sizeCol.setMinWidth((width / 1.065) / 5);

	    TableColumn<TransactionHistory, String> priceCol = new TableColumn<>("Price");
	    priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
	    priceCol.setMinWidth((width / 1.065) / 5);

	    table.getColumns().addAll(transactionIdCol, nameCol, categoryCol, sizeCol, priceCol);

	    ArrayList<TransactionHistory> items = TransactionController.viewHistory(userId);
	    table.getItems().addAll(items);
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
	
	}
	
	@Override
	public void setHandler() {
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("buyer-homepage", userId));
		wishlistNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("wishlist-page", userId));
		historyNavItem.setOnAction(event -> sceneManager.switchToPageBuyer("purchase-history-page", userId));
		
	}

	@Override
	public void handlePage(ActionEvent e) {
		
	}
	
	@Override
	public Scene createPageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
	
}