package org.pfc.business.device;

import java.util.List;

import org.pfc.business.util.genericdao.IGenericDao;

public interface IDeviceDao extends IGenericDao<Device, Long> {

	public List<Device> getAllDevices();
	
}
