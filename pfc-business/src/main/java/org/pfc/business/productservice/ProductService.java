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
	
	private IProductDao productDao;
	private IMibObjectDao mibObjectDao;
	
	@Autowired
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	
	@Autowired
	public void setMibObjectDao(IMibObjectDao mibObjectDao) {
		this.mibObjectDao = mibObjectDao;
	}

	public Product createProduct(Product product) {
		productDao.save(product);
		return product;
	}

	public void removeProduct(Long productId) throws InstanceNotFoundException {
		productDao.remove(productId);		
	}

	@Transactional(readOnly = true)
	public Product findProduct(Long productId) throws InstanceNotFoundException {
		return productDao.find(productId);
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAllProducts() {
		return productDao.getAllProducts();
	}

	public MibObject createMibObject(MibObject mibObject) {
		mibObjectDao.save(mibObject);
		return mibObject;
	}
	
	public void removeMibObject(Long mibObjectId) throws InstanceNotFoundException {
		mibObjectDao.remove(mibObjectId);		
	}
	
	@Transactional(readOnly = true)
	public MibObject findMibObject(Long mibObjectId) throws InstanceNotFoundException {
		return mibObjectDao.find(mibObjectId);
	}
	
	@Transactional(readOnly = true)
	public List<MibObject> findAllMibObjects() {
		return mibObjectDao.getAllMibObjects();
	}

//	@Transactional(readOnly = true)
//	public List<MibObject> findMibObjectsByProduct(Long productId) throws InstanceNotFoundException {
//		return productDao.find(productId).getMibObjects();
//		//return mibObjectDao.getMibObjectsByProduct(productId);
//	}
//	
//	@Transactional(readOnly = true)
//	public List<Product> findProductsByMibObject(Long mibObjectId) throws InstanceNotFoundException {
//		return mibObjectDao.find(mibObjectId).getProducts();
//		//return productDao.getProductsByMibObject(mibObjectId);
//	}
//	
//	public void addMibObjectsToProduct(Long productId, List<MibObject> mibObjects) {
//		
//		Product product;
//		try {
//			product = productDao.find(productId);
////			product.getMibObjects().clear();
////			product.getMibObjects().addAll(mibObjects);
//			product.setMibObjects(mibObjects);
//			productDao.save(product);
//		} catch (InstanceNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//public void addProductsToMibObject(Long mibObjectId, List<Product> products) {
//		
//		MibObject mibObject;
//		try {
//			mibObject = mibObjectDao.find(mibObjectId);
//
//			mibObject.setProducts(products);
//			mibObjectDao.save(mibObject);
//		} catch (InstanceNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
