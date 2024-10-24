package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

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
                setScene(registerPage.createRegisterScene());
                break;
            case "login":
                LoginPage loginPage = new LoginPage(primaryStage);
                setScene(loginPage.createLoginScene());
                break;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
	
}
