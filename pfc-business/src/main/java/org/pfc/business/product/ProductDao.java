package org.pfc.business.product;

import java.util.List;

import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository("productDao")
public class ProductDao extends GenericDao<Product, Long> implements IProductDao {

	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts() {

		return getSession().createQuery(
				"SELECT p FROM Product p ORDER BY p.productName").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findProductsByMibOBjectId(Long mibObjectId) {
		return getSession().createQuery("SELECT DISTINCT p " +
		"FROM Product p JOIN p.mibObjects mo " +
		"WHERE mo.mibObjectId = :mibObjectId " +
		"ORDER BY p.productName").
		setParameter("mibObjectId", mibObjectId).list();
	}

}
