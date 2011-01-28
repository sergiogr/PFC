package org.pfc.business.device;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
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
		return q.list();
	}

}
