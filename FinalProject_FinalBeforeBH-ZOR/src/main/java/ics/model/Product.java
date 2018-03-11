package ics.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="PRODUCT")
public class Product {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	//@Column(name="PRODUCT_ID")
	private Long productID;
	@NotEmpty(message = "Product name not specified")
	private String productName;
	@NotNull(message="please enter cost from manufacturer")
	private Integer cost;
	@NotNull(message="please enter price for sale")
	private Integer price;
//	@NotNull(message="You must provide a quantity")
	private Integer quantity;
	@ManyToOne
	private Cart cart;
//	@ManyToOne
//	private Order order;
	
	@ManyToMany(mappedBy="products",fetch=FetchType.EAGER)
	private List<Order> orders = new ArrayList<Order>();
	@ManyToMany(mappedBy="rpProducts",fetch=FetchType.EAGER)
	private List<ReplenishmentOrder> rpOrders = new ArrayList<ReplenishmentOrder>();

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
		
}
