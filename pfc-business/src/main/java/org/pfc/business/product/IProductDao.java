package org.pfc.business.product;

import java.util.List;

import org.pfc.business.util.genericdao.IGenericDao;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IProductDao extends IGenericDao<Product, Long> {

	
	/**
	 * 
	 * @return The list of all {@link Product} entities found.
	 */
	public List<Product> getAllProducts();
	
	/**
	 * 
	 * @param mibObjectId
	 * @return The list of all {@link Product} entities assigned to a @{link MibObject}.
	 */
	public List<Product> findProductsByMibOBjectId(Long mibObjectId);
	
}
