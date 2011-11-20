package org.pfc.business.monitorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.pfc.business.data.Data;
import org.pfc.business.dataservice.IDataService;
import org.pfc.business.device.Device;
import org.pfc.business.mibobject.MibObject;
import org.pfc.snmp.SnmpResponse;
import org.pfc.snmp.SnmpService;

public class MonitorTask extends TimerTask {

	private IDataService dataService;
	
	private SnmpService snmpService = new SnmpService();
	private Device device;
	private List<MibObject> mibObjects;
	private List<String> oids = new ArrayList<String>();

	public MonitorTask(Device device, List<MibObject> mibObjects, IDataService dataService) {
		this.device = device;
		this.mibObjects = mibObjects;
		this.oids = getOids(mibObjects);
		this.dataService = dataService;
	}
	
	@Override
	public void run() {
		List<SnmpResponse> results = snmpService.snmpGetQueryList(device.getPublicCommunity(), device.getIpAddress()
				, device.getSnmpPort(), oids);
		System.out.println("Saving in database...");
		showResults(mibObjects, results);
		saveResults(device,mibObjects,results);
	}
	
	private List<String> getOids(List<MibObject> mibObjects) {
		List<String> oids = new ArrayList<String>();
		for (MibObject m : mibObjects){
			oids.add(m.getOid());
		}
		return oids;
	}
	
	private void showResults(List<MibObject> mibObjects, List<SnmpResponse> results) {
		if (mibObjects.size() == results.size()) {
			System.out.println("**************************");
			for (int i=0;i<mibObjects.size();i++) {
				System.out.println(mibObjects.get(i).getMibObjectName()+": "+results.get(i).getValue()
						+" ("+ results.get(i).getDate().getTime().toString()+")");
			}
			System.out.println("**************************");
		}
	}
	
	private void saveResults(Device device, List<MibObject> mibObjects, List<SnmpResponse> results) {
		System.out.println("Device: "+device.getDeviceId()+" - "+device.getDeviceName());
		for (int i=0; i<mibObjects.size();i++) {
			System.out.println("MibObject: "+mibObjects.get(i).getMibObjectId()+" - "+results.get(i).getValue());
//			dataDao.save(new Data(results.get(i).getValue(),results.get(i).getDate(),device,mibObjects.get(i)));
	
			dataService.addNewdata(new Data(results.get(i).getValue(),results.get(i).getDate(),device,mibObjects.get(i)));
		}
	}

}
