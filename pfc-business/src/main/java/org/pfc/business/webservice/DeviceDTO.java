package org.pfc.business.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DeviceDTO", namespace="http://webservice.business.pfc.org/" )
public class DeviceDTO {

	 private Long deviceId;
     private String deviceName;
     private String description;
     private String ipAddress;
     private String publicCommunity;
     private String snmpPort;
     private double lat;
     private double lng;
     private Long productId;
     private Long projectId;
     
     public DeviceDTO() {
    	 
     }

	public DeviceDTO(Long deviceId, String deviceName, String description, String ipAddress,
			String publicCommunity, String snmpPort, double lat, double lng) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.description = description;
		this.ipAddress = ipAddress;
		this.publicCommunity = publicCommunity;
		this.snmpPort = snmpPort;
		this.lat = lat;
		this.lng = lng;

	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPublicCommunity() {
		return publicCommunity;
	}

	public void setPublicCommunity(String publicCommunity) {
		this.publicCommunity = publicCommunity;
	}

	public String getSnmpPort() {
		return snmpPort;
	}

	public void setSnmpPort(String snmpPort) {
		this.snmpPort = snmpPort;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}  
     
	
}
