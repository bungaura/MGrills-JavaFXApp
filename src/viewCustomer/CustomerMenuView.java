package viewCustomer;

import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.MenuItem;
import model.Order;
import model.User;
import viewController.CustomerMenuViewController;

// CustomerMenuView class representing the view for viewing menu items in the customer dashboard
public class CustomerMenuView { 
	private TableView<MenuItem> table;
	private VBox root = new VBox();
	private User currentUser;
	private Order currentOrder;
	private CustomerMenuViewController controller;
	
    // Constructor to set the current user and current order
	public CustomerMenuView(User currentUser, Order currentOrder) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
	}
	
	// Returns the root node of the view
	public Parent getRoot() {
		return root;
	}

    // Initialize the Customer Order View
    public void initialize() {
        table = createItemTable();
        controller = new CustomerMenuViewController(currentUser, currentOrder, table);
        controller.initialize();
        
        root.getChildren().addAll(table);  
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Create the order table with columns
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
}
