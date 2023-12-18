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
import model.User;
import viewController.AdminUserManagementController;

public class UserManagement extends Stage {
    private TextField idInput = new TextField();
    private TextField roleInput = new TextField();
	private TextField nameInput = new TextField();
	private TextField emailInput = new TextField();
	private TextField passInput = new TextField();
	private TableView<User> table;
	private Label errorLbl = new Label();
	private VBox root = new VBox();
    private AdminUserManagementController controller;

	
	public Parent getRoot() {
        return root;
    }
	
	public void initialize() {
        table = createUserTable();
        controller = new AdminUserManagementController(table, idInput, roleInput, nameInput, emailInput, passInput, errorLbl);
        controller.initialize();
        
        GridPane form = createProductForm(table);
        VBox.setMargin(form, new Insets(20));
        root.getChildren().addAll(table, form);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        controller.setupTableSelectionListener();
        controller.loadUsers();
    }

    private TableView<User> createUserTable() {
        TableView<User> table = new TableView<>();
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

        TableColumn<User, String> passColumn = new TableColumn<>("Password");
        passColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));

        table.getColumns().add(idColumn);
        table.getColumns().add(roleColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(emailColumn);
        table.getColumns().add(passColumn);

        return table;
    }
    
    private GridPane createProductForm(TableView<User> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        Button updateBtn = new Button("Update User's Role");
        Button deleteBtn = new Button("Delete User");

        form.add(new Label("ID:"), 0, 0);
        idInput.setDisable(true);
        form.add(idInput, 1, 0);
        
        form.add(new Label("Role:"), 0, 1);
        form.add(roleInput, 1, 1);
        
        form.add(new Label("Name:"), 0, 2);
        nameInput.setDisable(true);
        form.add(nameInput, 1, 2);
        
        form.add(new Label("Email:"), 0, 3);
        emailInput.setDisable(true);
        form.add(emailInput, 1, 3);
        
        form.add(new Label("Password:"), 0, 4);
        passInput.setDisable(true);
        form.add(passInput, 1, 4);
        
        form.add(updateBtn, 0, 5);
        form.add(deleteBtn, 1, 5);
                
        form.add(errorLbl, 0, 6, 2, 1);
        errorLbl.setTextFill(Color.RED);
        
        updateBtn.setOnAction(e -> controller.updateUser());
        deleteBtn.setOnAction(e -> controller.deleteUser());
        
        return form;
    }

}


