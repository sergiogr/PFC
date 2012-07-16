package org.pfc.business.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MibObjectDTO", namespace="http://webservice.business.pfc.org/" )
public class MibObjectDTO {

	private Long mibObjectId;
	private String mibObjectName;
	private String description;
	private String oid;
	private String mib;
	
	public MibObjectDTO() {}

	public MibObjectDTO(Long mibObjectId, String mibObjectName,
			String description, String oid, String mib) {
		this.mibObjectId = mibObjectId;
		this.mibObjectName = mibObjectName;
		this.description = description;
		this.oid = oid;
		this.mib = mib;
	}

	public Long getMibObjectId() {
		return mibObjectId;
	}

	public void setMibObjectId(Long mibObjectId) {
		this.mibObjectId = mibObjectId;
	}

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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MibObjectDTO))
			return false;
		MibObjectDTO other = (MibObjectDTO) obj;
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
		return true;
	}	

}
