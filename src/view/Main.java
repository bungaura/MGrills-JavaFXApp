package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Main class for the application. Launches the JavaFX application
public class Main extends Application {
    private VBox root;
    private Label welcomeLbl, infoLbl;
    private Button registerBtn, loginBtn;

    public static void main(String[] args) {
        launch(args);
    }

    // Initializes the main UI components for the welcome window
    @Override
    public void start(Stage primaryStage) {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        showWelcomeWindow();

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to MYstic Grills!");
        primaryStage.show();
    }
    
    // Shows the welcome window with options to register or login
    private void showWelcomeWindow() {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        welcomeLbl = new Label("Welcome to MYstic Grill!");
        infoLbl = new Label("Register or Login here to access this program!");
        registerBtn = new Button("Register");
        loginBtn = new Button("Login");

        // Handles the register button click event, opening the registration view
        registerBtn.setOnAction(e -> {
        	new Register().initialize();
        });

        // Handles the login button click event, opening the login view
        loginBtn.setOnAction(e -> {
            new Login().initialize();
        });

        root.getChildren().addAll(welcomeLbl, infoLbl, registerBtn, loginBtn);
    }
}
