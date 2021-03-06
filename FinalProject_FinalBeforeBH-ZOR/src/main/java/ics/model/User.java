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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="users")
public class User {

	
	@Column(name = "username", unique = true,
	nullable = false, length = 45)
	@NotEmpty(message="username cannot be empty")
	private String username;
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long userId;
	@Column(name = "password",
	nullable = false, length = 60)
	@Size(min=6,max=15,message="Your password must be betwen 6 and 15 characters")
	private String password;
	@Size(min=6,max=15,message="Please confirm your password")
	private String passwordConfirm;
	@NotEmpty(message="Please provide your email")
	@Email
	private String email;
	@NotEmpty(message="Enter your first name")
	private String firstName;
	@NotEmpty(message="Enter your last name")
	private String lastName;
	private Long phoneNumber;
	@NotEmpty(message="Please provide your address")
	private String address;
	@NotEmpty(message="Please enter city")
	private String city;
	@NotEmpty(message="Please enter state")
	private String state;
	@NotEmpty(message="Please enter zip code")
	private String zip;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user",cascade = {CascadeType.ALL})
	private List<Role> roles = new ArrayList<Role>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<Order> orders = new ArrayList<Order>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<Order> rpOrders = new ArrayList<Order>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<ReceivedRpOrder> receivedRpOrders = new ArrayList<ReceivedRpOrder>();
	@OneToOne
	private Cart cart;
	@UpdateTimestamp
	private Date created_at;
	private String roleName;
	
	public User() {
		
	}
	
	public User(String username, Long userId, String password, String passwordConfirm, String email, String firstName,
			String lastName, Long phoneNumber, String address, String city, String state, String zip, boolean enabled,
			List<Role> roles, List<Order> orders, List<Order> rpOrders, Cart cart, Date created_at, String roleName) {
		super();
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.enabled = enabled;
		this.roles = roles;
		this.orders = orders;
		this.rpOrders = rpOrders;
		this.cart = cart;
		this.created_at = created_at;
		this.roleName = roleName;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
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

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getRpOrders() {
		return rpOrders;
	}

	public void setRpOrders(List<Order> rpOrders) {
		this.rpOrders = rpOrders;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public List<ReceivedRpOrder> getReceivedRpOrders() {
		return receivedRpOrders;
	}

	public void setReceivedRpOrders(List<ReceivedRpOrder> receivedRpOrders) {
		this.receivedRpOrders = receivedRpOrders;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", userId=" + userId + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", enabled=" + enabled + ", roles=" + roles + ", orders=" + orders + ", rpOrders="
				+ rpOrders + ", cart=" + cart + ", created_at=" + created_at + ", roleName=" + roleName + "]";
	}

	
}
