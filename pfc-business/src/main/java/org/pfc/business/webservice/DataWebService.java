package org.pfc.business.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.pfc.business.data.Data;
import org.pfc.business.dataservice.IDataService;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@WebService
public class DataWebService implements IDataWebService {

	@Autowired
	private IDataService dataService;
	
	@Autowired
	private IDeviceService deviceService;
	
	@Autowired
	private IProductService productService;
	
	@Override
	public void addNewData(DataDTO dataDTO) {

		Data data = new Data();
		data.setDate(dataDTO.getDate());
		try {
			data.setDevice(deviceService.findDevice(dataDTO.getDeviceId()));
			data.setMibObject(productService.findMibObject(dataDTO.getMibObjectId()));
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.setValue(dataDTO.getValue());
		dataService.addNewData(data);
		
	}

	@Override
	public DataFindResponse findDataByDeviceId(Long deviceId) {

		List<Data> data = dataService.findDataByDeviceId(deviceId); 
		List<DataDTO> dataDTO = new ArrayList<DataDTO>();
		
		for (Data d : data) {
			dataDTO.add(toDataDTO(d));
		}
		return new DataFindResponse(dataDTO);		
	}
	

	@Override
	public DataFindResponse findDataByDeviceIdAndMibObjectId(Long deviceId,
			Long mibObjectId) {

		List<Data> data = dataService.findDataByDeviceIdAndMibObjectId(deviceId, mibObjectId);
		List<DataDTO> dataDTO = new ArrayList<DataDTO>();
		
		for (Data d : data) {
			dataDTO.add(toDataDTO(d));
		}
		return new DataFindResponse(dataDTO);
	}
	

	@Override
	public DataDTO getMostRecentValue(Long deviceId, Long mibObjectId) {
		Data d = dataService.getMostRecentValue(deviceId, mibObjectId);
		if (d != null) {
			return toDataDTO(d);
		}
		else {
			return null;
		}
	}
	
	private DataDTO toDataDTO(Data data) {
		DataDTO d;
		try {
			d = new DataDTO(data.getValue(),data.getDate(),data.getDevice().getDeviceId(),
					data.getMibObject().getMibObjectId(),productService.findMibObject(data.getMibObject().getMibObjectId()).getMibObjectName());
			d.setDataId(data.getDataId());
			return d;
		} catch (InstanceNotFoundException e) {
			return null;
		}

	}


}
