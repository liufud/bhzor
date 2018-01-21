package ics.services;

import java.util.Collection;
import java.util.List;

import ics.model.Order;


public interface OrderService {
	void createOrder(Order order);
	Order getOrder(Long orderID);
	List<Order> getOrderByUsername(String username);
	public Collection<Order> listOrders();
	public void delete(Long orderID);
}
