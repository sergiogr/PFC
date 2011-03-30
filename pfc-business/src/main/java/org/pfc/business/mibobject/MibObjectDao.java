package org.pfc.business.mibobject;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class MibObjectDao extends GenericDao<MibObject, Long> implements IMibObjectDao {


	@SuppressWarnings("unchecked")
	@Transactional
	public List<MibObject> getAllMibObjects() {
		Session session = getSession();
		session.beginTransaction();
		Criteria q = session.createCriteria(MibObject.class);
		return q.list();
	}

}
