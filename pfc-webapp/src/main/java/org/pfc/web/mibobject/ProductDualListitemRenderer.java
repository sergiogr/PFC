package org.pfc.web.mibobject;

import org.pfc.business.product.Product;
import org.pfc.web.widgets.duallistbox.DualListitemRenderer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class ProductDualListitemRenderer extends DualListitemRenderer {
	
	@Override
	protected void doRender(Listitem item, Object data) throws Exception {
		Product product = (Product) data;
		new Listcell(product.getProductName()).setParent(item);
		new Listcell(product.getDescription()).setParent(item);
		new Listcell(product.getManufacturer()).setParent(item);
	}
	
	public Listhead getListhead() {
		Listhead lh = new Listhead();
		new Listheader("Name", null, "80px").setParent(lh);
		new Listheader("Description", null, "80px").setParent(lh);
		new Listheader("Manufacturer", null, "80px").setParent(lh);
		return lh;
		
	}
}
