package org.pfc.business.deviceservice;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("deviceService")
@Transactional
public class DeviceService implements IDeviceService {

	private IDeviceDao deviceDao;
        
    @Autowired
    public void setDeviceDao(IDeviceDao deviceDao) {
    	this.deviceDao = deviceDao;    
    }
    
    public Device createDevice(Device device) {
    	deviceDao.save(device);
    	return device;
    }
    
    public void removeDevice(Long deviceId) throws InstanceNotFoundException {
    	deviceDao.remove(deviceId);
    }    
    
    @Transactional(readOnly = true)
    public List<Device> findAllDevice() {
    	return deviceDao.getAllDevices();
    }

}
