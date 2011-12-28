package org.pfc.web.monitor;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.dataservice.DataInfo;
import org.pfc.business.dataservice.IDataService;
import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.monitorservice.IMonitorService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;

@SuppressWarnings("serial")
public class MonitorController extends GenericForwardComposer {
	
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired
	private IDataService dataService;
	
	@Autowired
	private IDeviceService deviceService;
	
	private Label monitorStatus;
	private Listbox deviceLb;
	
	private Device selected;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		monitorStatus.setValue("Monitor stopped");	
	}
	
	public List<Device> getDevices() {
		return deviceService.findAllDevice();
	}

	public List<DataInfo> getData() {
		if (selected != null) {
			return dataService.findDataByDeviceId(selected.getDeviceId());
		}
		else {
			return new ArrayList<DataInfo>();
		}
	}
	
	public void onClick$startMonitor() {
		System.out.println("WEB: Starting monitor...");
		monitorService.startMonitor();
		System.out.println("WEB: Monitor started!");
		monitorStatus.setValue("Monitor started");

	}
	
	public void onClick$stopMonitor() {
		System.out.println("WEB: Stoping monitor...");
		monitorService.stopMonitor();
		System.out.println("WEB: Monitor stopped!");
		monitorStatus.setValue("Monitor stopped");

	}
	
	public void onSelect$deviceLb() {

		try {
			selected = deviceService.findDeviceByName(((Device) deviceLb.getSelectedItem().getValue()).getDeviceName());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
