package ics.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ics.dao.ProductDAO;
import ics.model.Product;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	@Qualifier("productHibernateDAOImpl")
	private ProductDAO productDAO;
	
	@Transactional
	public void addOrUpdateProduct(Product product) {
		productDAO.addOrUpdateProduct(product);
	}
	@Transactional
	public Collection<Product> listProducts() {
		return productDAO.listProducts();
	}
	@Transactional
	public Product get(Long productID) {
		return productDAO.get(productID);
	}
	@Transactional
	public void delete(Long productID) {
		productDAO.delete(productID);
	}
	public void buyProduct(Integer quantity, Long productID) {
		productDAO.buyProduct(quantity, productID);	
	}
	@Transactional
	public Product getProductByName(String productName) {
		return productDAO.getProductByName(productName);
	}

}
