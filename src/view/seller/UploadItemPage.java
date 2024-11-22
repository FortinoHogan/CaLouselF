package view.seller;

import client.SceneManager;
import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Page;

public class UploadItemPage extends Page{

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
	
	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, validateLbl;
	private TextField nameTxt, categoryTxt, sizeTxt, priceTxt;
	private Button uploadBtn;
	
	public UploadItemPage(Stage stage) {
		
		sceneManager = new SceneManager(stage);
		initPage();
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
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, uploadNavItem);
		
		titleLbl = new Label("Upload Item");
		titleLbl.setFont(new Font(24));
		nameLbl = new Label("Item Name");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		priceLbl = new Label("Item Price");
		validateLbl = new Label("");
		
		nameTxt = new TextField();
		nameTxt.setPromptText("Must be filled and at least 3 characters long");
		categoryTxt = new TextField();
		categoryTxt.setPromptText("Must be filled and at least 3 characters long");
		sizeTxt = new TextField();
		sizeTxt.setPromptText("Must be filled");
		priceTxt = new TextField();
		priceTxt.setPromptText("Must be filled with numbers and cannot be 0");
		
		uploadBtn = new Button("Upload");
		
	}

	@Override
	public void setAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(titleBp);
		titleBp.setCenter(titleLbl);
		layoutBp.setTop(navbarBp);
		layoutBp.setCenter(sp);
		layoutBp.setBottom(bottomBp);
		
		layoutBp.setCenter(sp);
		sp.setContent(gp);
		sp.setMaxWidth(width / 1.065);
	    sp.setMaxHeight(height); 
		
		gp.add(nameLbl, 0, 0);
	    gp.add(nameTxt, 1, 0);
	    gp.add(categoryLbl, 0, 1);
	    gp.add(categoryTxt, 1, 1);
	    gp.add(sizeLbl, 0, 2);
	    gp.add(sizeTxt, 1, 2);
	    gp.add(priceLbl, 0, 3);
	    gp.add(priceTxt, 1, 3);
	    gp.add(uploadBtn, 1, 4);
	    
	    gp.setVgap(height / 40);
		gp.setHgap(width / 77);
		sp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		VBox bottomLayout = new VBox(height/17.5);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(validateLbl, uploadBtn);
	    
	    bottomBp.setCenter(bottomLayout);
	    bottomBp.setPadding(new Insets(height/17.54, width/15.36, height/17.54, width/15.36));
		
		nameTxt.setMinWidth(width / 1.3);
	    
		titleBp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
	}
	
	@Override
	public void setHandler() {
		
		uploadBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPage("seller-homepage"));
		uploadNavItem.setOnAction(event -> sceneManager.switchToPage("upload-item"));
		
	}
	
	@Override
	public void handlePage(ActionEvent e) {
		
		if(e.getSource() == uploadBtn) {
			
			String name = nameTxt.getText().trim();
			String category = categoryTxt.getText().trim();
			String size = sizeTxt.getText().trim();
			String price = priceTxt.getText().trim();
			
			String errorMsg = ItemController.checkItemValidation(name, category, size, price);
			if(errorMsg.equals("")) {
				ItemController.uploadItem(name, category, size, price);
				validateLbl.setText("Item Uploaded");
	            validateLbl.setTextFill(Color.GREEN);
	            nameTxt.setText("");
	            categoryTxt.setText("");
	            sizeTxt.setText("");
	            priceTxt.setText("");
			} else {
				validateLbl.setText(errorMsg);
	            validateLbl.setTextFill(Color.RED);
			}
			
		}
		
	}
	
	@Override
	public Scene createPageScene() {
		
		return new Scene(layoutBp, width, height);
		
	}
	
}
