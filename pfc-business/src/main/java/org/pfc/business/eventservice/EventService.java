package org.pfc.business.eventservice;

import java.util.List;

import org.pfc.business.event.Event;
import org.pfc.business.event.IEventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("eventService")
public class EventService implements IEventService {

	@Autowired
	private IEventDao eventDao;
	
	@Transactional
	public void addNewEvent(Event event) {

		eventDao.save(event);
	}

	@Transactional
	public List<Event> findAllEvents() {
		
		return eventDao.findAllEvents();
	}

}
