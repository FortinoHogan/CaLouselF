package view.guest;

import client.SceneManager;
import controller.UserController;
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
import javafx.stage.*;
import model.Page;
import model.User;

public class LoginPage extends Page{
	
	private SceneManager sceneManager;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, loginBp, bottomBp;
	private GridPane gp;
	private ScrollPane sp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem loginNavItem, registerNavItem;
	
	private Label usernameLbl, passwordLbl, titleLbl, errorLbl;
	private TextField usernameTxt;
	private PasswordField passwordTxt;
	private Button loginBtn;

	public LoginPage(Stage stage) {
		
		sceneManager = new SceneManager(stage);
		initPage();
		setAlignment();
		setHandler();
		
	}
	
	@Override
	public void initPage() {
		
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
		
		titleLbl = new Label("Login Page");
		titleLbl.setFont(new Font(24));
		usernameLbl = new Label("Username");
		passwordLbl = new Label("Password");
		errorLbl = new Label("");
		
		usernameTxt = new TextField();
		usernameTxt.setPromptText("Must be filled");
		passwordTxt = new PasswordField();
		passwordTxt.setPromptText("Must be filled");
		
		loginBtn = new Button("Login");
		
	}
	
	@Override
	public void setAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(loginBp);
		loginBp.setCenter(titleLbl);
		layoutBp.setTop(navbarBp);
		
		loginBp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		layoutBp.setCenter(sp);
		sp.setContent(gp);
		sp.setMaxWidth(width / 1.065);
	    sp.setMaxHeight(height); 
		
		gp.add(usernameLbl, 0, 0);
	    gp.add(usernameTxt, 1, 0);
	    gp.add(passwordLbl, 0, 1);
	    gp.add(passwordTxt, 1, 1);
	    
	    gp.setVgap(height / 40);
		gp.setHgap(width / 77);
		sp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		VBox bottomLayout = new VBox(height/17.5);
	    bottomLayout.setAlignment(Pos.CENTER);
	    bottomLayout.getChildren().addAll(errorLbl, loginBtn);

	    bottomBp.setCenter(bottomLayout);
	    bottomBp.setPadding(new Insets(height/17.54, width/15.36, height/17.54, width/15.36));

	    usernameTxt.setMinWidth(width / 1.3);

	    layoutBp.setBottom(bottomBp);
	    BorderPane.setAlignment(bottomLayout, Pos.CENTER);
		
	}
	
	@Override
	public void setHandler() {
		
		loginBtn.setOnAction(this::handlePage);
		
		loginNavItem.setOnAction(event -> sceneManager.switchToPage("login"));
        registerNavItem.setOnAction(event -> sceneManager.switchToPage("register"));
		
	}
	
	@Override
	public void handlePage(ActionEvent e) {
		
		if(e.getSource() == loginBtn) {
					
			String username = usernameTxt.getText().trim();
			String password = passwordTxt.getText();
			
			String errorMsg = UserController.login(username, password);
			errorLbl.setText(errorMsg);
            errorLbl.setTextFill(Color.RED);
            
            if(errorMsg.equals("admin")) {
            	sceneManager.switchToPage("admin-homepage");
            }
            
            if(errorMsg.equals("")) {
            	User user = UserController.getUserByUsername(username);
            	if(user.getRole().equals("Buyer")) {
            		sceneManager.switchToPageBuyer("buyer-homepage", user.getUserId());
            	}
            	else if(user.getRole().equals("Seller")) sceneManager.switchToPageSeller("seller-homepage", user.getUserId());
            }
			
		}
		
	}

	@Override
	public Scene createPageScene() {
		
		return new Scene(layoutBp, width, height);
		
	}
	
}
