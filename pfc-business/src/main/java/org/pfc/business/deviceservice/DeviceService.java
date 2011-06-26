package org.pfc.business.deviceservice;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Service("deviceService")
@Transactional
public class DeviceService implements IDeviceService {

	private IDeviceDao deviceDao;
        
    @Autowired
    public void setDeviceDao(IDeviceDao deviceDao) {
    	this.deviceDao = deviceDao;    
    }
    
    public Device createDevice(Device device) throws DuplicateInstanceException{
    
    	try {
    		deviceDao.getDeviceByName(device.getDeviceName());
    		throw new DuplicateInstanceException(device.getDeviceName(), Device.class.getName());
    	} catch (InstanceNotFoundException e) {
    		deviceDao.save(device);
        	return device;
    	}	
    }
    
    public void removeDevice(Long deviceId) throws InstanceNotFoundException {
    	deviceDao.remove(deviceId);
    }    
    
    public void updateDevice(Device device) throws InstanceNotFoundException, DuplicateInstanceException {
    	
    	Device dev = deviceDao.find(device.getDeviceId());
    	if (!dev.getDeviceName().equals(device.getDeviceName())) {
    		try {
    			System.out.println(dev.getDeviceName());
    			System.out.println(device.getDeviceName());

    			deviceDao.getDeviceByName(device.getDeviceName());
    			throw new DuplicateInstanceException(device.getDeviceName(), Device.class.getName());
    		} catch (InstanceNotFoundException e) {
    			dev.setDeviceName(device.getDeviceName());
    		}
    	}
    	dev.setDescription(device.getDescription());
    	dev.setIpAddress(device.getIpAddress());
    	dev.setPublicCommunity(device.getPublicCommunity());
    	dev.setSnmpPort(device.getSnmpPort());
    	dev.setProduct(device.getProduct());
    	dev.setPosition(device.getPosition());
    	
    	deviceDao.save(dev);
    }
    
	public Device editDevice(Long deviceId, DeviceInfo deviceInfo) throws InstanceNotFoundException {
		
		Device device = deviceDao.find(deviceId);
		
		device.setDeviceName(deviceInfo.getDeviceName());
		device.setDescription(deviceInfo.getDescription());
		device.setIpAddress(deviceInfo.getIpAddress());
		//NOTA: faltan datos SNMP y Position
		return device;
		
	}
    
    @Transactional(readOnly = true)
    public Device findDevice(Long deviceId) throws InstanceNotFoundException {
    	return deviceDao.find(deviceId);
    }
    
    @Transactional(readOnly = true)
    public List<Device> findAllDevice() {
    	return deviceDao.getAllDevices();
    }
    
    @Transactional(readOnly = true)
    public List<MibObject> getMibObjects(Long deviceId) throws InstanceNotFoundException {
    	return deviceDao.find(deviceId).getProduct().getMibObjects();
    }
    
    @Transactional(readOnly = true)
    public Device findDeviceByName(String deviceName) throws InstanceNotFoundException {
    	return deviceDao.getDeviceByName(deviceName);
    }
    
}

