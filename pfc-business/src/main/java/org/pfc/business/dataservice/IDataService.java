package org.pfc.business.dataservice;

import java.util.List;

import org.pfc.business.data.Data;

public interface IDataService {

	public void addNewdata(Data data);
	
	public List<DataInfo> findDataByDeviceId(Long deviceId);
}
