package ics.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.Order;
import ics.model.ReceivedRpOrder;
import ics.model.ShippedOrder;

@Repository
public class OrderHibernateDAOImpl implements OrderDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public OrderHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void createOrder(Order order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
	@Transactional
	public void createShippedOrder(ShippedOrder order) {
		sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
	@Transactional
	public Order getOrder(Long orderID) {
		
		return (Order) sessionFactory.getCurrentSession().get(Order.class, orderID);
	}
	@Transactional
	public Collection<Order> listOrders() {
		@SuppressWarnings("unchecked")
		List<Order> listOrders = sessionFactory.getCurrentSession()
				.createQuery("from Order").list();
		return listOrders;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Order> listOrders(String statusType, String status) {
		List<Order> listOrders = new ArrayList<Order>();
		if(statusType == "paymentStatus") {
			listOrders = sessionFactory.getCurrentSession()
					.createQuery("from Order where paymentStatus=?")
					.setParameter(0, status)
					.list();
		}else if(statusType == "shipmentStatus") {
			listOrders = sessionFactory.getCurrentSession()
					.createQuery("from Order where shipmentStatus=?")
					.setParameter(0, status)
					.list();
		}		
		return listOrders;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ShippedOrder> getShippedOrderByLot(Long orderID, Long lotID, String productName) {
		List<ShippedOrder> orders = new ArrayList<ShippedOrder>();
		orders = sessionFactory.getCurrentSession()
			.createQuery("from ShippedOrder where orderID=? and lotID=? and shippedProductName=?")
			.setParameter(0, orderID)
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
	public List<ShippedOrder> getShippedOrderByID(Long orderID, String productName) {
		List<ShippedOrder> orders = new ArrayList<ShippedOrder>();
		orders = sessionFactory.getCurrentSession()
			.createQuery("from ShippedOrder where orderID=? and shippedProductName=?")
			.setParameter(0, orderID)
			.setParameter(1, productName)
			.list();
		if (orders.size() > 0) {
			return orders;
		} else {
			return null;
		}
	}
	@Transactional
	public void delete(Long orderID) {
		Order orderToDelete = new Order();
		orderToDelete.setOrderID(orderID);
		sessionFactory.getCurrentSession().delete(orderToDelete);		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Order> getOrderByUserId(Long userId) {
		List<Order> orders = new ArrayList<Order>();

		orders = sessionFactory.getCurrentSession()
			.createQuery("from Order where createByUser=?")
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
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Order> getOrderByUsername(String username) {
		List<Order> orders = new ArrayList<Order>();

		orders = sessionFactory.getCurrentSession()
			.createQuery("from Order where createByUser=?")
			.setParameter(0, username)
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
	public Order getLatestOrder() {
		Order latestOrder = (Order) sessionFactory.getCurrentSession()
											.createQuery("from Order order by orderID DESC")
											.setMaxResults(1)
											.uniqueResult();
		return latestOrder;
	}

}
