package org.pfc.business.data;

import java.util.List;

import org.pfc.business.data.Data;
import org.pfc.business.util.genericdao.GenericDao;

import org.springframework.stereotype.Repository;

@Repository("dataDao")
public class DataDao extends GenericDao<Data, Long> implements IDataDao {

	@SuppressWarnings("unchecked")
	public List<Data> findDataByDeviceId(Long deviceId) {
		
		return getSession().createQuery("SELECT d FROM Data d " + 
				"WHERE d.device.deviceId = :deviceId ORDER BY d.date DESC")
				.setParameter("deviceId", deviceId).list();
		
	}

	@SuppressWarnings("unchecked")
	public List<Data> findDataByDeviceIdAndMibObjectId(Long deviceId,
			Long mibObjectId) {
		
		return getSession().createQuery("SELECT d FROM Data d " + 
		"WHERE d.device.deviceId = :deviceId AND d.mibObject.mibObjectId = :mibObjectId ORDER BY d.date DESC")
		.setParameter("deviceId", deviceId).setParameter("mibObjectId", mibObjectId).list();
		
	}
	

}
