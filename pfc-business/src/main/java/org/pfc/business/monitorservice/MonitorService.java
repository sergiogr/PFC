package org.pfc.business.monitorservice;

import java.util.List;
import java.util.Timer;

import org.pfc.business.dataservice.IDataService;
import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.pfc.business.mibobject.IMibObjectDao;
import org.pfc.business.monitorservice.MonitorTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("monitorService")
public class MonitorService implements IMonitorService {

	private Timer timer;
	
	@Autowired
	private IDeviceDao deviceDao;
	
	@Autowired 
	private IMibObjectDao mibObjectDao;
	
	@Autowired
	private IDataService dataService;

	@Transactional
	public void startMonitor() {
		System.out.println("Starting monitor...");
		List<Device> devices = deviceDao.getAllDevices();
		timer = new Timer();
		for (Device d: devices) {
			System.out.println(d.getDeviceName());
			timer.schedule(new MonitorTask(d, 
					mibObjectDao.findMibObjectsByProductId(d.getProduct().getProductId()),dataService),
				0, 60*1000);
			
		}
	}

	public void stopMonitor() {
		System.out.println("Stopping Monitor...");
		timer.cancel();
		System.out.println(timer.purge() + " tasks purged.");
	}

	public void restartMonitor() {
		this.stopMonitor();
		this.startMonitor();
		
	}
	
}
