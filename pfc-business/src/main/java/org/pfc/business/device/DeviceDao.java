package org.pfc.business.device;

import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository("deviceDao")
public class DeviceDao extends GenericDao<Device, Long> implements IDeviceDao{

	@SuppressWarnings("unchecked")
	public List<Device> getAllDevices() {
		
		return getSession().createQuery(
				"SELECT d FROM Device d ORDER BY d.deviceName").list();

	}
	
	public Device getDeviceByName(String deviceName) throws InstanceNotFoundException {
		
		Device device = (Device) getSession().createQuery(
				"FROM Device d WHERE d.deviceName = :deviceName")
				.setParameter("deviceName", deviceName)
				.uniqueResult();
		if (device == null) {
			throw new InstanceNotFoundException(deviceName, Device.class.getName());
		}
		else {
			return device;
		}
	}
	
}
