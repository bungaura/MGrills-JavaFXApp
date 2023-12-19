package viewWaiter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuItem;
import viewController.WaiterMenuViewController;

// WaiterOrderMenuView class representing the view for viewing menu items in the waiter dashboard
public class WaiterOrderMenuView extends Stage { 
	private TableView<MenuItem> table;
	private VBox root = new VBox();
	private WaiterMenuViewController controller;
	private Label infoLbl;
	private Button cancelBtn, addBtn;
	
	// Returns the root node of the view
	public Parent getRoot() {
		return root;
	}

    // Initialize the Waiter Order View
    public void initialize(int orderId) {
    	root = new VBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        
        infoLbl = new Label("Add another menu to Order ID: " + orderId + " ?");
        
        cancelBtn = new Button("Cancel");
        addBtn = new Button("Add Order");
               
        table = createItemTable();
        controller = new WaiterMenuViewController(orderId, table);
        controller.initialize();
        
        HBox buttonBox = new HBox(cancelBtn, addBtn);
        buttonBox.setSpacing(10);
        
        root.getChildren().addAll(table, infoLbl, buttonBox);  
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                
        Scene scene = new Scene(root, 750, 550);
        setScene(scene);
        setTitle("List of MYstic Grills Menu");
        show();
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

