package ics.dao;

import ics.model.Cart;

public interface CartDAO {
	public void addOrUpdateCart(Cart cart);
	public Cart get(Long cartId);
	public void delete(Long cartId);
}
