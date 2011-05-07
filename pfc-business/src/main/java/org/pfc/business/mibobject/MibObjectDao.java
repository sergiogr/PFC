package org.pfc.business.mibobject;

import java.util.List;

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
//		Session session = getSession();
//		session.beginTransaction();
//		Criteria q = session.createCriteria(MibObject.class);
//		return q.list();
		return getSession().createQuery(
				"SELECT m FROM MibObject m ORDER BY m.mibObjectName").list();
	}

}
