package view.admin;

import java.util.ArrayList;

import client.SceneManager;
import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Item;
import model.Page;

public class RequestedItemPage extends Page{

	private SceneManager sceneManager;
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp, detailBp, reasonBp;
	private GridPane gp, reasonGp;
	private ScrollPane sp, reasonSp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem homeNavItem, viewRequestedItemNavItem;
	
	private Label titleLbl, errorLbl, errorReasonLbl, reasonLbl, nameLbl, priceLbl, categoryLbl, sizeLbl, nameTxtLbl, priceTxtLbl, categoryTxtLbl, sizeTxtLbl;
	private TextField reasonTxt;
	
	private TableView<Item> table;
	private Item detail;
	
	private Button detailBtn, cancelBtn, approveBtn, declineBtn, submitBtn;
	
	private Popup detailPopup, reasonPopup;
	
	public RequestedItemPage(Stage stage) {
		
		sceneManager = new SceneManager(stage);
		this.stage = stage;
		initPage();
		initTable();
		setAlignment();
		setHandler();
		
	}

	@Override
	public void initPage() {
		
		layoutBp = new BorderPane();
		navbarBp = new BorderPane();
		titleBp = new BorderPane();
		bottomBp = new BorderPane();
		detailBp = new BorderPane();
		reasonBp = new BorderPane();
		
		gp = new GridPane();
		reasonGp = new GridPane();
		sp = new ScrollPane();
		reasonSp = new ScrollPane();
		
		navbar = new MenuBar();
		menu = new Menu("Action");
		homeNavItem = new MenuItem("Home");
		viewRequestedItemNavItem = new MenuItem("Requested Item");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, viewRequestedItemNavItem);
		
		titleLbl = new Label("Requested Item Page");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		errorReasonLbl = new Label("");
		nameLbl = new Label("Item Name");
		priceLbl = new Label("Item Price");
		categoryLbl = new Label("Item Category");
		sizeLbl = new Label("Item Size");
		nameTxtLbl = new Label("");
		priceTxtLbl = new Label("");
		categoryTxtLbl = new Label("");
		sizeTxtLbl = new Label("");
		reasonLbl = new Label("Reason");
		reasonTxt = new TextField();
		
		table = new TableView<Item>();
		
		detailBtn = new Button("View Detail");
		cancelBtn = new Button("Cancel");
		approveBtn = new Button("Approve");
		declineBtn = new Button("Decline");
		submitBtn = new Button("Submit");
		
