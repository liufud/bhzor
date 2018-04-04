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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="ORDERTABLE")
public class Order {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long orderID;

/*	@OneToMany(mappedBy="order", cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
//	@JoinTable(name="Order_Product",joinColumns=@JoinColumn(name="Order_ID"),
//				inverseJoinColumns=@JoinColumn(name="Product_ID"))
	private List<Product> products = new ArrayList<Product>();*/
	
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="OrderTable_Product",joinColumns= {@JoinColumn(name="Order_ID")},
				inverseJoinColumns= {@JoinColumn(name="Product_ID")})
	private List<OrderedProd> products = new ArrayList<OrderedProd>();
	@NotNull
	private Double totalPrice;
	@ManyToOne(fetch=FetchType.EAGER)
	private User createByUser;
	@ManyToOne(fetch=FetchType.EAGER)
	private User createForUser;
	@NotEmpty
	private String paymentMethod;
	@NotEmpty
	private String orderStatus;
	@NotEmpty
	private String paymentStatus;
	@NotEmpty
	private String shipmentStatus;
	private String orderType;
	@OneToOne(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval=true)
	private BillingInfo billingInfo;
	@UpdateTimestamp
	private Date created_At;
	public Long getOrderID() {
		return orderID;
	}
	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}
	public List<OrderedProd> getProducts() {
		return products;
	}
	public void setProducts(List<OrderedProd> products) {
		this.products = products;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public User getCreateByUser() {
		return createByUser;
	}
	public void setCreateByUser(User createByUser) {
		this.createByUser = createByUser;
	}
	public User getCreateForUser() {
		return createForUser;
	}
	public void setCreateForUser(User createForUser) {
		this.createForUser = createForUser;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public BillingInfo getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}
	public Date getCreated_At() {
		return created_At;
	}
	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

}
