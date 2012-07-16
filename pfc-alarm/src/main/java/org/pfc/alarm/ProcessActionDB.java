package org.pfc.alarm;

import java.util.Calendar;

import org.pfc.business.webservice.EventDTO;
import org.pfc.business.webservice.IEventWebService;
import org.pfc.snmp.ProcessAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessActionDB implements ProcessAction {

	@Override
	public void processPDUAction(String ipSource,String trapType,String variables,String pdu) {
		
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:/pfc-alarm-spring-config.xml");
		IEventWebService eventWebService = (IEventWebService) cxt.getBean("eventWSClient");

		EventDTO event = new EventDTO(ipSource, Calendar.getInstance(), trapType,
				variables , pdu, null);
		eventWebService.addNewEvent(event);

		System.out.println("Evento añadido...."+event.getIpSource());
		System.out.println("*********** "+Calendar.getInstance());
		System.out.println("*********** "+event.getTrapType());


	}

}
