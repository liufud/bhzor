package ics.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table
public class Cart {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long cartId;
	@OneToOne(mappedBy = "cart", cascade=CascadeType.ALL, orphanRemoval=true)
	private User user;
	@OneToMany(mappedBy="cart", orphanRemoval=true)
	private List<OrderedProd> products = new ArrayList<OrderedProd>();
	private Double cartTotal;
	@UpdateTimestamp
	private Date created_At;
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderedProd> getProducts() {
		return products;
	}
	public void setProducts(List<OrderedProd> products) {
		this.products = products;
	}
	public Double getCartTotal() {
		return cartTotal;
	}
	public void setCartTotal(Double cartTotal) {
		this.cartTotal = cartTotal;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user=" + user + ", products=" + products + ", cartTotal=" + cartTotal
				+ ", created_At=" + created_At + "]";
	}
	
	
}
