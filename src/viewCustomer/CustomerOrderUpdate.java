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
import viewController.CustomerOrderUpdateController;

// CustomerOrderUpdate class representing the view for updating menu quantity of selected order in the customer dashboard
public class CustomerOrderUpdate extends Stage { 
	private TextField qtyInput = new TextField();
	private Label nameLbl;
	private VBox root;
	private User currentUser;
	private Order currentOrder;
	private Button cancelBtn, saveBtn;
	private CustomerOrderUpdateController controller;
	
    // Constructor to set the current user and current order
	public CustomerOrderUpdate(User currentUser, Order currentOrder) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
	}

    // Initialize the Customer Order Update view
	public void initialize(int orderItemId, int menuItemId, String menuItemName, int qty) {
		root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        showMenuDetails(orderItemId, menuItemId, menuItemName, qty);
		controller = new CustomerOrderUpdateController(currentOrder);

        Scene scene = new Scene(root, 400, 200);
        setScene(scene);
        setTitle(menuItemName);
        show();
	}

    // Show the menu details in the form
	private void showMenuDetails(int orderItemId, int menuItemId, String menuItemName, int qty) {
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);
        
		nameLbl = new Label(menuItemName);
        nameLbl.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        form.add(nameLbl, 0, 1);
                
        qtyInput.setText(String.valueOf(qty));
        Label quantityLbl = new Label("Quantity:");
        form.add(quantityLbl, 0, 2);
        form.add(qtyInput, 1, 2);
                
        saveBtn = new Button("Save Changes");
        cancelBtn = new Button("Cancel");
        
        cancelBtn.setOnAction(e -> close());
        saveBtn.setOnAction(e -> {
            int qtyLastInput = Integer.parseInt(qtyInput.getText());
        	controller.saveChanges(qtyLastInput, orderItemId, menuItemId);
            close();
            
            Order updatedOrder = OrderController.getOrderByOrderId(currentOrder.getOrderId());
            new CustomerOrderDetails(currentUser, updatedOrder, updatedOrder.getOrderId()).initialize();
        });
        
        form.add(saveBtn, 0, 3);
        form.add(cancelBtn, 1, 3);
        
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(form);
	}
	
}
