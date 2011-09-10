package org.pfc.business.productservice;

import java.util.List;

import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IProductService {
	
	public Product createProduct(Product product);

	public MibObject createMibObject(MibObject mibObject);
	
	public void assignMibObjectToProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException;

	public void unassignMibObjectFromProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException;
	
	public void removeProduct(Long productId) throws InstanceNotFoundException;
	
	public void removeMibObject(Long mibObjectId) throws InstanceNotFoundException;

	public Product findProduct(Long productId) throws InstanceNotFoundException;
	
	public List<Product> findAllProducts();
		
	public MibObject findMibObject(Long mibObjectId) throws InstanceNotFoundException;

	public List<MibObject> findAllMibObjects();
	
	public List<Product> findProductsByMibObjectId(Long mibObjectId);
	
	public List<MibObject> findMibObjectsByProductId(Long productId);

}
