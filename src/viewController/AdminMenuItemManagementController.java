package viewController;

import controller.MenuItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.MenuItem;

//A view controller class managing the business logic for menu item management in the admin menu item management view
//AdminMenuItemManagementController handles the initialization, loading, updating, and deleting of menu items
public class AdminMenuItemManagementController {
	private TableView<MenuItem> table;
    private TextField idInput;
    private TextField nameInput;
    private TextField descInput;
    private TextField priceInput;
    private Label errorLbl;
    private ObservableList<MenuItem> items;
	
    // Constructor initializing the controller with the provided TableView, TextFields, and Label
    public AdminMenuItemManagementController(TableView<MenuItem> table, TextField idInput, TextField nameInput,
            TextField descInput, TextField priceInput, Label errorLbl) {
		this.table = table;
		this.idInput = idInput;
		this.nameInput = nameInput;
		this.descInput = descInput;
		this.priceInput = priceInput;
		this.errorLbl = errorLbl;
		this.items = FXCollections.observableArrayList();
    }
    
    // Initializes the controller by loading menu items and setting up the table selection listener
	public void initialize() {
		loadMenuItems();
		setupTableSelectionListener();
	}
	
    // Loads menu items into the TableView
	void loadMenuItems() {
		items.clear();
		items.addAll(MenuItemController.getAllMenuItems());
		table.setItems(items);
		table.refresh();
	}
	
    // Sets up a selection listener for the TableView to populate the input fields when a menu item is selected
	private void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idInput.setText(String.valueOf(newSelection.getMenuItemId()));
                nameInput.setText(newSelection.getMenuItemName());
                descInput.setText(newSelection.getMenuItemDescription());
                priceInput.setText(String.valueOf(newSelection.getMenuItemPrice()));
            }
        });
    }
	
    // Adds a new menu item based on the input fields
	public void addItem() {
    	String name = nameInput.getText();
    	String desc = descInput.getText();
    	Double price = Double.parseDouble(priceInput.getText());
    	
    	String errorMessage = MenuItemController.validateItemCreation(name, desc, price);

        if (errorMessage != null) {
            errorLbl.setText(errorMessage);
            return; 
        }
        
        MenuItemController.createMenuItem(name, desc, price);
        loadMenuItems();
        errorLbl.setText("");
    }
    
    // Updates an existing menu item based on the input fields
	public void updateItem() {
    	int id = Integer.parseInt(idInput.getText());
    	String name = nameInput.getText();
    	String desc = descInput.getText();
    	Double price = Double.parseDouble(priceInput.getText());
    	
    	String errorMessage = MenuItemController.validateItemOnModify(name, desc, price);

        if (errorMessage != null) {
            errorLbl.setText(errorMessage);
            return; 
        }
        
        MenuItemController.updateMenuItem(id, name, desc, price);
        loadMenuItems();
        errorLbl.setText("");
    }
    
    // Deletes a menu item based on the selected item
	public void deleteItem() {
    	int id = Integer.parseInt(idInput.getText());

    	MenuItemController.deleteMenuItem(id);
    	loadMenuItems();
        errorLbl.setText("");
	}

}
