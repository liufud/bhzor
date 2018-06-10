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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {

	
	@Column(name = "username", unique = true,
	nullable = false, length = 45)
	@NotEmpty(message="Nombre de usuario no puede estar vacio")
	private String username;
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long userId;
	@Column(name = "password",
	nullable = false, length = 60)
//	@Size(min=6,max=15,message="Your password must be betwen 6 and 15 characters")
	@NotEmpty(message="Porfavor ponga su contraseña")
	private String password;
	@NotEmpty(message="Porfavor ponga su email")
//	@Size(min=6,max=15,message="Porfavor confirma tu contraseña")
	private String passwordConfirm;
	@NotEmpty(message="Porfavor ponga su email")
	@Email
	private String email;
	@NotEmpty(message="Porfavor escriba su Nombre")
	private String firstName;
	@NotEmpty(message="Porfavor escriba su Apellido")
	private String lastName;
	private Long phoneNumber;
	@NotEmpty(message="Porfavor escriba su Direccion")
	private String address;
	@NotEmpty(message="Porfavor escriba la Ciudad")
	private String city;
	@NotEmpty(message="Porfavor escriba el Estado")
	private String state;
	@NotEmpty(message="Porfavor escriba el Codigo Postal")
	private String zip;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user",cascade = {CascadeType.ALL})
	private List<Role> roles = new ArrayList<Role>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<Order> orderCreator = new ArrayList<Order>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createForUser",cascade = {CascadeType.ALL})
	private List<Order> orderReceiver = new ArrayList<Order>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<Order> rpOrders = new ArrayList<Order>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy="createByUser",cascade = {CascadeType.ALL})
	private List<ReceivedRpOrder> receivedRpOrders = new ArrayList<ReceivedRpOrder>();
	@OneToOne
	private Cart cart;
	@CreationTimestamp
	@Type(type="date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date created_at;
	private String roleName;
	@ManyToOne
	private User vendor;
	@OneToMany(mappedBy="vendor")
	private List<User> customer;
	
	public User() {
		
	}

	public User(String username, Long userId, String password, String passwordConfirm, String email, String firstName,
			String lastName, Long phoneNumber, String address, String city, String state, String zip, boolean enabled,
			List<Role> roles, List<Order> orderCreator, List<Order> orderReceiver, List<Order> rpOrders,
			List<ReceivedRpOrder> receivedRpOrders, Cart cart, Date created_at, String roleName, User vendor,
			List<User> customer) {
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
		this.orderCreator = orderCreator;
		this.orderReceiver = orderReceiver;
		this.rpOrders = rpOrders;
		this.receivedRpOrders = receivedRpOrders;
		this.cart = cart;
		this.created_at = created_at;
		this.roleName = roleName;
		this.vendor = vendor;
		this.customer = customer;
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

	public List<Order> getOrderCreator() {
		return orderCreator;
	}

	public void setOrderCreator(List<Order> orderCreator) {
		this.orderCreator = orderCreator;
	}

	public List<Order> getOrderReceiver() {
		return orderReceiver;
	}

	public void setOrderReceiver(List<Order> orderReceiver) {
		this.orderReceiver = orderReceiver;
	}

	public List<Order> getRpOrders() {
		return rpOrders;
	}

	public void setRpOrders(List<Order> rpOrders) {
		this.rpOrders = rpOrders;
	}

	public List<ReceivedRpOrder> getReceivedRpOrders() {
		return receivedRpOrders;
	}

	public void setReceivedRpOrders(List<ReceivedRpOrder> receivedRpOrders) {
		this.receivedRpOrders = receivedRpOrders;
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

	public User getVendor() {
		return vendor;
	}

	public void setVendor(User vendor) {
		this.vendor = vendor;
	}

	public List<User> getCustomer() {
		return customer;
	}

	public void setCustomer(List<User> customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", userId=" + userId + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", zip=" + zip + ", enabled=" + enabled + ", roles=" + roles + ", orderCreator=" + orderCreator
				+ ", orderReceiver=" + orderReceiver + ", rpOrders=" + rpOrders + ", receivedRpOrders="
				+ receivedRpOrders + ", cart=" + cart + ", created_at=" + created_at + ", roleName=" + roleName
				+ "]";
	}
		
}
