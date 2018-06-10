package ics.services;

import ics.model.Cart;

public interface CartService {
	public void addOrUpdateCart(Cart cart);
	public Cart get(Long cartId);
	public void delete(Long cartId);
}