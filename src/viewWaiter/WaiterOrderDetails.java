package viewWaiter;

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
import model.OrderItem;
import viewController.WaiterOrderDetailsController;

// WaiterOrderDetails class representing the view for viewing order details of each order in the waiter dashboard
public class WaiterOrderDetails extends Stage {
	private VBox root;
    private static TableView<OrderItem> table;
    private Label orderIdLbl, dateLbl, orderTotalLbl, statusLbl, messageLbl;
    private Button backBtn, serveOrderBtn, addBtn;
    private WaiterOrderDetailsController controller;

    // Initializes the Waiter Order Details View
    public void initialize(int orderId) {
        root = new VBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        
        orderIdLbl = new Label("Order ID: " + orderId);
        dateLbl = new Label("Date Ordered: " + OrderController.getOrderByOrderId(orderId).getOrderDate());  
        statusLbl = new Label("Status: " + OrderController.getOrderByOrderId(orderId).getOrderStatus());  
        
        table = createOrderItemTable();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        controller = new WaiterOrderDetailsController(orderId, table);
        controller.initialize();
                
        orderTotalLbl = new Label("Total Order: " + OrderController.getOrderTotalByOrderId(orderId));
        messageLbl = new Label();
        backBtn = new Button("Cancel");
        serveOrderBtn = new Button("Serve Order");
        addBtn = new Button("Add Order Item");

        HBox buttonBox = new HBox(backBtn, serveOrderBtn, addBtn);
        buttonBox.setSpacing(10);

        root.getChildren().addAll(orderIdLbl, dateLbl, statusLbl, table, orderTotalLbl, messageLbl, buttonBox);

        backBtn.setOnAction(e -> close()); 
        
        serveOrderBtn.setOnAction(e -> {
        	String result = controller.serveOrder();
            messageLbl.setText(result);
            messageLbl.setStyle(result.contains("Served") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        });
        
        addBtn.setOnAction(e -> {
        	String result = controller.addNewOrderItem();
        	messageLbl.setText(result);
            messageLbl.setStyle(result.contains("Cannot") ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        });
    
        Scene scene = new Scene(root, 750, 550);
        setScene(scene);
        setTitle("Order Details");
        show();
    }

    // Creates and configures the TableView for displaying order items
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
