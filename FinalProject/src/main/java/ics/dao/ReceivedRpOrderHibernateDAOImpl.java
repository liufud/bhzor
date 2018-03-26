package ics.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.ReceivedRpOrder;
import ics.model.ReplenishmentOrder;


public class ReceivedRpOrderHibernateDAOImpl implements ReceivedRpOrderDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public ReceivedRpOrderHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	public void createOrder(ReceivedRpOrder order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
	@Transactional
	public ReceivedRpOrder getOrder(Long orderID) {
		return (ReceivedRpOrder) sessionFactory.getCurrentSession().get(ReceivedRpOrder.class, orderID);
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReceivedRpOrder> getOrderByUserId(Long userId) {
		List<ReceivedRpOrder> orders = new ArrayList<ReceivedRpOrder>();
		orders = sessionFactory.getCurrentSession()
			.createQuery("from ReceivedRpOrder where createByUser=?")
			.setParameter(0, userId)
			.list();
		if (orders.size() > 0) {
			return orders;
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReceivedRpOrder> getOrderByLot(Long rpOrderID, Long lotID, String productName) {
		List<ReceivedRpOrder> orders = new ArrayList<ReceivedRpOrder>();
		orders = sessionFactory.getCurrentSession()
			.createQuery("from ReceivedRpOrder where rpOrderID=? and lotID=? and receivedRpProductName=?")
			.setParameter(0, rpOrderID)
			.setParameter(1, lotID)
			.setParameter(2, productName)
			.list();
		if (orders.size() > 0) {
			return orders;
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReceivedRpOrder> getOrderByRpOrderID(Long rpOrderID, String productName) {
		List<ReceivedRpOrder> orders = new ArrayList<ReceivedRpOrder>();
		orders = sessionFactory.getCurrentSession()
			.createQuery("from ReceivedRpOrder where rpOrderID=? and receivedRpProductName=?")
			.setParameter(0, rpOrderID)
			.setParameter(1, productName)
			.list();
		if (orders.size() > 0) {
			return orders;
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<ReceivedRpOrder> listOrders() {
		List<ReceivedRpOrder> listOrders = sessionFactory.getCurrentSession()
				.createQuery("from ReceivedRpOrder").list();
		return listOrders;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<ReceivedRpOrder> listOrders(Long rpOrderID, String productName) {
		List<ReceivedRpOrder> listOrders = sessionFactory.getCurrentSession()
				.createQuery("from ReceivedRpOrder where rpOrderID=? and receivedRpProductName=?")
				.setParameter(0, rpOrderID)
				.setParameter(1, productName)
				.list();
		return listOrders;
	}
	@Transactional
	public Collection<ReceivedRpOrder> listOrders(String orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Long orderID) {
		ReceivedRpOrder orderToDelete = new ReceivedRpOrder();
		orderToDelete.setReceivedRpOrderID(orderID);
		sessionFactory.getCurrentSession().delete(orderToDelete);
	}

}
