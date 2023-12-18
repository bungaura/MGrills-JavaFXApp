package viewController;

import controller.UserController;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import view.Register;
import viewAdmin.AdminDashboard;
import viewCashier.CashierDashboard;
import viewChef.ChefDashboard;
import viewCustomer.CustomerDashboard;
import viewWaiter.WaiterDashboard;

public class LoginController extends Stage{

	private TextField loginEmailInput;
    private PasswordField loginPassInput;
    Label errorLbl;
	
    public LoginController(TextField loginEmailInput, PasswordField loginPassInput, Label errorLbl) {
		super();
		this.loginEmailInput = loginEmailInput;
		this.loginPassInput = loginPassInput;
		this.errorLbl = errorLbl;
	}

	public void handleLogin() {
    	String email = loginEmailInput.getText();
        String password = loginPassInput.getText();
        
        String validationMessage = UserController.authenticateUser(email, password);
        if (validationMessage != null && !validationMessage.isEmpty()) {
            if (errorLbl == null) {
                errorLbl = new Label();
            }
            errorLbl.setTextFill(Color.RED);
            errorLbl.setText(validationMessage);
        } else {

        	if (validationMessage != null && !validationMessage.isEmpty()) {
                errorLbl.setText(validationMessage);
            } else {
                User authenticatedUser = UserController.getAuthenticatedUser();
                String role = authenticatedUser.getUserRole();
                
                switch (role) {
                    case "Admin":
                        new AdminDashboard().initialize();
                        break;
                    case "Cashier":
                        new CashierDashboard().initialize();
                        break;
                    case "Customer":
                        new CustomerDashboard(authenticatedUser).initialize();;
                        break;
                    case "Chef":
                        new ChefDashboard().initialize();
                        break;
                    case "Waiter":
                        new WaiterDashboard().initialize();
                        break;
                }
        	}
        	System.out.println("Login successful!");
            close();
        }
    }
    
    public void register() {
    	new Register().initialize();
    	close();
    }
	
}
