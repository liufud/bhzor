package ics.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ReplenishmentOrder {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long rpOrderID;	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="RPOrder_Product",joinColumns= {@JoinColumn(name="RPOrder_ID")},
				inverseJoinColumns= {@JoinColumn(name="Product_ID")})
	private List<Product> rpProducts = new ArrayList<Product>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User createByUser;
	@UpdateTimestamp
	private Date created_At;
	private String orderStatus;
	public Long getRpOrderID() {
		return rpOrderID;
	}
	public void setRpOrderID(Long rpOrderID) {
		this.rpOrderID = rpOrderID;
	}
	public List<Product> getProducts() {
		return rpProducts;
	}
	public void setProducts(List<Product> products) {
		this.rpProducts = products;
	}
	public User getCreateByUser() {
		return createByUser;
	}
	public void setCreateByUser(User createByUser) {
		this.createByUser = createByUser;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "ReplenishmentOrder [rpOrderID=" + rpOrderID + ", products=" + rpProducts + ", createByUser="
				+ createByUser + ", created_At=" + created_At + ", orderStatus=" + orderStatus + "]";
	}
	
	
	
	
	
}
