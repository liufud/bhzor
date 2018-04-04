/**
 * 
 */
package ics.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;


/**
 * @author fudi
 *
 */

@Entity
//@DiscriminatorValue("orderedProd")
@Table(name="orderedproduct")
//@Polymorphism(type = PolymorphismType.EXPLICIT)
public class OrderedProd extends Product {
	
	private Integer orderedProductQty;
	private Integer unreceivedProductqty;
	private Integer unshippedProductqty;
	@ManyToOne
	private Cart cart;
	@ManyToMany(mappedBy="products",fetch=FetchType.EAGER)
	private List<Order> orders = new ArrayList<Order>();
	@ManyToMany(mappedBy="rpProducts",fetch=FetchType.EAGER)
	private List<ReplenishmentOrder> rpOrders = new ArrayList<ReplenishmentOrder>();
	
	public OrderedProd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderedProd(Integer orderedProductQty) {
		super();
		this.orderedProductQty = orderedProductQty;
	}
	
	public Integer getOrderedProductQty() {
		return orderedProductQty;
	}

	public void setOrderedProductQty(Integer orderedProductQty) {
		this.orderedProductQty = orderedProductQty;
	}
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Integer getUnreceivedProductqty() {
		return unreceivedProductqty;
	}

	public void setUnreceivedProductqty(Integer unreceivedProductqty) {
		this.unreceivedProductqty = unreceivedProductqty;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<ReplenishmentOrder> getRpOrders() {
		return rpOrders;
	}

	public void setRpOrders(List<ReplenishmentOrder> rpOrders) {
		this.rpOrders = rpOrders;
	}

	public Integer getUnshippedProductqty() {
		return unshippedProductqty;
	}

	public void setUnshippedProductqty(Integer unshippedProductqty) {
		this.unshippedProductqty = unshippedProductqty;
	}
}
