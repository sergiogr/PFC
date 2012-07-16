package org.pfc.business.webservice;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EventDTO", namespace="http://webservice.business.pfc.org/" )
public class EventDTO {
	
	private String ipSource;
	private Calendar date;
	private String trapType;
	private String variables;
	private String pdu;
	private Long deviceId;

	public EventDTO() {
	}
	
	public EventDTO(String ipSource, Calendar date,
			String trapType, String variables, String pdu, Long deviceId) {
		this.ipSource = ipSource;
		this.date = date;
		this.trapType = trapType;
		this.variables = variables;
		this.pdu = pdu;
		this.deviceId = deviceId;
	}
	
	public String getIpSource() {
		return ipSource;
	}
	public Calendar getDate() {
		return date;
	}
	public String getTrapType() {
		return trapType;
	}
	public String getVariables() {
		return variables;
	}
	public String getPdu() {
		return pdu;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	
}
