package viewCustomer;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Order;
import model.User;
import viewController.CustomerOrderViewController;

// CustomerOrderView class representing the view for viewing order items in the customer dashboard
public class CustomerOrderView {
    private TableView<Order> table;
    private VBox root = new VBox();
    private User currentUser;
    private Order currentOrder;
    private CustomerOrderViewController controller;
        
    // Constructor to set the current user and current order
    public CustomerOrderView(User currentUser, Order currentOrder) {
    	this.currentUser = currentUser;
    	this.currentOrder = currentOrder;
    }
    
	// Returns the root node of the view
    public Parent getRoot() {
        return root;
    }

    // Initialize the Customer Order View
    public void initialize() {
        table = createOrderTable();
        controller = new CustomerOrderViewController(currentUser, currentOrder, table);
        controller.initialize();
        
        root.getChildren().addAll(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Create the order table with columns
    private TableView<Order> createOrderTable() {
        TableView<Order> table = new TableView<>();
        
        TableColumn<Order, Integer> idColumn = new TableColumn<>("Order ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        
        TableColumn<Order, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        
        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        
        table.getColumns().add(idColumn);
        table.getColumns().add(dateColumn);
        table.getColumns().add(totalColumn);
        table.getColumns().add(statusColumn);

        return table;
    }
   
}

