package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DevicesFindResponse", namespace="http://webservice.business.pfc.org/" )
public class DevicesFindResponse {

	@XmlElementWrapper(name="deviceDTOs")
	private List<DeviceDTO> deviceDTOs;

	public DevicesFindResponse() {

	}

	public DevicesFindResponse(List<DeviceDTO> deviceDTOs) {
		this.deviceDTOs = deviceDTOs;
	}

	public List<DeviceDTO> getDeviceDTOs() {
		return deviceDTOs;
	}

	public void setDeviceDTOs(List<DeviceDTO> deviceDTOs) {
		this.deviceDTOs = deviceDTOs;
	}

}
