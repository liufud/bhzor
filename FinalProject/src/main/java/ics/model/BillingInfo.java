package ics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class BillingInfo {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long billID;
	@NotEmpty(message="Please provide your email")
	@Email
	private String email;
	@NotEmpty(message="Enter your first name")
	private String firstName;
	@NotEmpty(message="Enter your last name")
	private String lastName;
	@NotEmpty(message="Please provide your address")
	private String address;
	@NotEmpty(message="Please enter city")
	private String city;
	@NotEmpty(message="Please enter state")
	private String state;
	@NotEmpty(message="Please enter postal code")
	private String postalCode;
	private Long phone;
	@OneToOne
	private Order order;
	
	
	public Long getBillID() {
		return billID;
	}
	public void setBillID(Long billID) {
		this.billID = billID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
