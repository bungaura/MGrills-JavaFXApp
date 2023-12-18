package viewCashier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Receipt;
import viewController.CashierReceiptViewController;

public class CashierReceiptView extends Stage {
    private TextField receiptIdView = new TextField();
    private TextField receiptOrderIdView = new TextField();
    private TextField paymentDateView = new TextField();
	private TextField paymentTypeView = new TextField();
	private TextField paymentAmountView = new TextField();
	private ObservableList<Receipt> receipts = FXCollections.observableArrayList();
	private TableView<Receipt> table;
	private Label errorLbl = new Label();
	private VBox root = new VBox();
	private CashierReceiptViewController controller;
	
	public Parent getRoot() {
        return root;
    }

    public void initialize() {
    	table = createOrderTable();
        controller = new CashierReceiptViewController(receiptIdView, receiptOrderIdView, paymentDateView, paymentTypeView, paymentAmountView, receipts, table);
        controller.initialize();
        
        GridPane form = viewReceiptForm(table);
        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form);  
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private TableView<Receipt> createOrderTable() {
        TableView<Receipt> table = new TableView<>();
        TableColumn<Receipt, Integer> receiptIdColumn = new TableColumn<>("Receipt ID");
        receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));

        TableColumn<Receipt, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptOrderId"));

        TableColumn<Receipt, Integer> paymentAmountColumn = new TableColumn<>("Payment Amount");
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentAmount"));
        
        TableColumn<Receipt, String> paymentDateColumn = new TableColumn<>("Payment Date");
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));

        TableColumn<Receipt, String> paymentTypeColumn = new TableColumn<>("Payment Type");
        paymentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentType"));

        table.getColumns().add(receiptIdColumn);
        table.getColumns().add(orderIdColumn);
        table.getColumns().add(paymentAmountColumn);
        table.getColumns().add(paymentDateColumn);
        table.getColumns().add(paymentTypeColumn);

        return table;
    }
    
    private GridPane viewReceiptForm(TableView<Receipt> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        Button viewDetailsBtn = new Button("Details");

        form.add(new Label("Receipt ID:"), 0, 0);
        receiptIdView.setDisable(true);
        form.add(receiptIdView, 1, 0);
        
        form.add(new Label("Order ID:"), 0, 1);
        receiptOrderIdView.setDisable(true);
        form.add(receiptOrderIdView, 1, 1);
        
        form.add(new Label("Payment Amount:"), 0, 2);
        paymentAmountView.setDisable(true);
        form.add(paymentAmountView, 1, 2);
        
        form.add(new Label("Payment Date:"), 0, 3);
        paymentDateView.setDisable(true);
        form.add(paymentDateView, 1, 3);
        
        form.add(new Label("Payment Type:"), 0, 4);
        paymentTypeView.setDisable(true);
        form.add(paymentTypeView, 1, 4);
        
        form.add(viewDetailsBtn, 0, 5);
                
        form.add(errorLbl, 0, 6, 2, 1);
        errorLbl.setTextFill(Color.RED);
        
        viewDetailsBtn.setOnAction(e -> {
        	controller.viewDetails();
        });
        
        return form;
    }
    
}
