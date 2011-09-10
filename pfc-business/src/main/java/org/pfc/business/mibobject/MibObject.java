package org.pfc.business.mibobject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.pfc.business.product.Product;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Entity
@Table(name = "MibObject")
public class MibObject {
	
	private Long mibObjectId;
	private String mibObjectName;
	private String description;
	private String oid;
	private String mib;
	private Set<Product> products = new HashSet<Product>();
	private long version;
	
	public MibObject() {}
	
	public MibObject(String mibObjectName, String description, String oid,
			String mib) {

		this.mibObjectName = mibObjectName;
		this.description = description;
		this.oid = oid;
		this.mib = mib;
	}

	@Id
	@GeneratedValue
	@Column(name = "mibObjId")
	public Long getMibObjectId() {
		return mibObjectId;
	}

	public void setMibObjectId(Long mibObjectId) {
		this.mibObjectId = mibObjectId;
	}

	@Column(name = "mibObjName")
	public String getMibObjectName() {
		return mibObjectName;
	}

	public void setMibObjectName(String mibObjectName) {
		this.mibObjectName = mibObjectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getMib() {
		return mib;
	}

	public void setMib(String mib) {
		this.mib = mib;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@ManyToMany(targetEntity=org.pfc.business.product.Product.class,
			fetch=FetchType.LAZY,
			mappedBy="mibObjects")
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		getProducts().add(product);
		product.getMibObjects().add(this);
	}
	
	public void removeProduct(Product product) {
		getProducts().remove(product);
		product.getMibObjects().remove(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((mib == null) ? 0 : mib.hashCode());
		result = prime * result
				+ ((mibObjectId == null) ? 0 : mibObjectId.hashCode());
		result = prime * result
				+ ((mibObjectName == null) ? 0 : mibObjectName.hashCode());
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MibObject))
			return false;
		MibObject other = (MibObject) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (mib == null) {
			if (other.mib != null)
				return false;
		} else if (!mib.equals(other.mib))
			return false;
		if (mibObjectId == null) {
			if (other.mibObjectId != null)
				return false;
		} else if (!mibObjectId.equals(other.mibObjectId))
			return false;
		if (mibObjectName == null) {
			if (other.mibObjectName != null)
				return false;
		} else if (!mibObjectName.equals(other.mibObjectName))
			return false;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		if (version != other.version)
			return false;
		return true;
	}	

}
