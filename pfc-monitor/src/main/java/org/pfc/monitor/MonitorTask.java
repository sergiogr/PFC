package org.pfc.monitor;


import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.pfc.business.webservice.DataDTO;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDataWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.pfc.snmp.SnmpResponse;
import org.pfc.snmp.SnmpService;

public class MonitorTask extends TimerTask {

	private IDataWebService dataWebService;
	
	private SnmpService snmpService = new SnmpService();
	private DeviceDTO deviceDTO;
	private List<MibObjectDTO> mibObjects;
	private List<String> oids = new ArrayList<String>();

	public MonitorTask(DeviceDTO deviceDTO, List<MibObjectDTO> mibObjects, IDataWebService dataWebService) {
		this.deviceDTO = deviceDTO;
		this.mibObjects = mibObjects;
		this.oids = getOids(mibObjects);
		this.dataWebService = dataWebService;
	}
	
	@Override
	public void run() {
		List<SnmpResponse> results = snmpService.snmpGetQueryList(deviceDTO.getPublicCommunity(), deviceDTO.getIpAddress()
			, deviceDTO.getSnmpPort(), oids);
		
		if ((results != null) && (results.size() > 0)) {
			showResults(mibObjects, results);
			System.out.println("Saving in database...");
			saveResults(deviceDTO,mibObjects,results);
		}

	}
	
	private List<String> getOids(List<MibObjectDTO> mibObjects) {
		List<String> oids = new ArrayList<String>();
		for (MibObjectDTO m : mibObjects){
			oids.add(m.getOid());
		}
		return oids;
	}
	
	private void showResults(List<MibObjectDTO> mibObjects, List<SnmpResponse> results) {
		if (mibObjects.size() == results.size()) {
			System.out.println("**************************");
			for (int i=0;i<mibObjects.size();i++) {
				System.out.println(mibObjects.get(i).getMibObjectName()+": "+results.get(i).getValue()
						+" ("+ results.get(i).getDate().getTime().toString()+")");
			}
			System.out.println("**************************");
		}
	}
	
	private void saveResults(DeviceDTO deviceDTO, List<MibObjectDTO> mibObjects, List<SnmpResponse> results) {
		System.out.println("Device: "+deviceDTO.getDeviceId()+" - "+deviceDTO.getDeviceName());
		for (int i=0; i<mibObjects.size();i++) {
			System.out.println("MibObject: "+mibObjects.get(i).getMibObjectId()+" - "+results.get(i).getValue());
	
			dataWebService.addNewData(new DataDTO(results.get(i).getValue(),results.get(i).getDate(),deviceDTO.getDeviceId(),mibObjects.get(i).getMibObjectId(),mibObjects.get(i).getMibObjectName()));
		}
	}

}

