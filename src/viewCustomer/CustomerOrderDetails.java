package viewCustomer;

import controller.OrderController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class CustomerOrderDetails extends Stage {
    private VBox root;
    private static ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
    private static TableView<OrderItem> tableOrderItem;
    private Label orderIdLbl, dateLbl, orderTotalLbl, statusLbl;
    private Button backBtn, addOrderBtn;
    private int orderId;
    private User currentUser;
    private Order currentOrder;

    public CustomerOrderDetails(User currentUser, Order currentOrder, int orderId) {
    	this.currentUser = currentUser;
    	this.currentOrder = currentOrder;
    	this.orderId = orderId;
	}

	private void setupTableSelectionListener() {
        tableOrderItem.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        		new CustomerOrderUpdate(currentUser, currentOrder).initialize(
                		newSelection.getOrderItemId(),
                		newSelection.getMenuItemId(), 
                		newSelection.getMenuItemName(),
                		newSelection.getQuantity()); 
            }
        );
	}

    public void initialize() {
        root = new VBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        
        orderIdLbl = new Label("Order ID: " + orderId);
        dateLbl = new Label("Date: " + OrderController.getOrderByOrderId(orderId).getOrderDate());  
		statusLbl = new Label("Status: " + OrderController.getOrderByOrderId(orderId).getOrderStatus());  

        tableOrderItem = createOrderItemTable();
        tableOrderItem.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupTableSelectionListener();
        
        loadOrders(orderId);
        checkOrderItemsInOrder(orderId);
        OrderController.updateOrderTotal(orderId);

        orderTotalLbl = new Label("Total Order: " + OrderController.getOrderTotalByOrderId(orderId));

        backBtn = new Button("Back");
        addOrderBtn = new Button("Add Another Menu");

        HBox buttonBox = new HBox(backBtn, addOrderBtn);
        buttonBox.setSpacing(10);

        root.getChildren().addAll(orderIdLbl, dateLbl, statusLbl, tableOrderItem, orderTotalLbl, buttonBox);

        backBtn.setOnAction(e -> {
        	close();
        });
        
        addOrderBtn.setOnAction(e -> {
        	close();
        	new CustomerMenuView(currentUser, currentOrder).initialize();
        	        	
        	loadOrders(orderId);
            OrderController.updateOrderTotal(orderId);
        });
    
        Scene scene = new Scene(root, 750, 550);
        setScene(scene);
        setTitle("Order Details");
        show();
    }
    
    void checkOrderItemsInOrder(int orderId) {
    	if(OrderItemController.getAllOrderItemsByOrderId(orderId).isEmpty()) {
    		OrderController.deleteOrder(orderId);
    	}
    }

    static void loadOrders(int orderId) {
        orderItems.clear();
        orderItems.addAll(OrderItemController.getAllOrderItemsByOrderId(orderId));
        tableOrderItem.setItems(orderItems);
        tableOrderItem.refresh();
    }

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
