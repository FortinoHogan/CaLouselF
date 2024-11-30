package view.seller;

import java.util.ArrayList;
import client.SceneManager;
import controller.ItemController;
import controller.TransactionController;
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
import model.OfferDetail;
import model.Page;

public class OfferItemPage extends Page{

	private SceneManager sceneManager;
	private Stage stage;
	private Rectangle2D screen = Screen.getPrimary().getVisualBounds();
	private double width = screen.getWidth() * 0.80;
	private double height = screen.getHeight() * 0.85;
	
	private BorderPane layoutBp, navbarBp, titleBp, bottomBp, acceptBp, declineBp;
	private GridPane gp;
	private ScrollPane sp;
	private FlowPane fp;
	
	private MenuBar navbar;
	private Menu menu;
	private MenuItem uploadNavItem, homeNavItem, myItemNavItem, offerItemNavItem, logoutNavItem;
	
	private Label titleLbl, errorLbl, acceptLbl, reasonLbl, errorReasonLbl;
	private TextField reasonTxt;
	
	private TableView<OfferDetail> table;
	
	private String userId;
	
	private Button acceptBtn, declineBtn, yesAcceptBtn, noAcceptBtn, submitBtn, cancelBtn;
	
	private Popup acceptPopup, declinePopup;
	
	public OfferItemPage(Stage stage, String userId) {
		
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
		acceptBp = new BorderPane();
		declineBp = new BorderPane();
		
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
		
		titleLbl = new Label("Item Offer");
		titleLbl.setFont(new Font(24));
		errorLbl = new Label("");
		acceptLbl = new Label("Are you sure?");
		acceptLbl.setFont(new Font(18));
		reasonLbl = new Label("Reason");
		errorReasonLbl = new Label("");
		reasonTxt = new TextField();
		
		table = new TableView<OfferDetail>();
		
		acceptBtn = new Button("Accept");
		declineBtn = new Button("Decline");
		yesAcceptBtn = new Button("Yes");
		noAcceptBtn = new Button("No");
		submitBtn = new Button("Submit");
		cancelBtn = new Button("Cancel");
		
		acceptPopup = new Popup();
		declinePopup = new Popup();
		
	}

	public void initTable() {
		
		TableColumn<OfferDetail, String>  nameCol = new TableColumn<OfferDetail, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<OfferDetail, String>("itemName"));
		nameCol.setMinWidth((width/1.065)/5);
		
		TableColumn<OfferDetail, String>  categoryCol = new TableColumn<OfferDetail, String>("Category");
		categoryCol.setCellValueFactory(new PropertyValueFactory<OfferDetail, String>("itemCategory"));
		categoryCol.setMinWidth((width/1.065)/5);
		
		TableColumn<OfferDetail, String>  sizeCol = new TableColumn<OfferDetail, String>("Size");
		sizeCol.setCellValueFactory(new PropertyValueFactory<OfferDetail, String>("itemSize"));
		sizeCol.setMinWidth((width/1.065)/5);
		
		TableColumn<OfferDetail, String>  priceCol = new TableColumn<OfferDetail, String>("Initial Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<OfferDetail, String>("itemPrice"));
		priceCol.setMinWidth((width/1.065)/5);
		
		TableColumn<OfferDetail, String>  offerPriceCol = new TableColumn<OfferDetail, String>("Offered Price");
		offerPriceCol.setCellValueFactory(new PropertyValueFactory<OfferDetail, String>("offerPrice"));
		offerPriceCol.setMinWidth((width/1.065)/5);
		
		table.getColumns().addAll(nameCol, categoryCol, sizeCol, priceCol, offerPriceCol);
		
		refreshTable();
		
	}
	
