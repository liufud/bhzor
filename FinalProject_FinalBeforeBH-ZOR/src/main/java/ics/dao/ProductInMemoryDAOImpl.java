package ics.dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Repository;

import ics.model.Product;

@Repository
public class ProductInMemoryDAOImpl implements ProductDAO {
	
	private Map<Long, Product> products = new ConcurrentHashMap<Long, Product>();
	
	
	public void addProduct(Product product) {
		products.put(product.getProductID(), product);
	}

	public Collection<Product> listProducts() {
		return products.values();
	}

	public void addOrUpdateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	public Product get(Long productID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Long productID) {
		// TODO Auto-generated method stub
		
	}

	public void buyProduct(Integer quantity, Long productID) {
		// TODO Auto-generated method stub
		
	}

	public Product getProductByName(String productName) {
		// TODO Auto-generated method stub
		return null;
	}

}
