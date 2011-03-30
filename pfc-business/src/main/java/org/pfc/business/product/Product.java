package org.pfc.business.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.pfc.business.mibobject.MibObject;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Entity
@Table(name = "Product")
public class Product {
	
	private Long productId;
	private String productName;
	private String description;
	private String manufacturer;
	private List<MibObject> mibObjects;
	private long version;

	public Product() {}
	
	public Product(String productName, String description, String manufacturer) {
		super();
		this.productName = productName;
		this.description = description;
		this.manufacturer = manufacturer;
		this.mibObjects = new ArrayList<MibObject>();
	}

	@Id
	@GeneratedValue
	@Column(name = "prodId")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Column(name = "prodName")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Version
	public long getVersion() {
		return version;
	}
	
	public void setVersion(long version) {
		this.version = version;
	}

	@ManyToMany(
			targetEntity=org.pfc.business.mibobject.MibObject.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
			name="Product_MibObject",
			joinColumns=@JoinColumn(name="prodId"),
			inverseJoinColumns=@JoinColumn(name="mibObjId")
	)
	public List<MibObject> getMibObjects() {
		return mibObjects;
	}
	
	public void setMibObjects(List<MibObject> mibObjects) {
		this.mibObjects = mibObjects;
	}

	
	

}
