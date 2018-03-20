package ics.services;

import java.util.Collection;

import ics.model.OrderedProd;
import ics.model.Product;


public interface ProductService {
	public Collection<Product> listProducts();
	public void addOrUpdateProduct(Product product);
	public void addOrUpdateOrderedProduct(OrderedProd product);
	public Product get(Long productID);
	public OrderedProd getOrderedProd(Long orderedProdID);
	public Product getProductByName(String productName);
	public void delete(Long productID);
	public void buyProduct(Integer quantity, Long productID);
}
