package org.pfc.business.dataservice;

import java.util.List;

import org.pfc.business.data.Data;

public interface IDataService {

	public void addNewData(Data data);
	
	public List<Data> findDataByDeviceId(Long deviceId);
	
	public List<Data> findDataByDeviceIdAndMibObjectId(Long deviceId, Long mibObjectId);
	
	public Data getMostRecentValue(Long deviceId, Long mibObjectId);
	
}
