package ics.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.OrderedProd;
import ics.model.Product;


@Repository
public class ProductHibernateDAOImpl implements ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Product product;
	
	public ProductHibernateDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public Collection<Product> listProducts() {
		@SuppressWarnings("unchecked")
		List<Product> listProduct = sessionFactory.getCurrentSession()
				.createQuery("from Product as p where p.class = Product").list();
		return listProduct;
	}
	
	@Transactional
	public void addOrUpdateProduct(Product product) {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
	}
	
	@Transactional
	public Product get(Long productID) {
		product =  (Product) sessionFactory.getCurrentSession().get(Product.class, productID);
		System.out.println("get Product is called.............. ");
		//System.out.println(product);
		return product;
//		String hql = "from Product where id=" + productID;
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		
//		@SuppressWarnings("unchecked")
//		List<Product> listProduct = (List<Product>) query.list();
//		if(listProduct != null && !listProduct.isEmpty()) {
//			return listProduct.get(0);
//		}
//		return null;
	}
	
	@Transactional
	public void delete(Long productID) {
		Product productToDelete = product;
		productToDelete.setProductID(productID);
		sessionFactory.getCurrentSession().delete(productToDelete);
	}
	
	@Transactional
	public void buyProduct(Integer quantity, Long productID) {
//		product = get(productID);
//		Integer newInventoryLevel = product.getInventoryLevel() - quantity;
//		product.setInventoryLevel(newInventoryLevel);
//		addOrUpdateProduct(product);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Product getProductByName(String productName) {
		List<Product> products = new ArrayList<Product>();

		products = sessionFactory.getCurrentSession()
			.createQuery("from Product as p where productName=? and p.class = Product")
			.setParameter(0, productName)
			.list();

		if (products.size() > 0) {
			return products.get(0);
		} else {
			return null;
		}
	}

	public OrderedProd getOrderedProd(Long orderedProdID) {
		product =  (OrderedProd) sessionFactory.getCurrentSession().get(OrderedProd.class, orderedProdID);
		System.out.println("get Product is called.............. ");
		//System.out.println(product);
		return (OrderedProd) product;
	}

	public void addOrUpdateOrderedProduct(OrderedProd product) {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
	}

}
