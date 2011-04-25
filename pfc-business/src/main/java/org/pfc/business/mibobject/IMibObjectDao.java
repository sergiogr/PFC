package org.pfc.business.mibobject;

import java.util.List;

import org.pfc.business.util.genericdao.IGenericDao;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IMibObjectDao extends IGenericDao<MibObject, Long> {
	
	/**
	 * 
	 * @return The list of all {@link MibObject} entities found.
	 */
	public List<MibObject> getAllMibObjects();
	
//	/**
//	 * 
//	 * @param productId
//	 * @return The list of all {@link MibObject} entities found for a {@link Product}.
//	 */
//	public List<MibObject> getMibObjectsByProduct(Long productId);

}
