package ics.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ics.dao.ReceivedRpOrderDAO;
import ics.model.ReceivedRpOrder;

public class ReceivedRpOrderServieImpl implements ReceivedRpOrderService {
	@Autowired
	private ReceivedRpOrderDAO receivedRpOrderDAO;
	
	public void createOrder(ReceivedRpOrder order) {
		receivedRpOrderDAO.createOrder(order);
	}

	public ReceivedRpOrder getOrder(Long orderID) {
		return receivedRpOrderDAO.getOrder(orderID);
	}

	public List<ReceivedRpOrder> getOrderByUserId(Long userId) {
		return receivedRpOrderDAO.getOrderByUserId(userId);
	}

	public Collection<ReceivedRpOrder> listOrders() {
		return receivedRpOrderDAO.listOrders();
	}

	public Collection<ReceivedRpOrder> listOrders(String orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Long orderID) {
		receivedRpOrderDAO.delete(orderID);
	}

	public Collection<ReceivedRpOrder> listOrders(Long rpOrderID, String productName) {
		return receivedRpOrderDAO.listOrders(rpOrderID, productName);
	}

	public List<ReceivedRpOrder> getOrderByLot(Long lotID, String productName) {
		return receivedRpOrderDAO.getOrderByLot(lotID, productName);
	}

}
