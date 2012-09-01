package org.pfc.business.data;

import java.util.List;

import org.pfc.business.data.Data;
import org.pfc.business.util.genericdao.IGenericDao;

public interface IDataDao extends IGenericDao<Data, Long> {

	public List<Data> findDataByDeviceId(Long deviceId);

	public List<Data> findDataByDeviceIdAndMibObjectId(Long deviceId, Long mibObjectId);

	public Data getMostRecentValue(Long deviceId, Long mibObjectId) ;
	
}
