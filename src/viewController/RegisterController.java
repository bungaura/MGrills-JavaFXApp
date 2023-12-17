package viewController;

import controller.UserController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.User;
import viewCustomer.CustomerDashboard;

// A controller class for handling user registration logic and actions
// RegisterController handles input validation and creates a new user if registration is successful
public class RegisterController {
	private TextField registerNameInput;
    private TextField registerEmailInput;
    private PasswordField registerPassInput;
    private PasswordField registerConfirmPassInput;
    private Label errorLbl;
    private User currentUser;
    
    // Constructor initializing the controller with input fields, error label, and user information
	public RegisterController(TextField registerNameInput, TextField registerEmailInput,
			PasswordField registerPassInput, PasswordField registerConfirmPassInput, Label errorLbl, User currentUser) {
		super();
		this.registerNameInput = registerNameInput;
		this.registerEmailInput = registerEmailInput;
		this.registerPassInput = registerPassInput;
		this.registerConfirmPassInput = registerConfirmPassInput;
		this.errorLbl = errorLbl;
		this.currentUser = currentUser;
	}
    
    // Handles the registration process, validates input, and creates a new user if successful
	public void handleRegister() {
        String name = registerNameInput.getText();
        String email = registerEmailInput.getText();
        String pass = registerPassInput.getText();
        String confirmPass = registerConfirmPassInput.getText();

        String validationMessage = UserController.validateRegistration(name, email, pass, confirmPass);
        if (validationMessage != null && !validationMessage.isEmpty()) {
            if (errorLbl == null) {
                errorLbl = new Label();
            }
            errorLbl.setTextFill(Color.RED);
            errorLbl.setText(validationMessage);
        } else {
        	UserController.createUser("Customer", name, email, confirmPass);
        	currentUser = UserController.getCurrentUser();
        	System.out.println(currentUser.getUserName());
        	new CustomerDashboard(currentUser).initialize();
            System.out.println("Registration successful!");
        }
        
    }
	
}
