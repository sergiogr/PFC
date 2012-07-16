package org.pfc.business.webservice;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DataDTO", namespace="http://webservice.business.pfc.org/" )
public class DataDTO {
	
	private Long dataId;
	private String value;
	private Calendar date;
	private Long deviceId;
	private Long mibObjectId;
	private String mibObjectName;
	
	public DataDTO() {
		
	}
	
	public DataDTO(String value, Calendar date, Long deviceId,
			Long mibObjectId, String mibObjectName) {
		this.value = value;
		this.date = date;
		this.deviceId = deviceId;
		this.mibObjectId = mibObjectId;
		this.mibObjectName = mibObjectName;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
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

	
}
