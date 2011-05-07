package org.pfc.business.test.productservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pfc.business.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.pfc.business.util.GlobalNames.SPRING_CONFIG_FILE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pfc.business.mibobject.IMibObjectDao;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.IProductDao;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class ProductServiceTest {
	
	private IProductDao productDao;
	
	@Autowired
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	
	private IMibObjectDao mibObjectDao;

	@Autowired
	public void setMibObjectDao(IMibObjectDao mibObjectDao) {
		this.mibObjectDao = mibObjectDao;
	}
	
	private IProductService productService;
	
	@Autowired
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@Test
	public void testCreateProduct() {
		Product product = productService.createProduct(new Product("AP-700", "Punto de acceso Wifi", "Proxim"));
		
		assertTrue(productDao.exists(product.getProductId()));
		
	}
	
	@Test
	public void testFindAllProducts() {
		productDao.save(new Product("AP-700", "Punto de acceso Wifi", "Proxim"));
		productDao.save(new Product("AP-4000M", "Punto de acceso Wifi", "Proxim"));
		productDao.save(new Product("AP-4000MR", "Punto de acceso Wifi", "Proxim"));
		List<Product> products = productService.findAllProducts();
		
		assertTrue(products.size() == 3);		
	}
	
	@Test
	public void testCreateMibObject() {
		MibObject mibObject = productService.createMibObject(new MibObject("sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II"));
		
		assertTrue(mibObjectDao.exists(mibObject.getMibObjectId()));
	}
	
	@Test
	public void testCreateMibObjectWithProducts() throws InstanceNotFoundException {
		Product product1 = productService.createProduct(new Product("AP-700", "Punto de acceso Wifi", "Proxim"));
		Product product2 = productService.createProduct(new Product("AP-4000", "Punto de acceso Wifi", "Proxim"));
		List<Product> listProd = new ArrayList<Product>();
		listProd.add(product1);
		listProd.add(product2);
		MibObject mibObject = new MibObject("sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II");
		mibObject.setProducts(listProd);
		productService.createMibObject(mibObject);
		System.out.println(mibObjectDao.find(mibObject.getMibObjectId()).getProducts().size());
		
		assertEquals(mibObject, productService.findMibObject(mibObject.getMibObjectId()));
		
	}
	
	@Test
	public void testFindAllMibObjects() {
		mibObjectDao.save( new MibObject("sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II"));
		mibObjectDao.save( new MibObject("sysLocation", "Posición del equipo", "1.3.6.1.2.1.1.6.0", "MIB-II"));
		List<MibObject> mibObjects = productService.findAllMibObjects();
		
		assertTrue(mibObjects.size() == 2);
	}

}
