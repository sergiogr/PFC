package org.pfc.business.deviceservice;

public class DeviceInfo {
	
	private Long deviceId;
	private String deviceName;
	private String description;
	private String ipAddress;
	private double lat;
	private double lng;
	
	public DeviceInfo(String deviceName, String description, String ipAddress,
			double lat, double lng) {
		super();
		this.deviceName = deviceName;
		this.description = description;
		this.ipAddress = ipAddress;
		this.lat = lat;
		this.lng = lng;
	}
	
	public Long getDeviceId() {
		return deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public String getDescription() {
		return description;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public double getLat() {
		return lat;
	}
	public double getLng() {
		return lng;
	}
	
	
	

}
