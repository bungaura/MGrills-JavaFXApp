package viewCashier;

import controller.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.OrderItem;
import viewController.CashierOrderDetailsController;

public class CashierOrderDetails extends Stage {
    private VBox root;
    private static TableView<OrderItem> table;
    private Label orderIdLbl, dateLbl, orderTotalLbl, statusLbl, messageLbl, paymentAmountLbl, paymentTypeLbl;
    private Button backBtn, processOrderBtn, removeOrderBtn;
    private TextField paymentAmountInput, paymentTypeInput;
//    private int orderId;
    private CashierOrderDetailsController controller;
    

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
        
        controller = new CashierOrderDetailsController(orderId, table);
        controller.initialize();
        OrderController.updateOrderTotal(orderId);
        
        Double orderTotal = OrderController.getOrderTotalByOrderId(orderId);
        orderTotalLbl = new Label("Total Order: " + orderTotal);
        
        paymentAmountLbl = new Label("Payment Amount: ");
        paymentAmountInput = new TextField();
        paymentAmountInput.setPromptText("Enter payment amount"); 
        
        paymentTypeLbl = new Label("Payment Type: ");
        paymentTypeInput = new TextField();
        paymentTypeInput.setPromptText("Enter payment type"); 
        
        messageLbl = new Label();

        backBtn = new Button("Back");
        processOrderBtn = new Button("Process Order");
        removeOrderBtn = new Button("Remove Order");

        HBox buttonBox = new HBox(backBtn, processOrderBtn, removeOrderBtn);
        buttonBox.setSpacing(10);

        root.getChildren().addAll(orderIdLbl, dateLbl, statusLbl, table, orderTotalLbl, paymentTypeLbl, paymentTypeInput, paymentAmountLbl, paymentAmountInput, messageLbl, buttonBox);

        backBtn.setOnAction(e -> {
        	close();
        }); 
        
        processOrderBtn.setOnAction(e -> {
        	String type = paymentTypeInput.getText();
        	Double amount;
        	try {
                amount = Double.parseDouble(paymentAmountInput.getText());
            } catch (NumberFormatException ex) {
                messageLbl.setText("Please enter a valid numeric amount!");
                messageLbl.setTextFill(Color.RED);
                return;
            }
        	String validationMessage = controller.validatePaymentInput(type, amount);
            if (validationMessage != null) {
                messageLbl.setText(validationMessage);
                messageLbl.setTextFill(Color.RED);
            } else {
                String result = controller.processOrder(type, amount);
                messageLbl.setText(result);
                messageLbl.setTextFill(result.contains("Order") ? Color.GREEN : Color.RED);
            }
        });
        
        removeOrderBtn.setOnAction(e -> {
        	String result = controller.removeOrder();
        	messageLbl.setText(result);
        	messageLbl.setTextFill(result.contains("Order") ? Color.GREEN : Color.RED);
        });
    
        Scene scene = new Scene(root, 750, 550);
        setScene(scene);
        setTitle("Order Details");
        show();
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
