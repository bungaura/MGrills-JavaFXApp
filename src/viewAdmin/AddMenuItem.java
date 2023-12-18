package viewAdmin;

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
import model.MenuItem;
import viewController.AdminAddMenuItemController;

public class AddMenuItem extends Stage {
    private TextField idInput = new TextField();
	private TextField nameInput = new TextField();
	private TextField descInput = new TextField();
	private TextField priceInput = new TextField();
	private TableView<MenuItem> table;
	private Label errorLbl = new Label();
	private VBox root = new VBox();
	private AdminAddMenuItemController controller;
	
	public Parent getRoot() {
        return root;
    }

    public void initialize() {
        table = createItemTable();        
        controller = new AdminAddMenuItemController(table, idInput, nameInput, descInput, priceInput, errorLbl);
        controller.initialize();

        GridPane form = createProductForm(table);
        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form);  
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
    
    private GridPane createProductForm(TableView<MenuItem> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        Button addBtn = new Button("Add Menu Item");
      
        form.add(new Label("ID:"), 0, 0);
        idInput.setDisable(true);
        form.add(idInput, 1, 0);
        
        form.add(new Label("Name:"), 0, 1);
        form.add(nameInput, 1, 1);
        
        form.add(new Label("Description:"), 0, 2);
        form.add(descInput, 1, 2);
        
        form.add(new Label("Price:"), 0, 3);
        form.add(priceInput, 1, 3);
        
        form.add(addBtn, 0, 5);
                
        form.add(errorLbl, 0, 6, 2, 1);
        errorLbl.setTextFill(Color.RED);
        
        addBtn.setOnAction(e -> controller.addItem());
        
        return form;
    }

}


