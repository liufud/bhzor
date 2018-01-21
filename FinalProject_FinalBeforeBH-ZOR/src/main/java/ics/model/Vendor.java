package ics.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="VENDOR")
public class Vendor {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="VENDOR_ID")
	private Long vendorID;
	@NotEmpty(message="Please enter vendor name")
	private String vendorName;
	@NotEmpty(message="Please enter vendor address")
	private String vendorAddress;
	@OneToMany(mappedBy="vendor", cascade = {CascadeType.ALL},fetch=FetchType.EAGER)
	private List<Product> products = new ArrayList<Product>();
	@UpdateTimestamp
    private Date created_at;
	
	
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Long getVendorID() {
		return vendorID;
	}
	public void setVendorID(Long vendorID) {
		this.vendorID = vendorID;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		return "Vendor [vendorID=" + vendorID + ", vendorName=" + vendorName + ", vendorAddress=" + vendorAddress
				+ ", created_at=" + created_at + "]";
	}

		
	
}
