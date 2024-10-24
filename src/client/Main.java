package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static SceneManager sceneManager;
	
	public static void main(String[] args) {

		launch(args);
		
	}
	
	@Override
	public void start(Stage pS) throws Exception {
		
		sceneManager = new SceneManager(pS);
		sceneManager.switchToPage("register");
		
		pS.setTitle("CaLouselF");
		pS.show();
		
	}
	
}
