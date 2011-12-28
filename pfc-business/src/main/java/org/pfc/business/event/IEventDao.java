package org.pfc.business.event;

import java.util.List;

import org.pfc.business.util.genericdao.IGenericDao;

public interface IEventDao extends IGenericDao<Event, Long> {
	
	public List<Event> findAllEvents();

}
