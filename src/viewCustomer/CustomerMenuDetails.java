package viewCustomer;

import controller.OrderController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.User;
import viewController.CustomerMenuDetailsController;

// CustomerMenuDetails class representing the view for viewing menu details of each menu in the customer dashboard
public class CustomerMenuDetails extends Stage { 
	private TextField qtyInput = new TextField();
	private Label nameLbl, descLbl, priceLbl, messageLbl;
	private VBox root;
	private User currentUser;
	private Order currentOrder;
	private Button cancelBtn, addToOrderBtn, seeOrderBtn;
	private CustomerMenuDetailsController controller;
	
    // Constructor to set the current user and current order
	public CustomerMenuDetails(User currentUser, Order currentOrder) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
	}

    // Initialize the Customer Menu Details view with menu item details
	public void initialize(int id, String name, String desc, Double price) {
		root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        showMenuDetails(id, name, desc, price);

        Scene scene = new Scene(root, 400, 500);
        setScene(scene);
        setTitle(name);
        show();
	}

    // Show menu details in the view
	private void showMenuDetails(int menuItemId, String name, String desc, Double price) {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);
        
		nameLbl = new Label(name);
        descLbl = new Label(desc);
        priceLbl = new Label(price.toString());
        messageLbl = new Label();

        nameLbl.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        descLbl.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        priceLbl.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        form.add(nameLbl, 0, 0, 2, 1);
        form.add(descLbl, 0, 1, 2, 1);
        form.add(priceLbl, 0, 2, 2, 1);

        Label quantityLbl = new Label("Quantity:");
        form.add(quantityLbl, 0, 3);
        form.add(qtyInput, 1, 3);
        
        addToOrderBtn = new Button("Add to Order");
        cancelBtn = new Button("Cancel");
        seeOrderBtn = new Button("See My Order");
        
        // Set actions for buttons
        cancelBtn.setOnAction(e -> close());
        
        addToOrderBtn.setOnAction(e -> {
            String quantityText = qtyInput.getText();
            String validationMessage = OrderController.validateQuantity(quantityText);

            if (validationMessage == null) {
                int quantity = Integer.parseInt(quantityText);
                controller = new CustomerMenuDetailsController(currentUser);
                messageLbl.setText(controller.addOrder(menuItemId, quantity));
                messageLbl.setStyle("-fx-text-fill: green;");
            } else {
            	messageLbl.setText(validationMessage);
            	messageLbl.setStyle("-fx-text-fill: red;");
            }
            form.add(messageLbl, 0, 5);
        });
        
        seeOrderBtn.setOnAction(e -> {
        	new CustomerOrderDetails(currentUser, currentOrder, currentOrder.getOrderId()).initialize();
        });
        
        form.add(addToOrderBtn, 0, 4);
        form.add(cancelBtn, 1, 4);
        form.add(seeOrderBtn, 0, 6);
        
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(form);
	}
}
