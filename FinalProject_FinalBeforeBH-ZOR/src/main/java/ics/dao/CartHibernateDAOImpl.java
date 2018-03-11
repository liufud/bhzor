package ics.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.Cart;

@Repository
public class CartHibernateDAOImpl implements CartDAO {
	@Autowired
	private SessionFactory sessionFactory;	
	
	public CartHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	public void addOrUpdateCart(Cart cart) {
		sessionFactory.getCurrentSession().saveOrUpdate(cart);
	}
	@Transactional
	public Cart get(Long cartId) {
		return (Cart) sessionFactory.getCurrentSession().get(Cart.class, cartId);
	}
	@Transactional
	public void delete(Long cartId) {
		Cart cartToDelete = new Cart();
		cartToDelete.setCartId(cartId);
		sessionFactory.getCurrentSession().delete(cartId);
	}

}
