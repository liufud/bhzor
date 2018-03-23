package ics.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.ReplenishmentOrder;

public class ReplenishmentOrderHibernateDAOImpl implements ReplenishmentOrderDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public ReplenishmentOrderHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void createOrder(ReplenishmentOrder order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
	@Transactional
	public ReplenishmentOrder getOrder(Long orderID) {
		return (ReplenishmentOrder) sessionFactory.getCurrentSession().get(ReplenishmentOrder.class, orderID);
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ReplenishmentOrder> getOrderByUserId(Long userId) {
		List<ReplenishmentOrder> orders = new ArrayList<ReplenishmentOrder>();

		orders = sessionFactory.getCurrentSession()
			.createQuery("from ReplenishmentOrder where createByUser=?")
			.setParameter(0, userId)
			.list();
		//System.out.println("check if order size is greater than 0 ============");
		if (orders.size() > 0) {
			//System.out.println("order size check complete =======");
			return orders;
		} else {
			return null;
		}
	}
	@Transactional
	public Collection<ReplenishmentOrder> listOrders() {
		@SuppressWarnings("unchecked")
		List<ReplenishmentOrder> listOrders = sessionFactory.getCurrentSession()
				.createQuery("from ReplenishmentOrder").list();
		return listOrders;
	}
	@Transactional
	public Collection<ReplenishmentOrder> listOrders(String orderStatus) {
		@SuppressWarnings("unchecked")
		List<ReplenishmentOrder> listOrders = sessionFactory.getCurrentSession()
				.createQuery("from ReplenishmentOrder where orderStatus=?")
				.setParameter(0, orderStatus)
				.list();
		return listOrders;
	}
	@Transactional
	public void delete(Long orderID) {
		ReplenishmentOrder orderToDelete = new ReplenishmentOrder();
		orderToDelete.setRpOrderID(orderID);
		sessionFactory.getCurrentSession().delete(orderToDelete);
	}

}
