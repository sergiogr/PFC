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
	
//	private List<DataInfo> toDataInfos(List<Data> data) {
//		List<DataInfo> dataInfos = new ArrayList<DataInfo>();
//		
//		for (Data d : data) {
//			dataInfos.add(new DataInfo(d.getMibObject().getMibObjectName(),
//					d.getValue(), d.getDate()));
//		}
//		return dataInfos;		
//	}
}
