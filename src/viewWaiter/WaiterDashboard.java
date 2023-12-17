package viewWaiter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Waiter Dashboard class representing the view for the Waiter's dashboard
public class WaiterDashboard extends Stage {
    private BorderPane root = new BorderPane();
    private Label welcomeLbl;

    // Initializes the Waiter Dashboard View
    public void initialize() {
        VBox menu = new VBox();
        menu.setPrefWidth(150);

        welcomeLbl = new Label("Welcome Waiter!");

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
        setTitle("Waiter Dashboard");
        show();
    }

    // Create a menu button with specified text and view name
    private Button createMenuButton(String buttonText, String viewName) {
        Button button = new Button(buttonText);
        button.setPrefWidth(150);
        button.setId(viewName);
        button.setOnAction(event -> showContent(viewName));
        return button;
    }

    // Show content based on the specified view type
    private void showContent(String viewType) {
        switch (viewType) {
            case "View Order":
                showViewOrderContent();
                break;
        }
    }
    
    // Show the View Order content in the center of the root
    private void showViewOrderContent() {
    	WaiterOrderView view = new WaiterOrderView();
    	view.initialize();
    	
        VBox centerContent = new VBox(new Label("View Order"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: lightblue;");
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(view.getRoot());

        root.setCenter(centerContent);
    }
  
}
