package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.MenuItem;
import viewWaiter.WaiterMenuDetails;

//A view controller class for managing the business logic related to the selected order menu view
//WaiterMenuViewController handles the loading of menu items for the waiter to add new menu to selected order
public class WaiterMenuViewController {
	private int orderId;
	private ObservableList<MenuItem> items;
	private TableView<MenuItem> table;

	public WaiterMenuViewController(int orderId, TableView<MenuItem> table) {
		// TODO Auto-generated constructor stub
		this.orderId = orderId;
		this.table = table;
		items = FXCollections.observableArrayList();
	}
	
		// Initializes the controller by loading menu items and setting up a table selection listener
		public void initialize() {
			loadOrderItems();
	        setupTableSelectionListener();	
		}

	    // Loads menu items into the TableView
		void loadOrderItems() {
			items.clear();
			items.addAll(MenuItem.getAllMenuItems());
			table.setItems(items);
			table.refresh();
		}
		
	    // Sets up a listener for the table selection to open the menu item details view
		private void setupTableSelectionListener() {
	        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null) {
	            	new WaiterMenuDetails(orderId).initialize(
	            			newSelection.getMenuItemId(),
	            			newSelection.getMenuItemName(), 
	            			newSelection.getMenuItemDescription(), 
	            			newSelection.getMenuItemPrice());
	            } 
	        });
	    }

}
