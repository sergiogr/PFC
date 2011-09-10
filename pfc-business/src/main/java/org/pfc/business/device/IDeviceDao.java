package org.pfc.business.device;

import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.util.genericdao.IGenericDao;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IDeviceDao extends IGenericDao<Device, Long> {

	/**
	 * 
	 * @return The list of all {@link Device} entities found.
	 */
	public List<Device> getAllDevices();
	
	public Device getDeviceByName(String deviceName) throws InstanceNotFoundException;
	
}
