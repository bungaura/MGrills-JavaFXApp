package viewCustomer;

import controller.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.OrderItem;
import model.User;
import viewController.CustomerOrderDetailsController;

// CustomerOrderDetails class representing the view for viewing order details of each order in the customer dashboard
public class CustomerOrderDetails extends Stage {
    private VBox root;
    private static TableView<OrderItem> table;
    private Label orderIdLbl, dateLbl, orderTotalLbl, statusLbl;
    private Button backBtn, addOrderBtn;
    private int orderId;
    private User currentUser;
    private Order currentOrder;
    private CustomerOrderDetailsController controller;

    // Constructor to set the current user, current order, and order ID
    public CustomerOrderDetails(User currentUser, Order currentOrder, int orderId) {
    	this.currentUser = currentUser;
    	this.currentOrder = currentOrder;
    	this.orderId = orderId;
	}

    // Initialize the Customer Order Details view
    public void initialize() {
        root = new VBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        
        orderIdLbl = new Label("Order ID: " + orderId);
        dateLbl = new Label("Date: " + OrderController.getOrderByOrderId(orderId).getOrderDate());  
		statusLbl = new Label("Status: " + OrderController.getOrderByOrderId(orderId).getOrderStatus());  

		table = createOrderItemTable();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        controller = new CustomerOrderDetailsController(currentUser, currentOrder, orderId, table);
        controller.initialize();

        orderTotalLbl = new Label("Total Order: " + OrderController.getOrderTotalByOrderId(orderId));

        backBtn = new Button("Back");
        addOrderBtn = new Button("Add Another Menu");

        HBox buttonBox = new HBox(backBtn, addOrderBtn);
        buttonBox.setSpacing(10);

        root.getChildren().addAll(orderIdLbl, dateLbl, statusLbl, table, orderTotalLbl, buttonBox);

        backBtn.setOnAction(e -> close());
        addOrderBtn.setOnAction(e -> {
        	close();
        	new CustomerMenuView(currentUser, currentOrder).initialize();
        	close();
        });
    
        Scene scene = new Scene(root, 750, 550);
        setScene(scene);
        setTitle("Order Details");
        show();
    }

    // Create the order item table
    private TableView<OrderItem> createOrderItemTable() {
        TableView<OrderItem> table = new TableView<>();

        TableColumn<OrderItem, Integer> idColumn = new TableColumn<>("Order Item ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));

        TableColumn<OrderItem, String> itemColumn = new TableColumn<>("Menu Item ID");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        
        TableColumn<OrderItem, String> itemNameColumn = new TableColumn<>("Menu Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));

        TableColumn<OrderItem, Integer> qtyColumn = new TableColumn<>("Quantity");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table.getColumns().add(idColumn);
        table.getColumns().add(itemColumn);
        table.getColumns().add(itemNameColumn);
        table.getColumns().add(qtyColumn);

        return table;
    
    }
}
