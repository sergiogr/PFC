package org.pfc.business.productservice;

import java.util.List;

import org.pfc.business.mibobject.IMibObjectDao;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.IProductDao;
import org.pfc.business.product.Product;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Service("productService")
@Transactional
public class ProductService implements IProductService {
	
	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IMibObjectDao mibObjectDao;

	public Product createProduct(Product product) {
		productDao.save(product);
		return product;
	}

	public MibObject createMibObject(MibObject mibObject) {
		mibObjectDao.save(mibObject);
		return mibObject;
	}
	
	public void removeProduct(Long productId) throws InstanceNotFoundException {
		
		Product product = productDao.find(productId);
		
		for (MibObject mo : product.getMibObjects()) {
			mo.getProducts().remove(product);
		}
		productDao.remove(productId);		
	}
	
	public void removeMibObject(Long mibObjectId) throws InstanceNotFoundException {
		
		MibObject mibObject = mibObjectDao.find(mibObjectId);
		
		for (Product p : mibObject.getProducts()) {
			p.getMibObjects().remove(mibObject);
		}
		mibObjectDao.remove(mibObjectId);		
	}
	
	public void assignMibObjectToProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException{
		
		MibObject mibObject = mibObjectDao.find(mibObjectId);
		Product product = productDao.find(productId);
		
		mibObject.addProduct(product);
	}
	
	public void unassignMibObjectFromProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException {
		
		MibObject mibObject = mibObjectDao.find(mibObjectId);
		Product product = productDao.find(productId);
		
		mibObject.removeProduct(product);
	}

	@Transactional(readOnly = true)
	public Product findProduct(Long productId) throws InstanceNotFoundException {
		return productDao.find(productId);
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productDao.getAllProducts();
	}
	
	@Transactional(readOnly = true)
	public MibObject findMibObject(Long mibObjectId) throws InstanceNotFoundException {
		return mibObjectDao.find(mibObjectId);
	}
	
	@Transactional(readOnly = true)
	public List<MibObject> findAllMibObjects() {
		return mibObjectDao.getAllMibObjects();
	}
	
	@Transactional(readOnly = true)
	public List<Product> findProductsByMibObjectId(Long mibObjectId) {
		return productDao.findProductsByMibOBjectId(mibObjectId);
	}
	
	@Transactional(readOnly = true)
	public List<MibObject> findMibObjectsByProductId(Long productId) {
		return mibObjectDao.findMibObjectsByProductId(productId);
	}
	

}
