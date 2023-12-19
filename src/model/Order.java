package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Connect;

public class Order {

    private int orderId;
    private int orderUserId;
    private int orderItemId;  
    private String orderUserName;
    private String orderStatus;
    private String orderDate;
    private Double orderTotal;
    private User orderUser;
    
    public Order(int orderId, int orderUserId, String orderUserName, String orderStatus, String orderDate, Double orderTotal) {
        this.orderId = orderId;
        this.orderUserId = orderUserId;
        this.orderUserName = orderUserName;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }
    
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

	public String getOrderUserName() {
		return orderUserName;
	}

	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}
    
	// Creates a new order with the provided user ID and order date. Returns the ID of the created order
    public static int createOrder(int userId, String orderDate) {
        try {
            String query = "INSERT INTO orders (orderUserId, orderStatus, orderDate, orderTotal) VALUES (?, 'Pending', ?, ?)";

            try (PreparedStatement ps = Connect.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, orderDate);
                ps.setDouble(3, 0.0); 
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    updateOrderTotal(orderId);
                    return orderId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; 
    }

    // Retrieves the next available order ID
    public static int getNextAvailableOrderId() {
        String query = "SELECT MAX(orderId) + 1 AS nextOrderId FROM orders";
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nextOrderId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }


 	// Updates the quantity of an order item within a specific order
    public void updateOrderQuantity(int orderId, int orderItemId, int quantity) {
        String query = "UPDATE orderitems SET quantity = ? WHERE orderItemId = ? AND orderId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setDouble(1, quantity);
            ps.setInt(2, orderItemId);
            ps.setInt(3, orderId);

            Connect.getConnection().executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Updates the status of an order item within a specific order
    public static void updateOrder(int orderId, int orderItemId, String orderStatus) {
        String query = "UPDATE orders SET orderStatus = ? WHERE orderItemId = ? AND orderId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setString(1, orderStatus);
            ps.setInt(2, orderItemId);
            ps.setInt(3, orderId);

            Connect.getConnection().executeUpdate(ps);
            
            Order orderToUpdate = getOrderByOrderId(orderId);
            if (orderToUpdate != null) {
                orderToUpdate.setOrderStatus(orderStatus);
            }
            updateOrderTotal(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Updates the status of an entire order.
    public static void updateOrder(int orderId, String orderStatus) {
        String query = "UPDATE orders SET orderStatus = ? WHERE orderId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setString(1, orderStatus);
            ps.setInt(2, orderId);

            Connect.getConnection().executeUpdate(ps);
            
            Order orderToUpdate = getOrderByOrderId(orderId);
            if (orderToUpdate != null) {
                orderToUpdate.setOrderStatus(orderStatus);
            }
            updateOrderTotal(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	// Deletes an order with the specified ID and updates the total amount
    public static void deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE orderId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);

            Connect.getConnection().executeUpdate(ps);
            updateOrderTotal(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   	// Retrieves all orders belonging to a specific customer
    public static ArrayList<Order> getAllOrdersByCustomerId(int customerId) {
        ArrayList<Order> orders = new ArrayList<>();

        String query = "SELECT orders.*, users.userName FROM orders JOIN users ON orders.orderUserId = users.userId WHERE orders.orderUserId = ?;";
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                String userName = rs.getString("userName");
                String status = rs.getString("orderStatus");
                String date = rs.getString("orderDate");
                Double total = rs.getDouble("orderTotal");

                orders.add(new Order(orderId, customerId, userName, status, date, total));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

   	// Retrieves an order based on its ID
    public static Order getOrderByOrderId(int orderId) {
        String query = "SELECT orders.*, users.userId, users.userName FROM orders JOIN users ON orders.orderUserId = users.userId WHERE orders.orderId = ?";
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String status = rs.getString("orderStatus");
                String date = rs.getString("orderDate");
                Double total = rs.getDouble("orderTotal");

                return new Order(orderId, userId, userName, status, date, total);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Retrieves the last order for a specific customer
    public static Order getLastOrder(int customerId) {
        String query = "SELECT o.*, users.userName FROM orders AS o JOIN users ON o.orderUserId = users.userId WHERE o.orderUserId = ? ORDER BY orderDate DESC LIMIT 1;";
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
        	ps.setInt(1, customerId);
        	
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	int orderId = rs.getInt("orderId");
            	String userName = rs.getString("userName");
                String status = rs.getString("orderStatus");
                String date = rs.getString("orderDate");
                Double total = rs.getDouble("orderTotal");
                
                System.out.println("Orderd id: " + orderId);
                System.out.println("Updated order status: " + status);
                
                return new Order(orderId, customerId, userName, status, date, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 	
    // Checks if an order is processed (paid, prepared, or served) or still pending
    public static String isOrderProcessed(int orderId) {
        String query = "SELECT orderStatus FROM orders WHERE orderId = ?";
        
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String orderStatus = rs.getString("orderStatus");
                return orderStatus;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

   	// Updates the total amount of an order based on its ID
    public static void updateOrderTotal(int orderId) {
        String query = "UPDATE orders o " + 
                       "SET o.orderTotal = COALESCE(( " + 
                       "    SELECT SUM(oi.quantity * mi.menuItemPrice) " + 
                       "    FROM orderitems oi " + 
                       "    JOIN menuitems mi ON oi.menuItemId = mi.menuItemId " + 
                       "    WHERE oi.orderId = o.orderId " + 
                       "    GROUP BY oi.orderId " + 
                       "), 0) " +  
                       "WHERE o.orderId = ?;";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the total amount of an order based on its ID
    public static Double getOrderTotalByOrderId(int orderId) {
        String query = "SELECT orderTotal FROM orders WHERE orderId = ?";
        
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("orderTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Retrieves an order with details of users and items
	public static Order getOrder() {
		String query = "SELECT DISTINCT orders.orderId, users.userId, users.userName, "
				+ "orders.orderStatus, orders.orderDate, orders.orderTotal "
				+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
				+ "JOIN users ON orders.orderUserId = users.userId;";
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
        	
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	int orderId = rs.getInt("orderId");
            	int userId = rs.getInt("userId");
            	String userName = rs.getString("userName");
                String status = rs.getString("orderStatus");
                String date = rs.getString("orderDate");
                Double total = rs.getDouble("orderTotal");
                
                return new Order(orderId, userId, userName, status, date, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}

    // Retrieves all the orders
	public static ArrayList<Order> getAllOrderList() {
	    ArrayList<Order> orders = new ArrayList<>();

		String query = "SELECT DISTINCT orders.orderId, users.userId, users.userName, "
				+ "orders.orderStatus, orders.orderDate, orders.orderTotal "
				+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
				+ "JOIN users ON orders.orderUserId = users.userId;";	    
		
		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            int orderId = rs.getInt("orderId");
	            int userId = rs.getInt("userId");
	            String userName = rs.getString("userName");
	            String status = rs.getString("orderStatus");
	            String date = rs.getString("orderDate");
	            Double total = rs.getDouble("orderTotal");

	            Order order = new Order(orderId, userId, userName, status, date, total);

	            orders.add(order);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orders;
	}
	
	// Retrieves all prepared orders
		public static ArrayList<Order> getAllChefOrderList() {
		    ArrayList<Order> orders = new ArrayList<>();

			String query = "SELECT DISTINCT orders.orderId, users.userId, users.userName, "
					+ "orders.orderStatus, orders.orderDate, orders.orderTotal "
					+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
					+ "JOIN users ON orders.orderUserId = users.userId "
					+ "WHERE orders.orderStatus <> 'Served';";	    
			
			try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            int orderId = rs.getInt("orderId");
		            int userId = rs.getInt("userId");
		            String userName = rs.getString("userName");
		            String status = rs.getString("orderStatus");
		            String date = rs.getString("orderDate");
		            Double total = rs.getDouble("orderTotal");

		            Order order = new Order(orderId, userId, userName, status, date, total);

		            orders.add(order);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return orders;
		}
	
	// Retrieves all paid orders
	public static ArrayList<Order> getAllPaidOrderList() {
	    ArrayList<Order> orders = new ArrayList<>();

		String query = "SELECT DISTINCT orders.orderId, users.userId, users.userName, "
				+ "orders.orderStatus, orders.orderDate, orders.orderTotal "
				+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
				+ "JOIN users ON orders.orderUserId = users.userId "
				+ "WHERE orders.orderStatus = 'Paid';";	    
		
		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            int orderId = rs.getInt("orderId");
	            int userId = rs.getInt("userId");
	            String userName = rs.getString("userName");
	            String status = rs.getString("orderStatus");
	            String date = rs.getString("orderDate");
	            Double total = rs.getDouble("orderTotal");

	            Order order = new Order(orderId, userId, userName, status, date, total);

	            orders.add(order);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orders;
	}
	
	// Retrieves all prepared orders
	public static ArrayList<Order> getAllPreparedOrderList() {
	    ArrayList<Order> orders = new ArrayList<>();

		String query = "SELECT DISTINCT orders.orderId, users.userId, users.userName, "
				+ "orders.orderStatus, orders.orderDate, orders.orderTotal "
				+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
				+ "JOIN users ON orders.orderUserId = users.userId "
				+ "WHERE orders.orderStatus = 'Prepared';";	    
		
		try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            int orderId = rs.getInt("orderId");
	            int userId = rs.getInt("userId");
	            String userName = rs.getString("userName");
	            String status = rs.getString("orderStatus");
	            String date = rs.getString("orderDate");
	            Double total = rs.getDouble("orderTotal");

	            Order order = new Order(orderId, userId, userName, status, date, total);

	            orders.add(order);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orders;
	}
}
