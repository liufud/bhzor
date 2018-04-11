package ics.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="PRODUCT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Polymorphism(type = PolymorphismType.EXPLICIT)
//@DiscriminatorValue("product")
public class Product {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
//	@Column(name="PRODUCT_ID")
	private Long productID;
	@NotEmpty(message = "Nombre de Producto no Especificado")
	private String productName;
	@NotNull(message="Porfavor ponga el costo de Fabrica")
	private Integer cost;
//	@NotNull(message="Porfavor ponga el precio de Distribuidor")
	private Integer distributorPrice;
//	@NotNull(message="Porfavor ponga el precio de Vendedor")
	private Integer vendorPrice;
//	@NotNull(message="Porfavor ponga el precio de Cliente")
	private Integer clientPrice;
//	@NotNull(message="You must provide a quantity")
	private Integer quantityOnHand;
	private ArrayList<Long> shelfLocations = new ArrayList<Long>();
//	@ManyToOne
//	private Order order;

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
	
	public Integer getQuantity() {
		return quantityOnHand;
	}

	public void setQuantity(Integer quantity) {
		this.quantityOnHand = quantity;
	}

	public Integer getDistributorPrice() {
		return distributorPrice;
	}

	public void setDistributorPrice(Integer distributorPrice) {
		this.distributorPrice = distributorPrice;
	}

	public Integer getVendorPrice() {
		return vendorPrice;
	}

	public void setVendorPrice(Integer vendorPrice) {
		this.vendorPrice = vendorPrice;
	}

	public Integer getClientPrice() {
		return clientPrice;
	}

	public void setClientPrice(Integer clientPrice) {
		this.clientPrice = clientPrice;
	}

	public Integer getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(Integer quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public ArrayList<Long> getShelfLocations() {
		return shelfLocations;
	}

	public void setShelfLocations(ArrayList<Long> shelfLocations) {
		this.shelfLocations = shelfLocations;
	}		
}
