package org.pfc.business.dataservice;

import java.util.List;

import org.pfc.business.data.Data;
import org.pfc.business.data.IDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dataService")
public class DataService implements IDataService{

	@Autowired
	private IDataDao dataDao;
		
	@Transactional
	public void addNewData(Data data) {

		dataDao.save(data);

	}

	@Transactional
	public List<Data> findDataByDeviceId(Long deviceId) {
		return dataDao.findDataByDeviceId(deviceId);
	}

	@Transactional
	public List<Data> findDataByDeviceIdAndMibObjectId(Long deviceId,
			Long mibObjectId) {
		return dataDao.findDataByDeviceIdAndMibObjectId(deviceId, mibObjectId);
	}

	@Transactional
	public Data getMostRecentValue(Long deviceId, Long mibObjectId) {

		return dataDao.getMostRecentValue(deviceId, mibObjectId);
	}
	
}
