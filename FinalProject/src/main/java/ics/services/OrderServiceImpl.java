package ics.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.dao.OrderDAO;
import ics.model.Order;
import ics.model.ShippedOrder;

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

	public List<Order> getOrderByUserId(Long userId) {		
		return orderDAO.getOrderByUserId(userId);
	}


	public List<Order> getOrderByUsername(String username) {
		return orderDAO.getOrderByUsername(username);
	}


	public Collection<Order> listOrders(String statusType, String status) {
		return orderDAO.listOrders(statusType, status);
	}

	
	public List<ShippedOrder> getShippedOrderByShelf(Long orderID, Long shelfID, String productName) {
		return orderDAO.getShippedOrderByShelf(orderID, shelfID, productName);
	}


	public List<ShippedOrder> getShippedOrderByID(Long orderID, String productName) {
		return orderDAO.getShippedOrderByID(orderID, productName);
	}


	public void createShippedOrder(ShippedOrder order) {
		orderDAO.createShippedOrder(order);
	}


	public Order getLatestOrder() {
		return orderDAO.getLatestOrder();
	}


	public Collection<ShippedOrder> listShippedOrders() {
		return orderDAO.listShippedOrders();
	}


	public List<Order> getShippedOrdersByStatus(String orderStatus) {
		return orderDAO.getShippedOrdersByStatus(orderStatus);
	}


	public int qtyInInventoryByShelf(String productName, Long shelfID) {
		return orderDAO.qtyInInventoryByShelf(productName, shelfID);
	}

}
