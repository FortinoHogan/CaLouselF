package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Item;
import view.guest.*;
import view.admin.AdminHomePage;
import view.admin.RequestedItemPage;
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
                break;
            case "login":
                LoginPage loginPage = new LoginPage(primaryStage);
                setScene(loginPage.createPageScene());
                break;
            case "admin-homepage":
            	AdminHomePage adminHomePage = new AdminHomePage(primaryStage);
            	setScene(adminHomePage.createPageScene());
            	break;
            case "requested-item-page":
            	RequestedItemPage requestedItemPage = new RequestedItemPage(primaryStage);
            	setScene(requestedItemPage.createPageScene());
            	break;
        }
    }
    
    public void switchToPageItem(Item item, String userId) {
    	EditItemPage editItemPage = new EditItemPage(primaryStage, item, userId);
    	setScene(editItemPage.createPageScene());
    }
    
    public void switchToPageSeller(String pageName, String userId) {
    	switch(pageName) {
	    	case "seller-homepage":
	        	SellerHomePage sellerHomePage = new SellerHomePage(primaryStage, userId);
	        	setScene(sellerHomePage.createPageScene());
	        	break;
	    	case "seller-item-page":
	    		SellerItemPage sellerItemPage = new SellerItemPage(primaryStage, userId);
	        	setScene(sellerItemPage.createPageScene());
	        	break;
	    	case "upload-item":
            	UploadItemPage uploadItemPage = new UploadItemPage(primaryStage, userId);
            	setScene(uploadItemPage.createPageScene());
            	break;
    	}
    	
    }
    
    public void switchToPageBuyer(String pageName, String userId) {
    	switch(pageName) {
	    	case "buyer-homepage":
	        	BuyerHomePage buyerHomePage = new BuyerHomePage(primaryStage, userId);
	        	setScene(buyerHomePage.createPageScene());
	        	break;
    	}
    }
	
}
