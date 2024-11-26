package model;

import javafx.event.ActionEvent;
import javafx.scene.Scene;

public abstract class Page {

	
	
	public abstract void initPage();
	public abstract void setAlignment();
	public abstract void setHandler();
	public abstract void handlePage(ActionEvent e);
	public abstract Scene createPageScene();
	
}
