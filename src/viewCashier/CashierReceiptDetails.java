package viewCashier;

import java.util.ArrayList;

import controller.OrderItemController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuItem;
import model.Order;
import model.OrderItem;

//CashierReceiptDetails class representing the view for displaying receipt details
public class CashierReceiptDetails extends Stage {
    private VBox root;
    private static TableView<OrderItem> table;
    private ArrayList<OrderItem> listOrderItem;
    private Label orderIdLbl;
    private Order currentOrder;
	private int receiptId;

    public CashierReceiptDetails(Order currentOrder, int receiptId) {
    	this.currentOrder = currentOrder;
    	this.receiptId = receiptId;
    	this.listOrderItem = new ArrayList<>();
	}

    //Initializes the CashierReceiptDetails, including the receipt details table
    public void initialize() {
        root = new VBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        
        orderIdLbl = new Label("Receipt ID: " + receiptId); 

        table = createOrderItemTable();
        listOrderItem.addAll(OrderItemController.getAllOrderItemsByOrderId(this.currentOrder.getOrderId()));
        table.getItems().addAll(listOrderItem);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(orderIdLbl, table);
    
        Scene scene = new Scene(root, 750, 550);
        this.setScene(scene);
        this.setTitle("Order Details");
    }

    //Creates and configures the order item table for displaying receipt details
    private TableView<OrderItem> createOrderItemTable() {
        TableView<OrderItem> table = new TableView<>();

        TableColumn<OrderItem, Integer> idColumn = new TableColumn<>("Order Item ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));

        TableColumn<OrderItem, Integer> itemColumn = new TableColumn<>("Menu Item ID");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        
        TableColumn<OrderItem, String> itemNameColumn = new TableColumn<>("Menu Item Name");
        itemNameColumn.setCellValueFactory(x->new SimpleStringProperty(MenuItem.getMenuItemObjectById(x.getValue().getMenuItemId()).getMenuItemName()));

        TableColumn<OrderItem, Integer> qtyColumn = new TableColumn<>("Quantity");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<OrderItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(x->new SimpleDoubleProperty(MenuItem.getMenuItemObjectById(x.getValue().getMenuItemId()).getMenuItemPrice()*x.getValue().getQuantity()).asObject());

        table.getColumns().add(idColumn);
        table.getColumns().add(itemColumn);
        table.getColumns().add(itemNameColumn);
        table.getColumns().add(qtyColumn);
        table.getColumns().add(priceColumn);
        
        return table;
    }
   
}
