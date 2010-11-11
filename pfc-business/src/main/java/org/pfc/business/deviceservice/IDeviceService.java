package org.pfc.business.deviceservice;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

public interface IDeviceService {

	public Device createDevice(Device device);
	
	public void removeDevice(Long deviceId) throws InstanceNotFoundException;
	
	public List<Device> findAllDevice();
	
	public List<String> findAllNames();

	public double getPosition(Long id) throws InstanceNotFoundException;
}
