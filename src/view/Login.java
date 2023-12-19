package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import viewController.LoginController;

// Login class representing the login view
public class Login extends Stage {
    private TextField loginEmailInput = new TextField();
    private PasswordField loginPassInput = new PasswordField();
    Label errorLbl = new Label();
    LoginController controller = new LoginController(loginEmailInput, loginPassInput, errorLbl);

    // Initializes the Login view, creating UI components and setting up event handlers
    public GridPane initialize() {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Back to Register");

        form.add(new Label("Email:"), 0, 0);
        form.add(loginEmailInput, 1, 0);

        form.add(new Label("Password:"), 0, 1);
        form.add(loginPassInput, 1, 1);

        form.add(loginBtn, 0, 2);
        form.add(registerBtn, 1, 2);
        
        form.add(errorLbl, 0, 5, 2, 1);


        // Handles the login button click event
        loginBtn.setOnAction(e -> controller.handleLogin());

        // Handles the register button click event
        registerBtn.setOnAction(e -> controller.register());
        
        Scene loginScene = new Scene(form, 400, 300);
        setScene(loginScene); 
        setTitle("Login Page");
        show();

        return form;
    }
    
}