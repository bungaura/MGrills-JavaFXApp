package viewController;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.User;

// A view controller class managing the business logic for user management in the admin user management view
// AdminUserManagementController handles the initialization, loading, updating, and deleting of user information
public class AdminUserManagementController {
	private TableView<User> table;
    private TextField idInput;
    private TextField roleInput;
    private TextField nameInput;
    private TextField emailInput;
    private TextField passInput;
    private Label errorLbl;
    private ObservableList<User> users;

    // Constructor initializing the controller with the provided TableView, TextFields, and Label
    public AdminUserManagementController(TableView<User> table, TextField idInput, TextField roleInput,
            TextField nameInput, TextField emailInput, TextField passInput, Label errorLbl) {
		this.table = table;
		this.idInput = idInput;
		this.roleInput = roleInput;
		this.nameInput = nameInput;
		this.emailInput = emailInput;
		this.passInput = passInput;
		this.errorLbl = errorLbl;
		this.users = FXCollections.observableArrayList();
    }
    
    // Sets up a selection listener for the TableView to populate the input fields when a user is selected
    public void setupTableSelectionListener() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idInput.setText(String.valueOf(newSelection.getUserId()));
                roleInput.setText(newSelection.getUserRole());
                nameInput.setText(newSelection.getUserName());
                emailInput.setText(newSelection.getUserEmail());
                passInput.setText(newSelection.getUserPassword());
            }
        });
    }

    // Loads users into the TableView
    public void loadUsers() {
		users.clear();
		users.addAll(User.getAllUsers());
		table.setItems(users);
	}

    // Updates user information based on the input fields
    public void updateUser() {
    	int id = Integer.parseInt(idInput.getText());
    	String role = roleInput.getText();
    	
    	String errorMessage = UserController.validateUserRole(role, id);

        if (errorMessage != null) {
            errorLbl.setText(errorMessage);
            return; 
        }
        UserController.updateUser(role, id);
        loadUsers();
        errorLbl.setText("");
    }

    // Deletes a user based on the selected user's ID
    public void deleteUser() {
    	int id = Integer.parseInt(idInput.getText());

    	UserController.deleteUser(id);
    	loadUsers();
        errorLbl.setText("");

	}

    // Initializes the controller by loading users and setting up the table selection listener
	public void initialize() {
		loadUsers();
		setupTableSelectionListener();
		
	}

}
