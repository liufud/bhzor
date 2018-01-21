package ics.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.dao.OrderDAO;
import ics.model.Order;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDAO orderDAO;
	
	public void createOrder(Order order) {
		orderDAO.createOrder(order);
	}
	
	
	public Order getOrder(Long orderID) {
		return orderDAO.getOrder(orderID);
	}

	public Collection<Order> listOrders() {
		return orderDAO.listOrders();
	}

	public void delete(Long orderID) {
		orderDAO.delete(orderID);
	}

	public List<Order> getOrderByUsername(String username) {		
		return orderDAO.getOrderByUsername(username);
	}

}
