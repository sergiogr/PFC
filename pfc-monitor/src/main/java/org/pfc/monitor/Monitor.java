package org.pfc.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDataWebService;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.pfc.monitor.MonitorTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Monitor {

	public static void main(String [] args) {
				
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:/pfc-monitor-spring-config.xml");

		IDeviceWebService deviceWebService = (IDeviceWebService) cxt.getBean("deviceWSClient");
		IProductWebService productWebService = (IProductWebService) cxt.getBean("productWSClient");
		IDataWebService dataWebService = (IDataWebService) cxt.getBean("dataWSClient");

		List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
		System.out.println(devices.size()+" devices");

		devices.addAll(deviceWebService.findAllDevice().getDeviceDTOs());
		
		Timer timer = new Timer();
		for (DeviceDTO d: devices) {
			System.out.println(d.getDeviceName());
			List<MibObjectDTO> mibObjects = new ArrayList<MibObjectDTO>();
			if (d.getProductId() !=  null) {
				mibObjects.addAll(productWebService.findMibObjectsByProductId(d.getProductId()).getMibObjectDTOs());
			
				if (mibObjects.size() > 0) {
					timer.schedule(new MonitorTask(d, mibObjects, dataWebService), 0, 60*1000);
				}
				else {
					System.out.println("No tiene ningún MibObject asociado");
				}
			}
			else {
				System.out.println("Este dispositivo no tiene un producto asociado");
			}
		}

	}
}
