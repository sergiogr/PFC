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
	
	public void removeProduct(Long productId) throws InstanceNotFoundException;
	
	public Product findProduct(Long productId) throws InstanceNotFoundException;
	
	public List<Product> findAllProducts();
	
	public MibObject createMibObject(MibObject mibObject);
	
	public void removeMibObject(Long mibObjectId) throws InstanceNotFoundException;
	
	public MibObject findMibObject(Long mibObjectId) throws InstanceNotFoundException;
	
//	public List<MibObject> findMibObjectsByProduct(Long productId) throws InstanceNotFoundException;
//	
//	public List<Product> findProductsByMibObject(Long mibObjectId) throws InstanceNotFoundException;

	public List<MibObject> findAllMibObjects();
	
//	public void addMibObjectsToProduct(Long productId, List<MibObject> mibObjects);
//
//	public void addProductsToMibObject(Long mibObjectId, List<Product> products);

}
