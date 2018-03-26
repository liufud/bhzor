package ics.services;

import java.util.Collection;
import java.util.List;

import ics.model.ReceivedRpOrder;

public interface ReceivedRpOrderService {
	void createOrder(ReceivedRpOrder order);
	ReceivedRpOrder getOrder(Long orderID);
	List<ReceivedRpOrder> getOrderByUserId(Long userId);
	public List<ReceivedRpOrder> getOrderByLot(Long rpOrderID, Long lotID, String productName);
	public List<ReceivedRpOrder> getOrderByRpOrderID(Long rpOrderID, String productName);
	public Collection<ReceivedRpOrder> listOrders();
	public Collection<ReceivedRpOrder> listOrders(Long rpOrderID, String productName);
	public Collection<ReceivedRpOrder> listOrders(String orderStatus);
	public void delete(Long orderID);
}
