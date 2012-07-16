package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EventsFindResponse", namespace="http://webservice.business.pfc.org/" )
public class EventsFindResponse {
	
	@XmlElementWrapper(name="eventDTOs")
	private List<EventDTO> eventDTOs;

	public EventsFindResponse() {

	}

	public EventsFindResponse(List<EventDTO> eventDTOs) {
		this.eventDTOs = eventDTOs;
	}

	public List<EventDTO> getEventDTOs() {
		return eventDTOs;
	}

	public void setEventDTOs(List<EventDTO> eventDTOs) {
		this.eventDTOs = eventDTOs;
	}	

}
