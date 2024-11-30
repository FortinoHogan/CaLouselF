package view.seller;

import java.util.ArrayList;

import client.SceneManager;
import controller.ItemController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;
import model.Page;

public class SellerHomePage extends Page{

	private SceneManager sceneManager;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem uploadNavItem, homeNavItem, myItemNavItem, offerItemNavItem, logoutNavItem;
	
	private Label titleLbl, errorLbl;
	private Button editBtn, searchBtn;
	private TextField searchTxt;
	
	private TableView<Item> table;
	
	private String userId;
	
	public SellerHomePage(Stage stage, String userId) {
		
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
		uploadNavItem = new MenuItem("Upload");
		myItemNavItem = new MenuItem("My Item");
		offerItemNavItem = new MenuItem("Offer Item");
		logoutNavItem = new MenuItem("Logout");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, uploadNavItem, myItemNavItem, offerItemNavItem, logoutNavItem);
		
		titleLbl = new Label("Home Page - Seller");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		
		table = new TableView<Item>();
		
		searchTxt = new TextField();
		searchTxt.setPromptText("Search...");
		
		searchBtn = new Button("Search");
		editBtn = new Button("Edit");
		
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
		
		refreshTable(ItemController.viewItem());
		
	}
	
	public void refreshTable(ArrayList<Item> items) {

		table.getItems().clear();
		
		for (Item item : items) {
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
		
		HBox searchLayout = new HBox(width / 20);
		searchLayout.setAlignment(Pos.BOTTOM_RIGHT);
		searchLayout.getChildren().addAll(searchTxt, searchBtn);
	    
		titleBp.setBottom(searchLayout);
	
	}
	
	@Override
	public void setHandler() {
		
		searchBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-homepage", userId));
		uploadNavItem.setOnAction(event -> sceneManager.switchToPageSeller("upload-item", userId));
		myItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-item-page", userId));
		offerItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("offer-item-page", userId));
		logoutNavItem.setOnAction(event -> sceneManager.switchToPage("login"));
		
	}

	@Override
	public void handlePage(ActionEvent e) {
		
		if(e.getSource() == searchBtn) {
			refreshTable(ItemController.browseItem(searchTxt.getText()));
		}
		
	}
	
	@Override
	public Scene createPageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
	
}