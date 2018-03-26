package ics.dao;

import java.util.Collection;
import java.util.List;

import ics.model.ReplenishmentOrder;

public interface ReplenishmentOrderDAO {
	void createOrder(ReplenishmentOrder order);
	ReplenishmentOrder getOrder(Long orderID);
	List<ReplenishmentOrder> getOrderByUserId(Long userId);
	public Collection<ReplenishmentOrder> listOrders();
	public Collection<ReplenishmentOrder> listOrders(String orderStatus);
	public void delete(Long orderID);
}
