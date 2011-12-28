package org.pfc.business.eventservice;

import java.util.List;

import org.pfc.business.event.Event;

public interface IEventService {
	
	public void addNewEvent(Event event);
	
	public List<Event> findAllEvents();

}
