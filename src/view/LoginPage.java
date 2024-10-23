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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

public class LoginPage {
	
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, loginBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNavItem, registerNavItem;
	
	private Label usernameLbl, passwordLbl, loginLbl, errorLbl;
	private TextField usernameTxt;
	private PasswordField passwordTxt;
	private Button loginBtn;

	public LoginPage(Stage stage) {
		
		this.stage = stage;
		initLogin();
		setLoginAlignment();
		setLoginHandler();
		
	}
	
	public void initLogin() {
		
		layoutBp = new BorderPane();
		navbarBp = new BorderPane();
		loginBp = new BorderPane();
		bottomBp = new BorderPane();
		
		gp = new GridPane();
		sp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		loginNavItem = new MenuItem("Login");
		registerNavItem = new MenuItem("Register");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(loginNavItem, registerNavItem);
		
		loginLbl = new Label("Login Page");
		loginLbl.setFont(new Font(24));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		errorLbl = new Label("");
		
		usernameTxt = new TextField();
		usernameTxt.setPromptText("Must be filled");
		passwordTxt = new PasswordField();
		passwordTxt.setPromptText("Must be filled");
		
		loginBtn = new Button("Login");
		
	}
	
	public void setLoginAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(loginBp);
		loginBp.setCenter(loginLbl);
		layoutBp.setTop(navbarBp);
		
		loginBp.setPadding(new Insets(50));
		
		layoutBp.setCenter(sp);
		sp.setContent(gp);
		sp.setMaxWidth(width / 1.065);
	    sp.setMaxHeight(height / 2); 
		
		gp.add(usernameLbl, 0, 0);
	    gp.add(usernameTxt, 1, 0);
	    gp.add(passwordLbl, 0, 1);
	    gp.add(passwordTxt, 1, 1);
	    
	    gp.setVgap(height / 40);
		gp.setHgap(width / 77);
		sp.setPadding(new Insets(50));
		
		VBox bottomLayout = new VBox(50);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(errorLbl, loginBtn);

	    bottomBp.setCenter(bottomLayout);
	    bottomBp.setPadding(new Insets(100));

	    usernameTxt.setMinWidth(width / 1.25);

	    layoutBp.setBottom(bottomBp);
	    BorderPane.setAlignment(bottomLayout, Pos.CENTER);
		
	}
	
	public void setLoginHandler() {
		
		loginBtn.setOnAction(this::handleLogin);
		
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
	
	public void handleLogin(ActionEvent e) {
		
		if(e.getSource() == loginBtn) {
					
			String username = usernameTxt.getText().trim();
			String password = passwordTxt.getText();
			
			String errorMsg = UserController.login(username, password);
			errorLbl.setText(errorMsg);
            errorLbl.setTextFill(Color.RED);
			
		}
		
	}
	
	public Scene createLoginScene() {
		
		return new Scene(layoutBp, width, height);
		
	}
	
}
