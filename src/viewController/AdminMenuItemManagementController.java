package viewController;

import controller.MenuItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.MenuItem;

public class AdminMenuItemManagementController {
	private TableView<MenuItem> table;
    private TextField idInput;
    private TextField nameInput;
    private TextField descInput;
    private TextField priceInput;
    private Label errorLbl;
    private ObservableList<MenuItem> items;
	
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

	public void initialize() {
		loadMenuItems();
		setupTableSelectionListener();
	}
	
	void loadMenuItems() {
		items.clear();
		items.addAll(MenuItemController.getAllMenuItems());
		table.setItems(items);
		table.refresh();
	}
	
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
    
	public void deleteItem() {
    	int id = Integer.parseInt(idInput.getText());

    	MenuItemController.deleteMenuItem(id);
    	loadMenuItems();
        errorLbl.setText("");
	}

}
