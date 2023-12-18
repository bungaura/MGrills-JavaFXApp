package viewCashier;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Order;
import viewController.CashierOrderViewController;

public class CashierOrderView {
    private static TableView<Order> table;
    private VBox root = new VBox();
    private CashierOrderViewController controller;

    public Parent getRoot() {
        return root;
    }
    
    public void initialize() {
        table = createOrderTable();
        controller = new CashierOrderViewController(table);
        controller.initialize();
        root.getChildren().addAll(table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private TableView<Order> createOrderTable() {
        TableView<Order> table = new TableView<>();

        TableColumn<Order, Integer> idColumn = new TableColumn<>("Order ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<Order, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("orderUserName"));

        TableColumn<Order, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        table.getColumns().add(idColumn);
        table.getColumns().add(dateColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(totalColumn);
        table.getColumns().add(statusColumn);

        return table;
    }
}
