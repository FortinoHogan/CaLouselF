package view.guest;

import client.SceneManager;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Page;

public class RegisterPage extends Page{

	private SceneManager sceneManager;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, registerBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNavItem, registerNavItem;
	
	private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, rolesLbl, titleLbl, validateLbl;
	private TextField usernameTxt, phoneNumberTxt, addressTxt;
	private PasswordField passwordTxt;
	private ToggleGroup role;
	private RadioButton sellerRadio, buyerRadio;
	private Button registerBtn;
	
	public RegisterPage(Stage stage) {
	    
		sceneManager = new SceneManager(stage);
		initPage();
		setAlignment();
		setHandler();
		
	}
	
	@Override
	public void initPage() {
		
		layoutBp = new BorderPane();
		navbarBp = new BorderPane();
		registerBp = new BorderPane();
		bottomBp = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		loginNavItem = new MenuItem("Login");
		registerNavItem = new MenuItem("Register");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(loginNavItem, registerNavItem);
		
		titleLbl = new Label("Register Page");
		titleLbl.setFont(new Font(24));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		phoneNumberLbl = new Label("Phone Number");
		addressLbl = new Label("Address");
		rolesLbl = new Label("Roles");
		validateLbl = new Label("");
		
		usernameTxt = new TextField();
		usernameTxt.setPromptText("Must be filled and at least 3 characters long");
		passwordTxt = new PasswordField();
		passwordTxt.setPromptText("Must be filled, at least 8 characters long, and has 1 special character");
		phoneNumberTxt = new TextField();
		phoneNumberTxt.setPromptText("Must contain +62 and 10 numbers long");
		addressTxt = new TextField();
		addressTxt.setPromptText("Must be filled");
		
		role = new ToggleGroup();
		sellerRadio = new RadioButton("Seller");
		sellerRadio.setToggleGroup(role);
		sellerRadio.setUserData("Seller");
		buyerRadio = new RadioButton("Buyer");
		buyerRadio.setToggleGroup(role);
		buyerRadio.setUserData("Buyer");
		
		registerBtn = new Button("Register");
	}
	
	@Override
	public void setAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(registerBp);
		registerBp.setCenter(titleLbl);
		layoutBp.setTop(navbarBp);
		layoutBp.setBottom(bottomBp);
		
		registerBp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		layoutBp.setCenter(sp);
		sp.setContent(gp);
		sp.setMaxWidth(width / 1.065);
	    sp.setMaxHeight(height); 
		
		gp.add(usernameLbl, 0, 0);
	    gp.add(usernameTxt, 1, 0);
	    gp.add(passwordLbl, 0, 1);
	    gp.add(passwordTxt, 1, 1);
	    gp.add(phoneNumberLbl, 0, 2);
	    gp.add(phoneNumberTxt, 1, 2);
	    gp.add(addressLbl, 0, 3);
	    gp.add(addressTxt, 1, 3);
	    gp.add(rolesLbl, 0, 4);
	    gp.add(buyerRadio, 1, 4);
	    gp.add(sellerRadio, 1, 5);
	    
	    gp.setVgap(height / 40);
		gp.setHgap(width / 77);
		sp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		VBox bottomLayout = new VBox(height/17.5);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(validateLbl, registerBtn);

	    bottomBp.setCenter(bottomLayout);
	    bottomBp.setPadding(new Insets(height/17.54, width/15.36, height/17.54, width/15.36));

	    usernameTxt.setMinWidth(width / 1.3);

	    BorderPane.setAlignment(bottomLayout, Pos.CENTER);
	}
	
	@Override
	public void setHandler() {

		registerBtn.setOnAction(this::handlePage);
		
		loginNavItem.setOnAction(event -> sceneManager.switchToPage("login"));
        registerNavItem.setOnAction(event -> sceneManager.switchToPage("register"));
		
	}
	
	@Override
	public void handlePage(ActionEvent e) {
		
		if(e.getSource() == registerBtn) {
			
			String username = usernameTxt.getText().trim();
			String password = passwordTxt.getText();
			String phoneNumber = phoneNumberTxt.getText().trim();
			String address = addressTxt.getText().trim();
			String roleStr = role.getSelectedToggle() != null 
	                  ? role.getSelectedToggle().getUserData().toString() : null;
			
			String errorMsg = UserController.checkAccountValidtion(username, password, phoneNumber, address, roleStr);
			if(errorMsg.equals("")) {
				UserController.register(username, password, phoneNumber, address, roleStr);
				validateLbl.setText("Register Success");
	            validateLbl.setTextFill(Color.GREEN);
	            usernameTxt.setText("");
	            passwordTxt.setText("");
	            phoneNumberTxt.setText("");
	            addressTxt.setText("");
	            role.selectToggle(null);
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
