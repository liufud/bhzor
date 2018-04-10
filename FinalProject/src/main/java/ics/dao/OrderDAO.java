package ics.dao;

import java.util.Collection;
import java.util.List;

import ics.model.Order;
import ics.model.ShippedOrder;

public interface OrderDAO {
	void createOrder(Order order);
	void createShippedOrder(ShippedOrder order);
	Order getOrder(Long orderID);
	List<Order> getOrderByUserId(Long userId);
	List<Order> getOrderByUsername(String username);
	public List<ShippedOrder> getShippedOrderByShelf(Long orderID, Long shelfID, String productName);
	public List<ShippedOrder> getShippedOrderByID(Long orderID, String productName);
	public List<Order> getShippedOrdersByStatus(String orderStatus);
	public Order getLatestOrder();
	public Collection<Order> listOrders();
	public Collection<ShippedOrder> listShippedOrders();
	Collection<Order> listOrders(String statusType, String status);
	public void delete(Long orderID);
}
