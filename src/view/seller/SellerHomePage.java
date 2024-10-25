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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;

public class SellerHomePage {

	private SceneManager sceneManager;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem uploadNavItem, homeNavItem;
	
	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, errorLbl;
	private TextField nameTxt, categoryTxt, sizeTxt, priceTxt;
	private Button uploadBtn;
	
	private TableView<Item> table;
	
	public SellerHomePage(Stage stage) {
		
		sceneManager = new SceneManager(stage);
		initSellerHomePage();
		initTable();
		setSellerHomePageAlignment();
		setSellerHomePageHandler();
		
	}

	private void initSellerHomePage() {
		
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
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, uploadNavItem);
		
		titleLbl = new Label("Home Page");
		titleLbl.setFont(new Font(24));
		nameLbl = new Label("Item Name");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		priceLbl = new Label("Item Price");
		errorLbl = new Label("");
		
		nameTxt = new TextField();
		nameTxt.setPromptText("Must be filled and at least 3 characters long");
		categoryTxt = new TextField();
		categoryTxt.setPromptText("Must be filled and at least 3 characters long");
		sizeTxt = new TextField();
		sizeTxt.setPromptText("Must be filled");
		priceTxt = new TextField();
		priceTxt.setPromptText("Must be filled with numbers more than 0");
		
		uploadBtn = new Button("Upload");
		
		table = new TableView<Item>();
		
	}
	
	private void initTable() {
		
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
	
	private void setSellerHomePageAlignment() {
		
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
	
	private void setSellerHomePageHandler() {
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPage("seller-homepage"));
		uploadNavItem.setOnAction(event -> sceneManager.switchToPage("upload-item"));
		
	}

	
	public Scene createSellerHomePageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
	
}