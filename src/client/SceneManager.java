package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.guest.*;
import view.buyer.*;
import view.seller.*;

public class SceneManager {

	private Stage primaryStage;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public void switchToPage(String pageName) {
        switch (pageName) {
            case "register":
//                RegisterPage registerPage = new RegisterPage(primaryStage);
//                setScene(registerPage.createRegisterScene());
            	SellerHomePage sellerHomePage2 = new SellerHomePage(primaryStage);
            	setScene(sellerHomePage2.createSellerHomePageScene());
                break;
            case "login":
                LoginPage loginPage = new LoginPage(primaryStage);
                setScene(loginPage.createLoginScene());
                break;
            case "buyer-homepage":
            	BuyerHomePage buyerHomePage = new BuyerHomePage(primaryStage);
            	setScene(buyerHomePage.createBuyerHomePageScene());
            	break;
            case "seller-homepage":
            	SellerHomePage sellerHomePage = new SellerHomePage(primaryStage);
            	setScene(sellerHomePage.createSellerHomePageScene());
            	break;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
	
}
