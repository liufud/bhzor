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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ReceivedRpOrder {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long receivedRpOrderID;	
	@NotNull(message="El # de Lote no puede estaer vacio!")
	@Min(value=0)
	private Long lotID;
	@NotNull(message="El # de Estante no puede estaer vacio!")
	@Min(value=0)
	@Max(value=1000)
	private Long shelfID;
	@CreationTimestamp
	@Type(type="date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateReceived;
	@NotNull(message="¡La cantidad recibida no puede estar vacía!")
	@Min(value=0)
	@Max(value=10000000)
	private Integer quantityReceived;
	@NotNull(message="¡La cantidad rechazada no puede estar vacía!Ponga 0 si no hay producto dañado")
	@Min(value=0)
	@Max(value=100000)
	private Integer quantityRejected;
	@NotNull(message="La fecha de expiracion no puede estar vacía!")
	@Pattern(message="Formato de fecha inválida. La entrada tiene que estar en formato dd/mm/yy", regexp="^(\\d{1,2})\\/(\\d{1,2})\\/(\\d{4})$")
	private String expDate;
	@ManyToOne(fetch=FetchType.EAGER)
	private User createByUser;
	@NotEmpty
	private String receivedRpProductName;
	private Double totalCost;
	private Long rpOrderID;
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="ReceivedRPOrder_Product",joinColumns= {@JoinColumn(name="ReceivedRPOrder_ID")},
				inverseJoinColumns= {@JoinColumn(name="productID")})
	private List<OrderedProd> receivedProds = new ArrayList<OrderedProd>();
	
	public Long getReceivedRpOrderID() {
		return receivedRpOrderID;
	}
	public void setReceivedRpOrderID(Long receivedRpOrderID) {
		this.receivedRpOrderID = receivedRpOrderID;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	public Integer getQuantityReceived() {
		return quantityReceived;
	}
	public void setQuantityReceived(Integer quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public Integer getQuantityRejected() {
		return quantityRejected;
	}
	public void setQuantityRejected(Integer quantityRejected) {
		this.quantityRejected = quantityRejected;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public User getCreateByUser() {
		return createByUser;
	}
	public void setCreateByUser(User createByUser) {
		this.createByUser = createByUser;
	}
	public String getReceivedRpProductName() {
		return receivedRpProductName;
	}
	public void setReceivedRpProductName(String receivedRpProductName) {
		this.receivedRpProductName = receivedRpProductName;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalPrice) {
		this.totalCost = totalPrice;
	}
	public Long getRpOrderID() {
		return rpOrderID;
	}
	public void setRpOrderID(Long rpOrderID) {
		this.rpOrderID = rpOrderID;
	}
	public List<OrderedProd> getReceivedProds() {
		return receivedProds;
	}
	public void setReceivedProds(List<OrderedProd> receivedProds) {
		this.receivedProds = receivedProds;
	}
	public Long getShelfID() {
		return shelfID;
	}
	public void setShelfID(Long shelfID) {
		this.shelfID = shelfID;
	}
	public Long getLotID() {
		return lotID;
	}
	public void setLotID(Long lotID) {
		this.lotID = lotID;
	}
}
