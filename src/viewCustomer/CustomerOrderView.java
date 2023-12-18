package viewCustomer;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Order;
import model.User;

public class CustomerOrderView {
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private TableView<Order> table;
    private VBox root = new VBox();
    private User currentUser;
    private Order currentOrder;
        
    public CustomerOrderView(User currentUser, Order currentOrder) {
    	this.currentUser = currentUser;
    	this.currentOrder = currentOrder;
    }
    
    private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int orderId = newSelection.getOrderId();
                new CustomerOrderDetails(currentUser, currentOrder, orderId).initialize();
            }
        });
    }

    public Parent getRoot() {
        return root;
    }

    public void loadOrders() {
        orders.clear();
        orders.addAll(OrderController.getOrdersByCustomerId(currentUser.getUserId()));
        table.setItems(orders);
        table.refresh();
    }

    public void initialize() {
        table = createOrderTable();
        loadOrders();
        
        setupTableSelectionListener();
        root.getChildren().addAll(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

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

