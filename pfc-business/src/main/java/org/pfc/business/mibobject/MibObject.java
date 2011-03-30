package org.pfc.business.mibobject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private List<Product> products;
	private long version;
	
	public MibObject() {}
	
	public MibObject(String mibObjectName, String description, String oid,
			String mib) {
		super();
		this.mibObjectName = mibObjectName;
		this.description = description;
		this.oid = oid;
		this.mib = mib;
		this.products = new ArrayList<Product>();
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

	@ManyToMany(
			targetEntity=org.pfc.business.product.Product.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy="mibObjects")
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
