package ics.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.Order;

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

}
