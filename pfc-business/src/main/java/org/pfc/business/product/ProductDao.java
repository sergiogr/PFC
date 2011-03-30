package org.pfc.business.product;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class ProductDao extends GenericDao<Product, Long> implements IProductDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Product> getAllProducts() {
		Session session = getSession();
		session.beginTransaction();
		Criteria q = session.createCriteria(Product.class);
		return q.list();
	}

}
