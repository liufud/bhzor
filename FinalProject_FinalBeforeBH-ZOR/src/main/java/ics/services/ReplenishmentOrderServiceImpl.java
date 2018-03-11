package ics.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ics.dao.ReplenishmentOrderDAO;
import ics.model.ReplenishmentOrder;

public class ReplenishmentOrderServiceImpl implements ReplenishmentOrderService {
	@Autowired
	private ReplenishmentOrderDAO replenishmentOrderDAO;
	
	public void createOrder(ReplenishmentOrder order) {
		replenishmentOrderDAO.createOrder(order);
	}

	public ReplenishmentOrder getOrder(Long orderID) {
		return replenishmentOrderDAO.getOrder(orderID);
	}

	public List<ReplenishmentOrder> getOrderByUserId(Long userId) {
		return replenishmentOrderDAO.getOrderByUserId(userId);
	}

	public Collection<ReplenishmentOrder> listOrders() {
		return replenishmentOrderDAO.listOrders();
	}

	public void delete(Long orderID) {
		replenishmentOrderDAO.delete(orderID);
	}

}
