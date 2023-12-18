package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private VBox root;
    private Label welcomeLbl, infoLbl;
    private Button registerBtn, loginBtn;

    public static void main(String[] args) {
        launch(args);
    }

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

    private void showWelcomeWindow() {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);

        welcomeLbl = new Label("Welcome to MYstic Grill!");
        infoLbl = new Label("Register or Login here to access this program!");
        registerBtn = new Button("Register");
        loginBtn = new Button("Login");

        registerBtn.setOnAction(e -> {
        	new Register().initialize();
        });
        
        loginBtn.setOnAction(e -> {
            new Login().initialize();
        });

        root.getChildren().addAll(welcomeLbl, infoLbl, registerBtn, loginBtn);
    }
}
