package view.buyer;

import client.SceneManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;

public class BuyerHomePage {

	private SceneManager sceneManager;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, buyerBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNavItem, wishlistNavItem;

	private TableView<Item> table;	
	
	public BuyerHomePage(Stage stage) {
		
		sceneManager = new SceneManager(stage);
		initBuyerHomePage();
		setBuyerHomePageAlignment();
		setBuyerHomePageHandler();
		
	}
	
	private void initBuyerHomePage() {
		
		layoutBp = new BorderPane();
		navbarBp = new BorderPane();
		buyerBp = new BorderPane();
		bottomBp = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		homeNavItem = new MenuItem("Home");
		wishlistNavItem = new MenuItem("Wishlist");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, wishlistNavItem);
		
	}
	
	private void setBuyerHomePageAlignment() {
		
	}
		
	private void setBuyerHomePageHandler() {
		
	}


	
	public Scene createBuyerHomePageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
}
