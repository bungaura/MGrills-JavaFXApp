package viewAdmin;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//AdminDashboard class representing the admin dashboard view.
public class AdminDashboard extends Stage {
	private Label welcomeLbl;
	private BorderPane root = new BorderPane();
	private UserManagement manageUser;
	private MenuItemManagement manageMenuItem;

	//Initializes the admin dashboard with menu buttons and default content
    public void initialize() {
    	VBox menu = new VBox();
        menu.setPrefWidth(150);

        welcomeLbl = new Label("Welcome Admin!");
        
        //Creates menu buttons with specified text and view identifier
        Button viewUsersBtn = createMenuButton("View Users", "viewUsers");
        Button viewMenuItemBtn = createMenuButton("View Menu Items", "viewMenuItems");
        Button addMenuItemBtn = createMenuButton("Add Menu Item", "addMenuItem");
        Button logoutBtn = createMenuButton("Logout", "logout");

        menu.getChildren().addAll(viewUsersBtn, viewMenuItemBtn, addMenuItemBtn, logoutBtn);

        root.setLeft(menu);

        showViewUsersContent();
        
        viewUsersBtn.setOnAction(e -> showViewUsersContent());
        viewMenuItemBtn.setOnAction(e -> showViewMenuItemsContent());
        addMenuItemBtn.setOnAction(e -> showAddMenuItemContent());
        logoutBtn.setOnAction(e -> close());

        setOnCloseRequest(e -> handleCloseRequest());

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
        setTitle("Admin Dashboard");
        show();
    }
    
    // Creates a menu button with specified text and view identifier
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
            case "View Users":
                showViewUsersContent();
                break;
            case "View Menu Item":
                showViewMenuItemsContent();
                break;
            case "Add Menu Item":
                showAddMenuItemContent();
                break;
        }
    }
	
    //Displays the content for viewing menu items
	private void showViewMenuItemsContent() {
		MenuItemManagement manageMenuItem = new MenuItemManagement();
		manageMenuItem.initialize();

	    VBox centerContent = new VBox(
	            new Label("View Menu Items"),
	            welcomeLbl
	    );

	    centerContent.setStyle("-fx-background-color: lightyellow;");
	    centerContent.setAlignment(Pos.TOP_CENTER);
	    centerContent.getChildren().add(manageMenuItem.getRoot());

	    root.setCenter(centerContent);
    }
	
	//Displays the content for viewing users
	private void showViewUsersContent() {
	    UserManagement manageUser = new UserManagement();
	    manageUser.initialize();

	    VBox centerContent = new VBox(
	            new Label("View Users"),
	            welcomeLbl
	    );

	    centerContent.setStyle("-fx-background-color: lightblue;");
	    centerContent.setAlignment(Pos.TOP_CENTER);
	    centerContent.getChildren().add(manageUser.getRoot());

	    root.setCenter(centerContent);
	}

	//Displays the content for adding menu items
	private void showAddMenuItemContent() {
		AddMenuItem addMenuItem = new AddMenuItem();
		addMenuItem.initialize();

	    VBox centerContent = new VBox(
	            new Label("Add Menu Items"),
	            welcomeLbl
	    );

	    centerContent.setStyle("-fx-background-color: lightpink;");
	    centerContent.setAlignment(Pos.TOP_CENTER);
	    centerContent.getChildren().add(addMenuItem.getRoot());

	    root.setCenter(centerContent);
    }
	
	//Handles the close request event, ensuring proper closure of associated views
	private void handleCloseRequest() {
        if (manageUser != null && manageUser.isShowing()) {
            manageUser.close();
        }
        if (manageMenuItem != null && manageMenuItem.isShowing()) {
            manageUser.close();
        }
    }	
   
}

