package org.pfc.business.device;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository
public class DeviceDao extends GenericDao<Device, Long> implements IDeviceDao{

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> getAllDevices() {
		Session session = getSession();
		session.beginTransaction();
		Criteria q = session.createCriteria(Device.class);
		// Avoid duplicated elements in the devices list.
		q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return q.list();
	}
	
	@Transactional
	public Device getDeviceByName(String deviceName) throws InstanceNotFoundException {
		Session session = getSession();
		session.beginTransaction();
		
		Device device = (Device) session.createQuery("FROM Device d WHERE d.deviceName = :deviceName").setParameter("deviceName", deviceName).uniqueResult();
	
		if (device == null) {
			throw new InstanceNotFoundException(deviceName, Device.class.getName());
		}
		else {
			return device;
		}
	}
	
}
