package org.pfc.business.mibobject;

import java.util.List;

import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository("mibObjectDao")
public class MibObjectDao extends GenericDao<MibObject, Long> implements IMibObjectDao {

	@SuppressWarnings("unchecked")
	public List<MibObject> getAllMibObjects() {

		return getSession().createQuery(
				"SELECT m FROM MibObject m ORDER BY m.mibObjectName").list();
	}

	@SuppressWarnings("unchecked")
	public List<MibObject> findMibObjectsByProductId(Long productId) {
		return getSession().createQuery("SELECT DISTINCT mo " +
				"FROM MibObject mo JOIN mo.products p " +
				"WHERE p.productId = :productId " +
				"ORDER BY mo.mibObjectName ").
				setParameter("productId", productId).list();
		
	}
}
