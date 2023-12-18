package viewCustomer;

import controller.OrderController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.User;

public class CustomerDashboard extends Stage {
    private BorderPane root = new BorderPane();
    private Label welcomeLbl;
    private User currentUser;
    private Order currentOrder;
    
    public CustomerDashboard(User currentUser) {
    	this.currentUser = currentUser;
    	this.currentOrder = OrderController.getLastOrderInfo(currentUser.getUserId());
    }

    public void initialize() {
        VBox menu = new VBox();
        menu.setPrefWidth(150);

        welcomeLbl = new Label("Welcome, Customer " + currentUser.getUserName()  + "!");

        Button viewMenuButton = createMenuButton("View Menu", "viewMenu");
        Button viewOrderButton = createMenuButton("View Order", "viewOrder");
        Button logoutBtn = createMenuButton("Logout", "logout");

        menu.getChildren().addAll(viewMenuButton, viewOrderButton, logoutBtn);

        root.setLeft(menu);

        showViewMenuContent();

        viewMenuButton.setOnAction(e -> showViewMenuContent());
        viewOrderButton.setOnAction(e -> showViewOrderContent());
        logoutBtn.setOnAction(e -> {
        	close();
        });

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
        setTitle("Customer Dashboard");
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
            case "View Menu":
                showViewMenuContent();
                break;
            case "View Order":
                showViewOrderContent();
                break;
        }
    }

    private void showViewMenuContent() {
    	CustomerMenuView viewMenu = new CustomerMenuView(currentUser, currentOrder);
    	viewMenu.initialize();
    	
        VBox centerContent = new VBox(new Label("View Menu"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: peachpuff;");
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(viewMenu.getRoot());
        
        root.setCenter(centerContent);
    }
    
    private void showViewOrderContent() {
    	CustomerOrderView viewOrder = new CustomerOrderView(currentUser, currentOrder);
    	viewOrder.initialize();
    	
        VBox centerContent = new VBox(new Label("View Order"), welcomeLbl);
        centerContent.setStyle("-fx-background-color: lavender;"); 
        centerContent.setAlignment(Pos.TOP_CENTER);
        centerContent.getChildren().add(viewOrder.getRoot());
        
        root.setCenter(centerContent);
    }
  
}
