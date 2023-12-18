package viewCustomer;

import controller.OrderController;
import controller.OrderItemController;
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

public class CustomerOrderUpdate extends Stage { 
	private TextField qtyInput = new TextField();
	private Label nameLbl;
	private VBox root;
	private User currentUser;
	private Order currentOrder;
	private Button cancelBtn, saveBtn;
	
	public CustomerOrderUpdate(User currentUser, Order currentOrder) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
	}

	public void initialize(int orderItemId, int menuItemId, String menuItemName, int qty) {
		root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        showMenuDetails(orderItemId, menuItemId, menuItemName, qty);

        Scene scene = new Scene(root, 400, 200);
        setScene(scene);
        setTitle(menuItemName);
        show();
	}

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
            if(qtyLastInput == 0) {
            	OrderItemController.deleteOrderItemByQuantity(orderItemId);
            } else {
                OrderItemController.updateOrderItem(orderItemId, menuItemId, qtyLastInput);
                OrderController.updateOrderTotal(currentOrder.getOrderId());
            }
            close();
            
            new CustomerOrderDetails(currentUser, currentOrder, currentOrder.getOrderId()).initialize();
        });
        
        form.add(saveBtn, 0, 3);
        form.add(cancelBtn, 1, 3);
        
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(form);
	}
	
}
