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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ReplenishmentOrder {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long rpOrderID;	
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="RpOrder_Product",joinColumns= {@JoinColumn(name="RpOrder_ID")},
				inverseJoinColumns= {@JoinColumn(name="orderedProductID")})
	private List<OrderedProd> rpProducts = new ArrayList<OrderedProd>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User createByUser;
	@CreationTimestamp
	@Type(type="date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date created_At;
	private String orderStatus;
	private Double totalPrice;
	
	public Long getRpOrderID() {
		return rpOrderID;
	}
	public void setRpOrderID(Long rpOrderID) {
		this.rpOrderID = rpOrderID;
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
	public List<OrderedProd> getRpProducts() {
		return rpProducts;
	}
	public void setRpProducts(List<OrderedProd> rpProducts) {
		this.rpProducts = rpProducts;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "ReplenishmentOrder [rpOrderID=" + rpOrderID + ", rpProducts=" + rpProducts + ", createByUser="
				+ createByUser + ", created_At=" + created_At + ", orderStatus=" + orderStatus + ", totalPrice="
				+ totalPrice + "]";
	}	
}
