package org.pfc.alarm;

import java.util.Calendar;

import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.EventDTO;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IEventWebService;
import org.pfc.snmp.ProcessAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessActionDB implements ProcessAction {

	@Override
	public void processPDUAction(String ipSource,String trapType,String variables,String pdu) {
		
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:/pfc-alarm-spring-config.xml");
		IEventWebService eventWSClient = (IEventWebService) cxt.getBean("eventWSClient");
		IDeviceWebService deviceWSClient = (IDeviceWebService) cxt.getBean("deviceWSClient");

//		DeviceDTO device = deviceWSClient.findDeviceByIpAddress(ipSource.split("/")[0]);
		System.out.println("Processing PDU database from... "+ipSource.split("/")[0]);
		EventDTO event;
//		if (device) {
			System.out.println("Ningún Device encontrado con la ip "+ipSource);
			event = new EventDTO(ipSource, Calendar.getInstance(), trapType,
					variables , pdu, null);
//		}
//		else {
//				event = new EventDTO(ipSource, Calendar.getInstance(), trapType,
//				variables , pdu, device.getDeviceId());
//		}
		
		System.out.println("Adding "+event.getPdu()+" to database...");
		eventWSClient.addNewEvent(event);

		System.out.println("Evento añadido...."+event.getIpSource());
		System.out.println("*********** "+Calendar.getInstance());
		System.out.println("*********** "+event.getTrapType());


	}

}
