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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ShippedOrder {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long shipmentID;
	@NotNull(message="ID de lote no puede estaer vacio!")
	@Min(value=0)
	@Max(value=999999999*100000)
	private Long lotID;
	@NotNull(message="# de estante no puede estaer vacio!")
	@Min(value=0)
	@Max(value=1000)
	private Long shelfID;
	@CreationTimestamp
	@Type(type="date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateShipped;
	@NotNull(message="Cantidad Enviada no puede estar vacio!")
	@Min(value=0)
	@Max(value=100000)
	private Integer qtyShipped;
	@ManyToOne(fetch=FetchType.EAGER)
	private User shippedByUser;
	@NotEmpty
	private String shippedProductName;
//	@NotEmpty(message="Numero de Rastreo no puede estar vacio!")
	private String trackingNumber;
	private Long orderID;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="ShippedOrder_Product",joinColumns= {@JoinColumn(name="ShippedOrder_ID")},
				inverseJoinColumns= {@JoinColumn(name="ProductID")})
	private List<OrderedProd> shippedProds = new ArrayList<OrderedProd>();
	public Long getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(Long shipmentID) {
		this.shipmentID = shipmentID;
	}
	public Long getShelfID() {
		return shelfID;
	}
	public void setShelfID(Long lotID) {
		this.shelfID = lotID;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public Integer getQtyShipped() {
		return qtyShipped;
	}
	public void setQtyShipped(Integer qtyShipped) {
		this.qtyShipped = qtyShipped;
	}
	public User getShippedByUser() {
		return shippedByUser;
	}
	public void setShippedByUser(User shippedByUser) {
		this.shippedByUser = shippedByUser;
	}
	public String getShippedProductName() {
		return shippedProductName;
	}
	public void setShippedProductName(String shippedProductName) {
		this.shippedProductName = shippedProductName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public Long getOrderID() {
		return orderID;
	}
	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}
	public List<OrderedProd> getShippedProds() {
		return shippedProds;
	}
	public void setShippedProds(List<OrderedProd> shippedProds) {
		this.shippedProds = shippedProds;
	}
	public Long getLotID() {
		return lotID;
	}
	public void setLotID(Long lotID) {
		this.lotID = lotID;
	}
	

}
