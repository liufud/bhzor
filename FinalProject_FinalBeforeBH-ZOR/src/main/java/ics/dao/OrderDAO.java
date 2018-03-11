package ics.dao;

import java.util.Collection;
import java.util.List;

import ics.model.Order;

public interface OrderDAO {
	void createOrder(Order order);
	Order getOrder(Long orderID);
	List<Order> getOrderByUserId(Long userId);
	public Collection<Order> listOrders();
	public void delete(Long orderID);
}
