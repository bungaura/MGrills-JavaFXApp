package viewCashier;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//CashierDashboard class representing the view for the cashier dashboard
public class CashierDashboard extends Stage {
    private BorderPane root = new BorderPane();
    private Label welcomeLbl;


    //Initializes the cashier dashboard view, including the menu, welcome label, and buttons
    public void initialize() {
        VBox menu = new VBox();
        menu.setPrefWidth(150);

        welcomeLbl = new Label("Welcome Cashier!");

        Button viewOrderButton = createMenuButton("View Order", "viewOrder");
        Button viewReceiptButton = createMenuButton("View Receipt", "viewOrder");
        Button logoutBtn = createMenuButton("Logout", "logout");

        menu.getChildren().addAll(viewOrderButton, viewReceiptButton, logoutBtn);

        root.setLeft(menu);

        showViewOrderContent();

        viewOrderButton.setOnAction(e -> showViewOrderContent());
        viewReceiptButton.setOnAction(e -> showViewReceiptContent());
        logoutBtn.setOnAction(e -> {
        	close();
        });

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
        setTitle("Cashier Dashboard");
        show();
    }

    //Creates and configures a menu button
    private Button createMenuButton(String buttonText, String viewName) {
        Button button = new Button(buttonText);
        button.setPrefWidth(150);
        button.setId(viewName);
        button.setOnAction(event -> showContent(viewName));
        return button;
    }

    //Shows the content based on the selected view type
    private void showContent(String viewType) {
        switch (viewType) {
            case "View Receipt":
                showViewReceiptContent();
                break;
            case "View Order":
                showViewOrderContent();
                break;
        }
    }

    //Shows the view for viewing receipts
    private void showViewReceiptContent() {
    	CashierReceiptView view = new CashierReceiptView();
    	view.initialize();
    	
        VBox centerContent = new VBox(new Label("View Receipt"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: lightgreen;");
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(view.getRoot());
        
        root.setCenter(centerContent);
    }
    
    //Shows the view for viewing orders
    private void showViewOrderContent() {
    	CashierOrderView view = new CashierOrderView();
    	view.initialize();
    	
        VBox centerContent = new VBox(new Label("View Order"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: lightblue;");
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(view.getRoot());

        root.setCenter(centerContent);
    }
  
}
