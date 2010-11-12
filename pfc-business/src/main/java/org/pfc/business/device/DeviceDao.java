package org.pfc.business.device;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class DeviceDao extends GenericDao<Device, Long> implements IDeviceDao{

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> getAllDevices() {
		Session session = getSession();
		session.beginTransaction();
		Criteria q = session.createCriteria(Device.class);
		List<Device> results = q.list();
		return results;
	}

}
