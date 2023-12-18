package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import viewController.RegisterController;

public class Register extends Stage {
    private TextField registerNameInput = new TextField();
    private TextField registerEmailInput = new TextField();
    private PasswordField registerPassInput = new PasswordField();
    private PasswordField registerConfirmPassInput = new PasswordField();
    private Label errorLbl = new Label();
    private User currentUser;
    RegisterController controller = new RegisterController(registerNameInput, registerEmailInput, registerPassInput, registerConfirmPassInput, errorLbl, currentUser);

    public GridPane initialize() {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        Button registerBtn = new Button("Register");
        Button loginBtn = new Button("Login");

        form.add(new Label("Name:"), 0, 0);
        form.add(registerNameInput, 1, 0);

        form.add(new Label("Email:"), 0, 1);
        form.add(registerEmailInput, 1, 1);

        form.add(new Label("Password:"), 0, 2);
        form.add(registerPassInput, 1, 2);

        form.add(new Label("Confirm Password:"), 0, 3);
        form.add(registerConfirmPassInput, 1, 3);

        form.add(registerBtn, 0, 4);
        form.add(loginBtn, 1, 4);

        form.add(errorLbl, 0, 5, 2, 1);

        registerBtn.setOnAction(e -> controller.handleRegister());
        loginBtn.setOnAction(e -> {
        	new Login().initialize();
        	close();
        });

        Scene registerScene = new Scene(form, 400, 300);
        setScene(registerScene); 
        setTitle("Register Page");
        show();

        return form;
    }

}
