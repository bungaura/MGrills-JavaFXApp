package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Connect;

public class Receipt {
	
	private int receiptId;
	private int receiptOrderId;
	private Double receiptPaymentAmount;
	private String receiptPaymentDate;
	private String receiptPaymentType;

	public Receipt(int receiptId, int receiptOrderId, Double receiptPaymentAmount, String receiptPaymentDate,
			String receiptPaymentType) {
		super();
		this.receiptId = receiptId;
		this.receiptOrderId = receiptOrderId;
		this.receiptPaymentAmount = receiptPaymentAmount;
		this.receiptPaymentDate = receiptPaymentDate;
		this.receiptPaymentType = receiptPaymentType;
	}

	public static void createReceipt(int receiptOrderId, String receiptPaymentType, Double receiptPaymentAmount, String receiptPaymentDate) {
		String query = "INSERT INTO receipts (receiptOrderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, receiptOrderId);
            ps.setDouble(2, receiptPaymentAmount);
            ps.setString(3, receiptPaymentDate);
            ps.setString(4, receiptPaymentType);

            Connect.getConnection().executeUpdate(ps);

        }
    catch (SQLException e) {
        e.printStackTrace();
    }
	}
	
	public void updateReceipt(int orderId, String receiptPaymentType, Double receiptPaymentAmount, String receiptPaymentDate) {
		
	}
	
	public void deleteReceipt(int orderId) {
		
	}
	
	public void getReceiptById(int receiptId) {
		
	}
	
	public static ArrayList<Receipt> getAllReceipts() {
		
		ArrayList<Receipt> receipts = new ArrayList<>();
		String query = "SELECT * FROM receipts";
		ResultSet rs = Connect.getConnection().executeQuery(query);
		try {
			while(rs.next()) { //selama ada row di setelahnya
				int receiptId = rs.getInt(1);
				int receiptOrderId = rs.getInt(2);
				Double receiptPaymentAmount = rs.getDouble(3);
				String receiptPaymentDate = rs.getString(4);
				String receiptPaymentType = rs.getString(5);
				receipts.add(new Receipt(receiptId, receiptOrderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return receipts;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public int getReceiptOrderId() {
		return receiptOrderId;
	}

	public void setReceiptOrderId(int receiptOrderId) {
		this.receiptOrderId = receiptOrderId;
	}

	public Double getReceiptPaymentAmount() {
		return receiptPaymentAmount;
	}

	public void setReceiptPaymentAmount(Double receiptPaymentAmount) {
		this.receiptPaymentAmount = receiptPaymentAmount;
	}

	public String getReceiptPaymentDate() {
		return receiptPaymentDate;
	}

	public void setReceiptPaymentDate(String receiptPaymentDate) {
		this.receiptPaymentDate = receiptPaymentDate;
	}

	public String getReceiptPaymentType() {
		return receiptPaymentType;
	}

	public void setReceiptPaymentType(String receiptPaymentType) {
		this.receiptPaymentType = receiptPaymentType;
	}
	
//	CREATE TABLE orders (
//		    orderId INT PRIMARY KEY AUTO_INCREMENT,
//		    orderUserId INT,
//		    orderStatus VARCHAR(50) NOT NULL,
//		    orderDate VARCHAR(50) NOT NULL,
//		    orderTotal INT NOT NULL,
//		    FOREIGN KEY (orderUserId) REFERENCES users(userId)
//		);
//
//		CREATE TABLE orderitems (
//		    orderItemId INT PRIMARY KEY AUTO_INCREMENT,
//		    orderId INT,
//		    menuItemId INT,
//		    quantity INT NOT NULL,
//		    FOREIGN KEY (orderId) REFERENCES orders(orderId),
//		    FOREIGN KEY (menuItemId) REFERENCES menuitems(menuItemId)
//		);
//
//		CREATE TABLE receipts (
//		    receiptId INT PRIMARY KEY AUTO_INCREMENT,
//		    receiptOrderId INT,
//		    receiptPaymentAmount DOUBLE NOT NULL,
//		    receiptPaymentDate VARCHAR(50) NOT NULL,
//		    receiptPaymentType VARCHAR(50) NOT NULL,
//		    FOREIGN KEY (receiptOrderId) REFERENCES orders(orderId)
//		);
}
