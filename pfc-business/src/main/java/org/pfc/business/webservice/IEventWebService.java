package org.pfc.business.webservice;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://webservice.business.pfc.org/" )
public interface IEventWebService {

	public void addNewEvent(EventDTO eventDTO);
	
	@WebMethod(operationName = "findAllEvents")
	@WebResult(name = "EventsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public EventsFindResponse findAllEvents();
	
}
