package ics.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@NotNull(message="please enter inventory level")
	private Integer inventoryLevel;
	@NotNull(message="please enter cost from manufacturer")
	private Integer costFromManufacturer;
	@NotNull(message="please enter cost for sale")
	private Integer costForSale;
	@ManyToOne
	@JoinColumn(name="VENDOR_ID")
	private Vendor vendor;
	@OneToMany(mappedBy="product", cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	private List<Order> orders = new ArrayList<Order>();

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName=" + productName + ", inventoryLevel=" + inventoryLevel
				+ ", costFromManufacturer=" + costFromManufacturer + ", costForSale=" + costForSale + ", vendor="
				+ vendor;
	}


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


	public Integer getInventoryLevel() {
		return inventoryLevel;
	}


	public void setInventoryLevel(Integer inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}


	public Integer getCostFromManufacturer() {
		return costFromManufacturer;
	}


	public void setCostFromManufacturer(Integer costFromManufacturer) {
		this.costFromManufacturer = costFromManufacturer;
	}


	public Integer getCostForSale() {
		return costForSale;
	}


	public void setCostForSale(Integer costForSale) {
		this.costForSale = costForSale;
	}


	public Vendor getVendor() {
		return vendor;
	}


	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}


	public List<Order> getOrders() {
		return orders;
	}


	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	

	
		
}
