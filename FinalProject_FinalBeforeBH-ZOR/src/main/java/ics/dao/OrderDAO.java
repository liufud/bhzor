package ics.dao;

import java.util.Collection;
import java.util.List;

import ics.model.Order;

public interface OrderDAO {
	void createOrder(Order order);
	Order getOrder(Long orderID);
	List<Order> getOrderByUserId(Long userId);
	List<Order> getOrderByUsername(String username);
	public Collection<Order> listOrders();
	Collection<Order> listOrders(String statusType, String status);
	public void delete(Long orderID);
}
