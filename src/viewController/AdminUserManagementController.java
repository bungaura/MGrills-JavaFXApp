package viewController;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.User;

public class AdminUserManagementController {

	private TableView<User> table;
    private TextField idInput;
    private TextField roleInput;
    private TextField nameInput;
    private TextField emailInput;
    private TextField passInput;
    private Label errorLbl;
    private ObservableList<User> users;

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

    public void loadUsers() {
		users.clear();
		users.addAll(User.getAllUsers());
		table.setItems(users);
	}

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

    public void deleteUser() {
    	int id = Integer.parseInt(idInput.getText());

    	UserController.deleteUser(id);
    	loadUsers();
        errorLbl.setText("");

	}

	public void initialize() {
		loadUsers();
		setupTableSelectionListener();
		
	}

}
