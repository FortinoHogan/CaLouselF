package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.guest.*;
import view.admin.AdminHomePage;
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
                RegisterPage registerPage = new RegisterPage(primaryStage);
                setScene(registerPage.createPageScene());
//            	SellerHomePage sellerHomePage2 = new SellerHomePage(primaryStage);
//            	setScene(sellerHomePage2.createSellerHomePageScene());
                break;
            case "login":
                LoginPage loginPage = new LoginPage(primaryStage);
                setScene(loginPage.createPageScene());
                break;
            case "buyer-homepage":
            	BuyerHomePage buyerHomePage = new BuyerHomePage(primaryStage);
            	setScene(buyerHomePage.createPageScene());
            	break;
            case "seller-homepage":
            	SellerHomePage sellerHomePage = new SellerHomePage(primaryStage);
            	setScene(sellerHomePage.createPageScene());
            	break;
            case "admin-homepage":
            	AdminHomePage adminHomePage = new AdminHomePage(primaryStage);
            	setScene(adminHomePage.createPageScene());
            	break;
            case "upload-item":
            	UploadItemPage uploadItemPage = new UploadItemPage(primaryStage);
            	setScene(uploadItemPage.createPageScene());
            	break;
        }
    }
	
}
