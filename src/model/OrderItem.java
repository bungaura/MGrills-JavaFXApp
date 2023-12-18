package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private String menuItemName;
    private Double orderTotal;
    private String orderDate;
    private String orderStatus;

    public OrderItem(int orderItemId, int orderId, int menuItemId, String menuItemName, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
    }

    public static void createOrderItem(int orderId, int menuItemId, int quantity) {
        String query = "INSERT INTO orderitems (orderId, menuItemId, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);

            ps.executeUpdate();
            updateOrderTotal(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOrderItem(int orderItemId, int menuItemId, int quantity) {
        String query = "UPDATE orderitems SET quantity = ? WHERE orderItemId = ? AND menuItemId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, orderItemId);
            ps.setInt(3, menuItemId);

            Connect.getConnection().executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrderItem(int orderItemId) {
        String query = "DELETE FROM orderitems WHERE orderItemId = ?";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderItemId);

            Connect.getConnection().executeUpdate(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void updateOrderTotal(int orderId) {
        String query = "UPDATE orders o "
                + "SET o.orderTotal = ( "
                + "    SELECT SUM(oi.quantity * mi.menuItemPrice) "
                + "    FROM orderitems oi "
                + "    JOIN menuitems mi ON oi.menuItemId = mi.menuItemId "
                + "    WHERE oi.orderId = o.orderId "
                + "    GROUP BY oi.orderId "
                + ") "
                + "WHERE o.orderId = ?;";

        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//    public static ArrayList<OrderItem> getOrderItemsByCustomerId(int customerId) {
//        ArrayList<OrderItem> orderitems = new ArrayList<>();
//        orders = new ArrayList<>();
////        String query = "SELECT orders.orderId, orderitems.orderItemId, users.userId, users.userName, menuitems.menuItemId, menuitems.menuItemName, orderitems.quantity, orders.orderDate, orders.orderTotal, orders.orderStatus " +
////                "FROM orderitems, orders " +
////                "JOIN menuitems ON orderitems.menuItemId = menuitems.menuItemId " +
////                "JOIN orders ON orderitems.orderId = orders.orderId " +
////                "JOIN users ON orders.userId = users.userId " +
////                "WHERE orders.orderUserId = ?";
//        String query = "SELECT orders.orderId, orderitems.orderItemId, users.userId, users.userName, menuitems.menuItemId, menuitems.menuItemName, orderitems.quantity, orders.orderDate, orders.orderTotal, orders.orderStatus "
//        		+ "FROM orders JOIN orderitems ON orders.orderId = orderitems.orderId "
//        		+ "JOIN menuitems ON orderitems.menuItemId = menuitems.menuItemId "
//        		+ "JOIN users ON orders.orderUserId = users.userId "
//        		+ "WHERE orders.orderUserId = ?;";
//
//        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
//            ps.setInt(1, customerId);
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int orderId = rs.getInt("orderId");
//                int orderItemId = rs.getInt("orderItemId");
//                int menuItemId = rs.getInt("menuItemId");
//                String menuItemName = rs.getString("menuItemName");
//                int quantity = rs.getInt("quantity");
//                Double total = rs.getDouble("orderTotal");
//                String date = rs.getString("orderDate");
//                String status = rs.getString("orderStatus");
//                String userName = rs.getString("userName");
//
//                // Use the orderId to create an Order instance
//                orders.add(new Order(orderId, customerId, userName, orderItemId, status, date, total));
//
//                // Add the OrderItem to the list
//                orderitems.add(new OrderItem(orderItemId, orderId, menuItemId, menuItemName, quantity));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return orderitems;
//    }

//    public static ArrayList<OrderItem> getOrderItemsByCustomerId(int customerId) {
//        ArrayList<OrderItem> orderitems = new ArrayList<>();
//        orders = new ArrayList<>();
//        String query = "SELECT orderitems.orderItemId, orderitems.orderId, menuitems.menuItemId, menuitems.menuItemName, orderitems.quantity, orders.orderTotal, orders.orderDate, orders.orderStatus " +
//                "FROM orderitems " +
//                "JOIN menuitems ON orderitems.menuItemId = menuitems.menuItemId " +
//                "JOIN orders ON orderitems.orderId = orders.orderId " +
//                "WHERE orders.orderUserId = ?";
//
//        try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
//            ps.setInt(1, customerId);
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int orderItemId = rs.getInt("orderItemId");
//                int orderId = rs.getInt("orderId");
//                int menuItemId = rs.getInt("menuItemId");
//                String menuItemName = rs.getString("menuItemName");
//                int quantity = rs.getInt("quantity");
//                Double total = rs.getDouble("orderTotal");
//                String date = rs.getString("orderDate");
//                String status = rs.getString("orderStatus");
//
//                orderitems.add(new OrderItem(orderItemId, orderId, menuItemId, menuItemName, quantity));
//                orders.add(new Order(orderId, customerId, orderItemId, status, date, total));
//                
////                System.out.println("Order Total: " + total + ", Order Date: " + date + ", Order Status: " + status);
//
//            }
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return orderitems;
//    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public static ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId) {
	    ArrayList<OrderItem> orderItems = new ArrayList<>();
	    String query = "SELECT orders.orderUserId, orders.orderTotal, "
	    		+ "orders.orderDate, orderitems.orderItemId, orderitems.menuItemId, "
	    		+ "menuitems.menuItemName, orderitems.quantity, users.userName "
	    		+ "FROM orders JOIN orderitems ON orderitems.orderId = orders.orderId "
	    		+ "JOIN menuitems ON menuitems.menuItemId = orderitems.menuItemId "
	    		+ "JOIN orders AS o ON o.orderId = orderitems.orderId "
	    		+ "JOIN users ON users.userId = o.orderUserId "
	    		+ "WHERE orders.orderId = ?;";
	    
	    try (PreparedStatement ps = Connect.getConnection().prepareStatement(query)) {
	        ps.setInt(1, orderId);
	        
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            int orderItemId = rs.getInt("orderItemId");
	            int menuItemId = rs.getInt("menuItemId");
	            int quantity = rs.getInt("quantity");
	            String menuItemName = rs.getString("menuItemName");
	            
	            orderItems.add(new OrderItem(orderItemId, orderId, menuItemId, menuItemName, quantity));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return orderItems;
	}

}

	
//	-- Insert order
//	INSERT INTO orders (orderUserId, orderStatus, orderDate, orderTotal) VALUES (37, 'Pending', NOW(), 0);
//
//	-- Retrieve the orderId of the last inserted order
//	SET @lastOrderId = LAST_INSERT_ID();
//
//	-- Insert orderItem pertama
//	INSERT INTO orderitems (orderId, menuItemId, quantity) VALUES (@lastOrderId, 1, 2);
//
//	-- Insert orderItem kedua
//	INSERT INTO orderitems (orderId, menuItemId, quantity) VALUES (@lastOrderId, 4, 3);

//	SELECT
//    orders.orderId,
//    menuitems.menuItemName AS menuItemName,
//    orderitems.quantity as qty,
//    orders.orderDate AS 'date',
//    orders.orderTotal as total,
//    orders.orderStatus as 'status',
//    users.userName AS userName
//FROM
//    orders
//JOIN
//    users ON orders.orderUserId = users.userId
//JOIN
//    orderitems ON orders.orderId = orderitems.orderId
//JOIN
//    menuitems ON orderitems.menuItemId = menuitems.menuItemId
//WHERE
//    orders.orderId = 4;



	

