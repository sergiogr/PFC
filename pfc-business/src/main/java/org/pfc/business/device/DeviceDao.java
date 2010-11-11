package org.pfc.business.device;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
//@Scope(BeanDefinition.SCOPE_SINGLETON)
public class DeviceDao extends GenericDao<Device, Long>
		implements IDeviceDao{

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> getAllDevices() {
			
//		WKTReader wktReader = new WKTReader();
//		Geometry filter = null;
//		try {
//			filter = wktReader.read("POLYGON((1 1, 20 1, 20 20, 1 20, 1 1))");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Session session = getSession();
		session.beginTransaction();
		Criteria q = session.createCriteria(Device.class);
//		q.add(SpatialRestrictions.within("position", filter));
//		Query q = getSession().createQuery("FROM Device WHERE within(position, ?) = true");
//		Type geometryType = GeometryUserType.TYPE;
//		q.setParameter(0, filter, geometryType);
		List<Device> results = q.list();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllNames() {
		return getSession().createQuery("SELECT a.name FROM Device a ORDER BY a.name").list();
	}
}
