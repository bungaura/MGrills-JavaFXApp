package viewChef;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChefDashboard extends Stage {
    private BorderPane root = new BorderPane();
    private Label welcomeLbl;

    public void initialize() {
        VBox menu = new VBox();
        menu.setPrefWidth(150);

        welcomeLbl = new Label("Welcome Chef!");

        Button viewOrderButton = createMenuButton("View Order", "viewOrder");
        Button logoutBtn = createMenuButton("Logout", "logout");

        menu.getChildren().addAll(viewOrderButton, logoutBtn);

        root.setLeft(menu);

        showViewOrderContent();

        viewOrderButton.setOnAction(e -> showViewOrderContent());
        logoutBtn.setOnAction(e -> {
        	close();
        });


        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
        setTitle("Chef Dashboard");
        show();
    }

    private Button createMenuButton(String buttonText, String viewName) {
        Button button = new Button(buttonText);
        button.setPrefWidth(150);
        button.setId(viewName);
        button.setOnAction(event -> showContent(viewName));
        return button;
    }

    private void showContent(String viewType) {
        switch (viewType) {
            case "View Order":
                showViewOrderContent();
                break;
        }
    }
    
    private void showViewOrderContent() {
    	ChefOrderView view = new ChefOrderView();
    	view.initialize();
    	
        VBox centerContent = new VBox(new Label("View Order"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: lightblue;");
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(view.getRoot());

        root.setCenter(centerContent);
    }
  
}
