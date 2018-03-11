package ics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.dao.CartDAO;
import ics.model.Cart;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartDAO cartDAO;
	
	public void addOrUpdateCart(Cart cart) {
		cartDAO.addOrUpdateCart(cart);
	}

	public Cart get(Long cartId) {
		return cartDAO.get(cartId);
	}

	public void delete(Long cartId) {
		cartDAO.delete(cartId);
	}

}