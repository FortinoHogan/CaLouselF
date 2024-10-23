package client;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginPage;
import view.RegisterPage;

public class Main extends Application {
	
	private static Stage primaryStage;
	
	public static void main(String[] args) {

		launch(args);
		
	}
	
	@Override
	public void start(Stage pS) throws Exception {
		
		primaryStage = pS;
		
		changeSceneToRegisterPage();
		
		pS.setTitle("CaLouselF");
		pS.show();
		
	}

	public static void changeSceneToRegisterPage() {
        RegisterPage registerPage = new RegisterPage(primaryStage);
        primaryStage.setScene(registerPage.createRegisterScene());
    }

    public static void changeSceneToLoginPage() {
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setScene(loginPage.createLoginScene());
    }
	
}
