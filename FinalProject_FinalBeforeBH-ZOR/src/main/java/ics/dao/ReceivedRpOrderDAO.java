package ics.dao;

import java.util.Collection;
import java.util.List;

import ics.model.ReceivedRpOrder;

public interface ReceivedRpOrderDAO {
	void createOrder(ReceivedRpOrder order);
	ReceivedRpOrder getOrder(Long orderID);
	List<ReceivedRpOrder> getOrderByUserId(Long userId);
	public List<ReceivedRpOrder> getOrderByLot(Long lotID, String productName);
	public Collection<ReceivedRpOrder> listOrders();
	public Collection<ReceivedRpOrder> listOrders(String orderStatus);
	public Collection<ReceivedRpOrder> listOrders(Long rpOrderID, String productName);
	public void delete(Long orderID);
}
