package view.seller;

import java.util.ArrayList;

import client.SceneManager;
import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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

public class SellerItemPage extends Page {

	private SceneManager sceneManager;
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;

	private BorderPane layoutBp, navbarBp, titleBp, bottomBp, deleteBp;
	private GridPane gp;
	private ScrollPane sp;
	private FlowPane fp;

	private MenuBar navbar;
	private Menu menu;
	private MenuItem uploadNavItem, homeNavItem, myItemNavItem, offerItemNavItem, logoutNavItem;

	private Label nameLbl, categoryLbl, sizeLbl, priceLbl, titleLbl, errorLbl, deleteLbl;
	private TextField nameTxt, categoryTxt, sizeTxt, priceTxt;
	private Button editBtn, deleteBtn, yesBtn, noBtn;

	private TableView<Item> table;

	private String userId;
	
	private Popup deleteConfirmation;

	public SellerItemPage(Stage stage, String userId) {

		this.userId = userId;
		this.stage = stage;
		sceneManager = new SceneManager(stage);
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
		deleteBp = new BorderPane();

		gp = new GridPane();
		sp = new ScrollPane();
		fp = new FlowPane();

		navbar = new MenuBar();
		menu = new Menu("Action");
		homeNavItem = new MenuItem("Home");
		uploadNavItem = new MenuItem("Upload");
		myItemNavItem = new MenuItem("My Item");
		offerItemNavItem = new MenuItem("Offer Item");
		logoutNavItem = new MenuItem("Logout");
		navbar.getMenus().add(menu);
		menu.getItems().addAll(homeNavItem, uploadNavItem, myItemNavItem, offerItemNavItem, logoutNavItem);

		titleLbl = new Label("My Item");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		deleteLbl = new Label("Are you sure want to delete this item?");
		deleteLbl.setFont(new Font(18));

		table = new TableView<Item>();

		editBtn = new Button("Edit");
		deleteBtn = new Button("Delete");
		yesBtn = new Button("Yes");
		noBtn = new Button("No");

		deleteConfirmation = new Popup();
		
	}

	public void initTable() {

		TableColumn<Item, String> nameCol = new TableColumn<Item, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
		nameCol.setMinWidth((width / 1.065) / 4);

		TableColumn<Item, String> categoryCol = new TableColumn<Item, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemCategory"));
		categoryCol.setMinWidth((width / 1.065) / 4);

		TableColumn<Item, String> sizeCol = new TableColumn<Item, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemSize"));
		sizeCol.setMinWidth((width / 1.065) / 4);

		TableColumn<Item, String> priceCol = new TableColumn<Item, String>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("itemPrice"));
		priceCol.setMinWidth((width / 1.065) / 4);

		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol);

		refreshTable();

	}

	public void refreshTable() {

		table.getItems().clear();
		
		ArrayList<Item> items = ItemController.getItemsByUserId(userId);

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

		titleBp.setPadding(new Insets(height / 17.54, width / 30.72, height / 17.54, width / 30.72));

		table.setMaxWidth(width / 1.065);
		table.setMaxHeight(height / 2);

		VBox bottomLayout = new VBox(height / 17.5);
		HBox buttonLayout = new HBox(width / 20);
		bottomLayout.setAlignment(Pos.CENTER);
		buttonLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(errorLbl, buttonLayout);
		buttonLayout.getChildren().addAll(editBtn, deleteBtn);

		bottomBp.setCenter(bottomLayout);
		bottomBp.setPadding(new Insets(height / 17.54, width / 15.36, height / 17.54, width / 15.36));
		
		fp.getChildren().add(yesBtn);
		fp.getChildren().add(noBtn);
		fp.setHgap(20);
		fp.setAlignment(Pos.CENTER);
		
		deleteBp.setMinWidth(width / 30);
		deleteBp.setMinHeight(height / 15);
		deleteConfirmation.getContent().add(deleteBp);

		deleteBp.setTop(deleteLbl);
		deleteBp.setCenter(fp);
		deleteBp.setStyle(" -fx-background-color: gray;");
		BorderPane.setAlignment(deleteLbl, Pos.CENTER);
		BorderPane.setAlignment(deleteBp, Pos.CENTER);
		BorderPane.setMargin(deleteLbl, new Insets(0, 0, 20, 0));
		deleteLbl.setStyle("-fx-text-fill: white;");
		deleteBp.setPadding(new Insets(20));

	}

	@Override
	public void setHandler() {

		editBtn.setOnAction(this::handlePage);
		deleteBtn.setOnAction(this::handlePage);
		yesBtn.setOnAction(this::handlePage);
		noBtn.setOnAction(this::handlePage);

		homeNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-homepage", userId));
		uploadNavItem.setOnAction(event -> sceneManager.switchToPageSeller("upload-item", userId));
		myItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-item-page", userId));
		offerItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("offer-item-page", userId));
		logoutNavItem.setOnAction(event -> sceneManager.switchToPage("login"));
		
	}

	@Override
	public void handlePage(ActionEvent e) {

		Item item = null;
		TableSelectionModel<Item> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.SINGLE);
		item = tsm.getSelectedItem();
		
		if(item != null) {
			if (e.getSource() == editBtn) {
				sceneManager.switchToPageItem(item, userId);
			} else if (e.getSource() == deleteBtn) {
				deleteConfirmation.show(stage);
			} else if (e.getSource() == yesBtn) {
				ItemController.deleteItem(item.getItemId());
				refreshTable();
				errorLbl.setText("Item Deleted");
				errorLbl.setTextFill(Color.GREEN);
				deleteConfirmation.hide();
			} else if (e.getSource() == noBtn) {
				deleteConfirmation.hide();
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
