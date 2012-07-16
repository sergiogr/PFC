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

		return new DataFindResponse(toDataDTOs(dataService.findDataByDeviceId(deviceId)));
		
	}
	
	private List<DataDTO> toDataDTOs(List<Data> data) {
		List<DataDTO> dataDTOs = new ArrayList<DataDTO>();
	
		for (Data d : data) {
			try {
				dataDTOs.add(new DataDTO(d.getValue(), d.getDate(),d.getDevice().getDeviceId(), d.getMibObject().getMibObjectId(),
						productService.findMibObject(d.getMibObject().getMibObjectId()).getMibObjectName()));
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataDTOs;		
	}

}
