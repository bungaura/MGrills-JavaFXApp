package viewCustomer;

import java.util.ArrayList;

import controller.OrderController;
import controller.OrderItemController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.User;

public class CustomerMenuView{ 
	private ObservableList<MenuItem> items = FXCollections.observableArrayList();
	private TableView<MenuItem> table;
	private VBox root = new VBox();
	private User currentUser;
	private Order currentOrder;
	public ArrayList<OrderItem> cart;
	private TableView<OrderItem> cartTable;
	
	public CustomerMenuView(User currentUser, Order currentOrder) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
		cart = new ArrayList<>();
	}

	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	new CustomerMenuDetails(currentUser, currentOrder, newSelection, this).initialize(
            			newSelection.getMenuItemId(),
            			newSelection.getMenuItemName(), 
            			newSelection.getMenuItemDescription(), 
            			newSelection.getMenuItemPrice());
            } 
        });
    }
	
	public void getCartData() {
		cartTable.getItems().setAll(cart);
	}
	
	public Parent getRoot() {
		return root;
	}
	
	void loadUsers() {
		items.clear();
		items.addAll(MenuItem.getAllMenuItems());
		table.setItems(items);
		table.refresh();
	}

    public void initialize() {
        table = createItemTable();
        loadUsers();
        Button addOrder = new Button("Add to order");
        setupTableSelectionListener();
        cartTable = createOrderItemTable();
        getCartData();
        addOrder.setOnAction(e -> {
        	int orderId = OrderController.createOrder(currentUser.getUserId());
			for (OrderItem orderItem : cart) {
				OrderItemController.createOrderItem(orderId, orderItem.getMenuItemId(), orderItem.getQuantity());
			}
			this.cart.clear();
			this.currentOrder = null;
			getCartData();
       });
        root.getChildren().addAll(table, cartTable, addOrder);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private TableView<MenuItem> createItemTable() {
        TableView<MenuItem> table = new TableView<>();
        TableColumn<MenuItem, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));

        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));

        TableColumn<MenuItem, String> descColumn = new TableColumn<>("Description");
        descColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));

        TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(descColumn);
        table.getColumns().add(priceColumn);

        return table;
    }
    
    private TableView<OrderItem> createOrderItemTable() {
        TableView<OrderItem> table = new TableView<>();
        TableColumn<OrderItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));

        TableColumn<OrderItem, Integer> qtyColumn = new TableColumn<>("Quantity");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<OrderItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(x -> new SimpleDoubleProperty(MenuItem.getMenuItemObjectById(x.getValue().getMenuItemId()).getMenuItemPrice()*x.getValue().getQuantity()).asObject());

        table.getColumns().add(nameColumn);
        table.getColumns().add(qtyColumn);
        table.getColumns().add(priceColumn);

        return table;
    }
    
}
