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
	@NotEmpty(message = "Product name not specified")
	private String productName;
	@NotNull(message="please enter cost from manufacturer")
	private Integer cost;
	@NotNull(message="please enter price for sale")
	private Integer price;
//	@NotNull(message="You must provide a quantity")
	private Integer quantityOnHand;
	
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantityOnHand;
	}

	public void setQuantity(Integer quantity) {
		this.quantityOnHand = quantity;
	}		
}