	public void refreshTable() {

		ArrayList<OfferDetail> offers = ItemController.viewOfferItem(userId);

		table.getItems().clear();
		
		for (OfferDetail d :  offers) {
			table.getItems().add(d);
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
		HBox buttonLayout = new HBox(width / 20);
		bottomLayout.setAlignment(Pos.CENTER);
		buttonLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(errorLbl, buttonLayout);
		buttonLayout.getChildren().addAll(declineBtn, acceptBtn);
		
		bottomBp.setCenter(bottomLayout);
		bottomBp.setPadding(new Insets(height / 17.54, width / 15.36, height / 17.54, width / 15.36));;
		
		fp.getChildren().add(yesAcceptBtn);
		fp.getChildren().add(noAcceptBtn);
		fp.setHgap(20);
		fp.setAlignment(Pos.CENTER);
		
		acceptBp.setMinWidth(width / 30);
		acceptBp.setMinHeight(height / 15);
		acceptPopup.getContent().add(acceptBp);

		acceptBp.setTop(acceptLbl);
		acceptBp.setCenter(fp);
		acceptBp.setStyle(" -fx-background-color: gray;");
		BorderPane.setAlignment(acceptLbl, Pos.CENTER);
		BorderPane.setAlignment(acceptBp, Pos.CENTER);
		BorderPane.setMargin(acceptLbl, new Insets(0, 0, 20, 0));
		acceptLbl.setStyle("-fx-text-fill: white;");
		acceptBp.setPadding(new Insets(20));
		
		declinePopup.getContent().add(declineBp);
		declineBp.setStyle(" -fx-background-color: lightgray;");
		declineBp.setCenter(sp);
		sp.setContent(gp);
		
		gp.add(reasonLbl, 0, 0);
		gp.add(reasonTxt, 1, 0);
		
		gp.setHgap(10);
		gp.setVgap(10);
		
		VBox reasonLayout = new VBox(height/17.5);
		HBox reasonBtnLayout = new HBox(width / 20);
		reasonBtnLayout.setAlignment(Pos.CENTER);
		reasonLayout.setAlignment(Pos.CENTER);
		reasonBtnLayout.getChildren().addAll(cancelBtn, submitBtn);
		reasonLayout.getChildren().addAll(errorReasonLbl, reasonBtnLayout);
		declineBp.setBottom(reasonLayout);
		BorderPane.setMargin(reasonLayout, new Insets(20,0,0,0));
		declineBp.setPadding(new Insets(20));

	}

	@Override
	public void setHandler() {
		
		acceptBtn.setOnAction(this::handlePage); 
		declineBtn.setOnAction(this::handlePage);
		yesAcceptBtn.setOnAction(this::handlePage);
		noAcceptBtn.setOnAction(this::handlePage);
		submitBtn.setOnAction(this::handlePage);
		cancelBtn.setOnAction(this::handlePage);
		
		homeNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-homepage", userId));
		uploadNavItem.setOnAction(event -> sceneManager.switchToPageSeller("upload-item", userId));
		myItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("seller-item-page", userId));
		offerItemNavItem.setOnAction(event -> sceneManager.switchToPageSeller("offer-item-page", userId));
		logoutNavItem.setOnAction(event -> sceneManager.switchToPage("login"));
		
	}

	@Override
	public void handlePage(ActionEvent e) {
		
		OfferDetail offer = null;
		TableSelectionModel<OfferDetail> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.SINGLE);
		offer = tsm.getSelectedItem();
		
		if(offer != null) {
			if(e.getSource() == acceptBtn) {
				acceptPopup.show(stage);
			} else if (e.getSource() == yesAcceptBtn) {
				ItemController.acceptOffer(offer.getItemId());
				TransactionController.purchaseItem(offer.getUserId(), offer.getItemId());
				errorLbl.setText("Offer Accepted");
				errorLbl.setTextFill(Color.GREEN);
				acceptPopup.hide();
				refreshTable();
			} else if (e.getSource() == noAcceptBtn) {
				acceptPopup.hide();
			} else if (e.getSource() == declineBtn) {
				declinePopup.show(stage);
			} else if (e.getSource() == submitBtn) {
				String errorMsg = ItemController.validateDecline(reasonTxt.getText());
				errorReasonLbl.setText(errorMsg);
				errorReasonLbl.setTextFill(Color.RED);
				
				if(errorMsg.equals("")) {
					ItemController.declineOffer(offer.getItemId());
					errorLbl.setText("Offer Declined");
					errorLbl.setTextFill(Color.GREEN);
					declinePopup.hide();
					refreshTable();
				}
			} else if (e.getSource() == cancelBtn) {
				declinePopup.hide();
			}
		} else if(offer == null) {
			errorLbl.setText("Item Not Selected");
			errorLbl.setTextFill(Color.RED);
		}
		
	}

	@Override
	public Scene createPageScene() {

		return new Scene(layoutBp, width, height);
		
	}

}
