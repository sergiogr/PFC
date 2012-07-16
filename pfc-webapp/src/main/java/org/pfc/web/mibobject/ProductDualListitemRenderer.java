package org.pfc.web.mibobject;

import org.pfc.business.webservice.ProductDTO;
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
		ProductDTO product = (ProductDTO) data;
		new Listcell(product.getProductName()).setParent(item);
		new Listcell(product.getDescription()).setParent(item);
		new Listcell(product.getManufacturer()).setParent(item);
	}
	
	public Listhead getListhead() {
		Listhead lh = new Listhead();
		new Listheader("Name", null, "25%").setParent(lh);
		new Listheader("Description", null, "50%").setParent(lh);
		new Listheader("Manufacturer", null, "25%").setParent(lh);
		return lh;
		
	}
}
