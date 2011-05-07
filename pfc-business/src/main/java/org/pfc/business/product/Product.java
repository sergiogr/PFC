package org.pfc.business.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
			cascade={CascadeType.PERSIST, CascadeType.MERGE},
			fetch=FetchType.EAGER
	)
	@JoinTable(
			name="Product_MibObject",
			joinColumns={@JoinColumn(name="prodId")},
			inverseJoinColumns={@JoinColumn(name="mibObjId")}
	)
	public List<MibObject> getMibObjects() {
		return mibObjects;
	}
	
	public void setMibObjects(List<MibObject> mibObjects) {
		this.mibObjects = mibObjects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
