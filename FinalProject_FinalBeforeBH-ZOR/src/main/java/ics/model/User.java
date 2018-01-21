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
	@NotEmpty(message="Please provide your email")
	@Email
	private String email;
	private Long phoneNumber;
	@NotEmpty(message="Please provide your shipping address")
	private String shippingAddress;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user",cascade = {CascadeType.ALL})
	private List<Role> roles = new ArrayList<Role>();
	@UpdateTimestamp
	private Date created_at;

	public User() {
		
	}

	public User(String username, Long userId, String password, String email, Long phoneNumber, String shippingAddress,
			boolean enabled, List<Role> roles, Date created_at) {
		super();
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.shippingAddress = shippingAddress;
		this.enabled = enabled;
		this.roles = roles;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", userId=" + userId + ", password=" + password + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", shippingAddress=" + shippingAddress + ", enabled=" + enabled
				+ ", roles=" + roles + ", created_at=" + created_at + "]";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

}