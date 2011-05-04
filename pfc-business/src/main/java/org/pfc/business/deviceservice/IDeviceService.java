package org.pfc.business.deviceservice;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IDeviceService {

	public Device createDevice(Device device);
	
	public void removeDevice(Long deviceId) throws InstanceNotFoundException;
	
	public Device editDevice(Long deviceId, DeviceInfo deviceInfo) throws InstanceNotFoundException;
	
	public Device findDevice(Long deviceId) throws InstanceNotFoundException;
	
	public List<Device> findAllDevice();
	
    public List<MibObject> getMibObjects(Long deviceId) throws InstanceNotFoundException;

}
