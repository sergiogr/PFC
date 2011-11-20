package org.pfc.business.data;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pfc.business.device.Device;
import org.pfc.business.mibobject.MibObject;


@Entity
@Table(name = "data")
public class Data {
	
	private Long dataId;
	private String value;
	private Calendar date;
	private Device device;
	private MibObject mibObject;
	
	public Data() {}

	public Data(String value, Calendar date, Device device,
			MibObject mibObject) {
		this.value = value;
		this.date = date;
		this.device = device;
		this.mibObject = mibObject;
	}

	@Id
	@GeneratedValue
	@Column(name = "dataId")
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

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="devId")
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="mibObjId")
	public MibObject getMibObject() {
		return mibObject;
	}

	public void setMibObject(MibObject mibObject) {
		this.mibObject = mibObject;
	}
	
}