		detailPopup = new Popup();
		reasonPopup = new Popup();
	}
	
	private void initTable() {
		
		TableColumn<Item, String>  nameCol = new TableColumn<Item, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  categoryCol = new TableColumn<Item, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		categoryCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  sizeCol = new TableColumn<Item, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		sizeCol.setMinWidth((width/1.065)/4);
		
		TableColumn<Item, String>  priceCol = new TableColumn<Item, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
		priceCol.setMinWidth((width/1.065)/4);
		
		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);
		
		refreshTable();
		
	}
	
	public void refreshTable() {

		table.getItems().clear();
		
		ArrayList<Item> items = ItemController.getPendingItems();

		for (Item item : items) {
			table.getItems().add(item);
		}

	}

	
	@Override
	public void setAlignment() {
		
		navbarBp.setTop(navbar);
		navbarBp.setCenter(titleBp);
		titleBp.setCenter(titleLbl);
		layoutBp.setTop(navbarBp);
		layoutBp.setCenter(table);
		layoutBp.setBottom(bottomBp);
	    
		titleBp.setPadding(new Insets(height/17.54, width/30.72, height/17.54, width/30.72));
		
		table.setMaxWidth(width/1.065);
		table.setMaxHeight(height/2);
		
		VBox bottomLayout = new VBox(height / 17.5);
		bottomLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(errorLbl, detailBtn);
		bottomBp.setCenter(bottomLayout);
		bottomBp.setPadding(new Insets(height / 17.54, width / 15.36, height / 17.54, width / 15.36));
		
		detailBp.setMinWidth(400);
		detailBp.setMinHeight(250);
		detailPopup.getContent().add(detailBp);
		detailBp.setStyle(" -fx-background-color: gray;");
		detailBp.setCenter(sp);
		sp.setContent(gp);
		
		gp.add(nameLbl, 0, 0);
		gp.add(nameTxtLbl, 1, 0);
		gp.add(categoryLbl, 0, 1);
		gp.add(categoryTxtLbl, 1, 1);
		gp.add(sizeLbl, 0, 2);
		gp.add(sizeTxtLbl, 1, 2);
		gp.add(priceLbl, 0, 3);
		gp.add(priceTxtLbl, 1, 3);
		
		gp.setHgap(10);
		gp.setVgap(10);
		
		HBox buttonLayout = new HBox(width/20);
		buttonLayout.setAlignment(Pos.CENTER);
		buttonLayout.getChildren().addAll(declineBtn, approveBtn);
		
		detailBp.setTop(cancelBtn);
		detailBp.setBottom(buttonLayout);
		
		BorderPane.setAlignment(cancelBtn, Pos.TOP_RIGHT);
		BorderPane.setMargin(cancelBtn, new Insets(0,0,20,0));
		BorderPane.setMargin(buttonLayout, new Insets(20,0,0,0));
		detailBp.setPadding(new Insets(20));
		
		reasonPopup.getContent().add(reasonBp);
		reasonBp.setStyle(" -fx-background-color: lightgray;");
		reasonBp.setCenter(reasonSp);
		reasonSp.setContent(reasonGp);
		
		reasonGp.add(reasonLbl, 0, 0);
		reasonGp.add(reasonTxt, 1, 0);
		
		reasonGp.setHgap(10);
		reasonGp.setVgap(10);
		
		VBox reasonBtnLayout = new VBox(height/17.5);
		reasonBtnLayout.setAlignment(Pos.CENTER);
		reasonBtnLayout.getChildren().addAll(errorReasonLbl, submitBtn);
		reasonBp.setBottom(reasonBtnLayout);
		BorderPane.setMargin(reasonBtnLayout, new Insets(20,0,0,0));
		reasonBp.setPadding(new Insets(20));
	}
	
	@Override
	public void setHandler() {
		
		detailBtn.setOnAction(this::handlePage);
		cancelBtn.setOnAction(this::handlePage);
		approveBtn.setOnAction(this::handlePage);
		declineBtn.setOnAction(this::handlePage);
		submitBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPage("admin-homepage"));
		viewRequestedItemNavItem.setOnAction(event -> sceneManager.switchToPage("requested-item-page"));
		
	}
	
	@Override
	public void handlePage(ActionEvent e) {
		
		Item item = null;
		TableSelectionModel<Item> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.SINGLE);
		item = tsm.getSelectedItem();
		if(item != null) {
			if (e.getSource() == detailBtn) {
				detailPopup.show(stage);
				detail = ItemController.viewRequestedItem(item.getItemId(), item.getItemStatus());
				nameTxtLbl.setText(item.getItemName());
				priceTxtLbl.setText(item.getItemPrice());
				categoryTxtLbl.setText(item.getItemCategory());
				sizeTxtLbl.setText(item.getItemSize());
			} else if (e.getSource() == cancelBtn) {
				detailPopup.hide();
			} else if (e.getSource() == approveBtn) {
				ItemController.approveItem(item.getItemId());
				errorLbl.setText("Item Approved");
				errorLbl.setTextFill(Color.GREEN);
				detailPopup.hide();
				refreshTable();
			} else if(e.getSource() == declineBtn) {
				reasonPopup.show(detailPopup);
			} else if(e.getSource() == submitBtn) {
				String errorMsg = ItemController.validateDecline(reasonTxt.getText());
				errorReasonLbl.setText(errorMsg);
				errorReasonLbl.setTextFill(Color.RED);
				
				if(errorMsg.equals("")) {
					ItemController.declineItem(item.getItemId());
					errorLbl.setText("Item Declined");
					errorLbl.setTextFill(Color.GREEN);
					detailPopup.hide();
					refreshTable();
				}
			}
		} else if(item == null) {
			errorLbl.setText("Item Not Selected");
			errorLbl.setTextFill(Color.RED);
		}
		
	}

	@Override
	public Scene createPageScene() {
     	
		return new Scene(layoutBp, width, height);
		
    }
	
}
