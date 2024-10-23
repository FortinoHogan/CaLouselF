package view;

import client.Main;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class RegisterPage {

	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, registerBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNavItem, registerNavItem;
	
	private Label usernameLbl, passwordLbl, phoneNumberLbl, addressLbl, rolesLbl, registerLbl, errorLbl;
	private TextField usernameTxt, phoneNumberTxt, addressTxt;
	private PasswordField passwordTxt;
	private ToggleGroup role;
	private RadioButton sellerRadio, buyerRadio;
	private Button registerBtn;
	
	public RegisterPage(Stage stage) {
	    
		this.stage = stage;
		initRegister();
		setRegisterAlignment();
		setRegisterHandler();
		
	}
	
	public void initRegister() {
		 
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
		
		registerLbl = new Label("Register Page");
		registerLbl.setFont(new Font(24));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		phoneNumberLbl = new Label("Phone Number");
		addressLbl = new Label("Address");
		rolesLbl = new Label("Roles");
		errorLbl = new Label("");
		
		usernameTxt = new TextField();
		usernameTxt.setPromptText("Must be filled and at least 3 characters");
		passwordTxt = new PasswordField();
		passwordTxt.setPromptText("Must be filled, at least 8 characters, and 1 special character");
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
	
	public void setRegisterAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(registerBp);
		registerBp.setCenter(registerLbl);
		layoutBp.setTop(navbarBp);
		
		registerBp.setPadding(new Insets(50));
		
		layoutBp.setCenter(sp);
		sp.setContent(gp);
		sp.setMaxWidth(width / 1.065);
	    sp.setMaxHeight(height / 2); 
		
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
		sp.setPadding(new Insets(50));
		
		VBox bottomLayout = new VBox(50);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(errorLbl, registerBtn);

	    bottomBp.setCenter(bottomLayout);
	    bottomBp.setPadding(new Insets(100));

	    usernameTxt.setMinWidth(width / 1.25);

	    layoutBp.setBottom(bottomBp);
	    BorderPane.setAlignment(bottomLayout, Pos.CENTER);
	}
	
	public void setRegisterHandler() {

		registerBtn.setOnAction(this::handleRegister);
		
		loginNavItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Main.changeSceneToLoginPage();
			}
		});
		
		registerNavItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Main.changeSceneToRegisterPage();
			}
		});
		
	}
	
	public void handleRegister(ActionEvent e) {
		
		if(e.getSource() == registerBtn) {
			
			String username = usernameTxt.getText().trim();
			String password = passwordTxt.getText();
			String phoneNumber = phoneNumberTxt.getText().trim();
			String address = addressTxt.getText().trim();
			String roleStr = role.getSelectedToggle() != null 
	                  ? role.getSelectedToggle().getUserData().toString() : null;
			
			String errorMsg = UserController.checkAccountValidtion(username, password, phoneNumber, address, roleStr);
			errorLbl.setText(errorMsg);
            errorLbl.setTextFill(Color.RED);
			
		}
		
	}
	
	public Scene createRegisterScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
}
