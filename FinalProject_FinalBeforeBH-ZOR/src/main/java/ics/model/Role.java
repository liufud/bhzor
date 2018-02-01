package ics.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_roles",uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "userId" }))
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_role_id",
		unique = true, nullable = false)
	private Integer userRoleId;
	@Column(name = "role", nullable = false, length = 45)
	private String roleName;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public Role() {
		
	}

	public Role(Integer userRoleId, String roleName, User user) {
		super();
		this.userRoleId = userRoleId;
		this.roleName = roleName;
		this.user = user;
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
