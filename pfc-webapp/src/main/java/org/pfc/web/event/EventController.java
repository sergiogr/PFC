package org.pfc.web.event;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import org.pfc.business.webservice.EventDTO;
import org.pfc.business.webservice.IEventWebService;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class EventController extends GenericForwardComposer {

	@Autowired
	private IEventWebService eventWSClient;
			
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
	}
	
	public List<EventDTO> getEvents() {
		return eventWSClient.findAllEvents().getEventDTOs();
	}
	
}
