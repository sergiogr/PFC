package org.pfc.web.event;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import org.pfc.business.event.Event;
import org.pfc.business.eventservice.IEventService;
import org.pfc.snmp.TrapReceiver;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class EventController extends GenericForwardComposer {

	@Autowired
	private IEventService eventService;
	
	private TrapReceiver trapReceiver;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
	}
	
	public List<Event> getEvents() {
		return eventService.findAllEvents();
	}
	
	public void onClick$startTrapReceiver() {
		System.out.println("WEB: Starting Trap Receiver...");
		setTrapReceiver(new TrapReceiver("0.0.0.0","1162"));
		trapReceiver.start();
	}
	
	public void onClick$stopTrapReceiver() {
		trapReceiver.stop();
		setTrapReceiver(null);
	}

	public void setTrapReceiver(TrapReceiver trapReceiver) {
		this.trapReceiver = trapReceiver;
	}

	public TrapReceiver getTrapReceiver() {
		return trapReceiver;
	}
}
