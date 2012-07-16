package org.pfc.business.webservice;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.event.Event;
import org.pfc.business.eventservice.IEventService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class EventWebService implements IEventWebService {

	@Autowired
	private IEventService eventService;
	
	@Autowired
	private IDeviceService deviceService;

	@Override
	public void addNewEvent(EventDTO eventDTO) {
		
		System.out.println("**EventWebService>> Adding new Event...");
		Device device;
		if (eventDTO.getIpSource() != null) 
			System.out.println("**************** "+eventDTO.getIpSource() +" ****************");
		if (eventDTO.getPdu() != null)
			System.out.println("**************** "+eventDTO.getPdu()+" ****************");

		try {
			if (eventDTO.getDeviceId() != null) {
				device = deviceService.findDevice(eventDTO.getDeviceId());
			}
			else {
				device = null;
			}
		} catch (InstanceNotFoundException e) {
			device = null;
		}
		
		eventService.addNewEvent(new Event(eventDTO.getIpSource(),eventDTO.getDate(),
				eventDTO.getTrapType(), eventDTO.getVariables(), eventDTO.getPdu(), device));

	}
	
	@Override
	public EventsFindResponse findAllEvents() {

		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		
		for (Event e : eventService.findAllEvents()) {
			eventDTOs.add(toEventDTO(e));
		}
		return new EventsFindResponse(eventDTOs);
	}
	
	private EventDTO toEventDTO(Event event) {
		Long deviceId = null;
		if (event.getDevice() != null) {
			deviceId = event.getDevice().getDeviceId();
		}
		return new EventDTO(event.getIpSource(), event.getDate(), event.getTrapType(),
				event.getVariables(), event.getPdu(), deviceId);
	}

}
