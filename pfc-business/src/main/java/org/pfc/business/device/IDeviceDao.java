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
	
	/**
	 * 
	 * @param deviceName
	 * @return
	 * @throws InstanceNotFoundException
	 */
	public Device getDeviceByName(String deviceName) throws InstanceNotFoundException;
	

	/**
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public List<Device> getDevicesByArea(double lat1, double lng1, double lat2, double lng2);
}
