package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.MenuItem;
import model.Order;
import model.User;
import viewCustomer.CustomerMenuDetails;

// A view controller class for managing the business logic related to the customer menu view
// CustomerMenuViewController handles the loading of menu items and user interactions
public class CustomerMenuViewController {
	private ObservableList<MenuItem> items;
	private TableView<MenuItem> table;
	private User currentUser;
	private Order currentOrder;

    // Constructor initializing the controller with the current user, order, and TableView
	public CustomerMenuViewController(User currentUser, Order currentOrder, TableView<MenuItem> table) {
		this.currentUser = currentUser;
		this.currentOrder = currentOrder;
		this.table = table;
		items = FXCollections.observableArrayList();
	}

    // Initializes the controller by loading menu items and setting up a table selection listener
	public void initialize() {
		loadUsers();
        setupTableSelectionListener();		
	}

    // Loads menu items into the TableView
	public void loadUsers() {
		items.clear();
		items.addAll(MenuItem.getAllMenuItems());
		table.setItems(items);
		table.refresh();
	}
	
    // Sets up a listener for the table selection to open the menu item details view
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	new CustomerMenuDetails(currentUser, currentOrder, null, null).initialize(
            			newSelection.getMenuItemId(),
            			newSelection.getMenuItemName(), 
            			newSelection.getMenuItemDescription(), 
            			newSelection.getMenuItemPrice());
            } 
        });
    }
}
