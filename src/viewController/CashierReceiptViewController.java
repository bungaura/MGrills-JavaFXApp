package viewController;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Order;
import model.Receipt;
import viewCashier.CashierReceiptDetails;

public class CashierReceiptViewController {

	private TextField receiptIdView;
    private TextField receiptOrderIdView;
    private TextField paymentDateView;
	private TextField paymentTypeView;
	private TextField paymentAmountView;
	private ObservableList<Receipt> receipts;
	private TableView<Receipt> table;
	
	public CashierReceiptViewController(TextField receiptIdView, TextField receiptOrderIdView, TextField paymentDateView, TextField paymentTypeView, TextField paymentAmountView, ObservableList<Receipt> receipts, TableView<Receipt> table) {
		this.receiptIdView = receiptIdView;
		this.receiptOrderIdView = receiptOrderIdView;
		this.paymentDateView = paymentDateView;
		this.paymentTypeView = paymentTypeView;
		this.paymentAmountView = paymentAmountView;
		this.table = table;
        receipts = FXCollections.observableArrayList();
	}
	
	public void initialize() {
		loadOrders();
		setupTableSelectionListener();
	}
	
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	receiptIdView.setText(String.valueOf(newSelection.getReceiptId()));
                receiptOrderIdView.setText(String.valueOf(newSelection.getReceiptOrderId()));
                paymentAmountView.setText(String.valueOf(newSelection.getReceiptPaymentAmount()));
                paymentDateView.setText(newSelection.getReceiptPaymentDate());
                paymentTypeView.setText(newSelection.getReceiptPaymentType());
            }
        });
    }
	
	private void loadOrders() {
		this.receipts = FXCollections.observableArrayList();
		receipts.clear();
		receipts.addAll(Receipt.getAllReceipts());
		table.setItems(receipts);
	}
	
	public void viewDetails(){
		if(receipts != null) {
    		Order order = OrderController.getOrderByOrderId(Integer.parseInt(receiptOrderIdView.getText()));
    		CashierReceiptDetails receiptDetails = new CashierReceiptDetails(order, Integer.parseInt(receiptIdView.getText()));
            receiptDetails.initialize();  
            receiptDetails.show();
    	}
	}
	
}
