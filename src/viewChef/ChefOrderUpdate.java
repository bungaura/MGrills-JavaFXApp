package viewChef;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import viewController.ChefOrderUpdateController;


//ChefOrderUpdate class representing the view for updating menu quantity of selected order in the chef dashboard
public class ChefOrderUpdate extends Stage {
	private TextField qtyInput = new TextField();
	private Label nameLbl, messageLbl;
	private VBox root;
	private Button cancelBtn, saveBtn, removeBtn;
	private ChefOrderUpdateController controller;
	
		// Initialize the Customer Order Update view
		public void initialize(int orderId, int orderItemId, int menuItemId, String menuItemName, int qty) {
			root = new VBox();
	        root.setAlignment(Pos.CENTER);
	        root.setSpacing(20);

	        showMenuDetails(orderId, orderItemId, menuItemId, menuItemName, qty);
			controller = new ChefOrderUpdateController();

	        Scene scene = new Scene(root, 550, 350);
	        setScene(scene);
	        setTitle(menuItemName);
	        show();
		}
		

	    // Show the menu details in the form
		private void showMenuDetails(int orderId, int orderItemId, int menuItemId, String menuItemName, int qty) {
			GridPane form = new GridPane();
	        form.setVgap(20);
	        form.setHgap(10);
	        form.setAlignment(Pos.CENTER);
	        
	        messageLbl = new Label();
			nameLbl = new Label(menuItemName);
	        nameLbl.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
	        form.add(nameLbl, 0, 1);
	                
	        qtyInput.setText(String.valueOf(qty));
	        Label quantityLbl = new Label("Quantity:");
	        form.add(quantityLbl, 0, 2);
	        form.add(qtyInput, 1, 2);
	                
	        cancelBtn = new Button("Cancel");
	        saveBtn = new Button("Update Order");
	        removeBtn = new Button("Remove Order");
	                
	        cancelBtn.setOnAction(e -> close());
	        
	        saveBtn.setOnAction(e -> {
	            int qtyLastInput = Integer.parseInt(qtyInput.getText());
	        	String result = controller.saveChanges(orderId, qtyLastInput, orderItemId, menuItemId);
	        	messageLbl.setText(result);
	            messageLbl.setStyle(result.contains("Cannot") ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
	        });
	       
	        removeBtn.setOnAction(e -> {
	        	String result = controller.removeOrder(orderId, orderItemId);
	        	messageLbl.setText(result);
	            messageLbl.setStyle(result.contains("Removed") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
	        });
	        
	        form.add(saveBtn, 0, 3);
	        form.add(removeBtn, 0, 4);
	        form.add(cancelBtn, 0, 5);
	        form.add(messageLbl, 0, 6);
	        
	        root.setAlignment(Pos.CENTER);
	        root.getChildren().addAll(form);
		}
		


}
