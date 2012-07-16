package org.pfc.business.event;

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

@Entity
@Table(name = "event")
public class Event {
	
	private Long eventId;
	private String ipSource;
	private Calendar date;
	private String trapType;
	private String variables;
	private String pdu;
	private Device device;

    public Event() {}
    
	public Event(String ipSource, Calendar date, String trapType,
			String variables, String pdu, Device device) {
		this.ipSource = ipSource;
		this.date = date;
		this.trapType = trapType;
		this.variables = variables;
		this.pdu = pdu;
		this.device = device;
	}

	@Id
	@GeneratedValue
	@Column(name = "eventId")
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getIpSource() {
		return ipSource;
	}

	public void setIpSource(String ipSource) {
		this.ipSource = ipSource;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getTrapType() {
		return trapType;
	}

	public void setTrapType(String trapType) {
		this.trapType = trapType;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	public String getPdu() {
		return pdu;
	}

	public void setPdu(String pdu) {
		this.pdu = pdu;
	}

	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="devId")
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
		

}
