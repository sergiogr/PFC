package org.pfc.business.event;

import java.util.List;

import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository("eventDao")
public class EventDao extends GenericDao<Event, Long> implements IEventDao {

	@SuppressWarnings("unchecked")
	public List<Event> findAllEvents() {

		return getSession().createQuery("SELECT e FROM Event e ORDER BY e.date DESC").list();

	}

	
}
