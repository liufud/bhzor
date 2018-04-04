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
	public List<ShippedOrder> getShippedOrderByLot(Long orderID, Long lotID, String productName);
	public List<ShippedOrder> getShippedOrderByID(Long orderID, String productName);
	public Order getLatestOrder();
	public Collection<Order> listOrders();
	Collection<Order> listOrders(String statusType, String status);
	public void delete(Long orderID);
}
