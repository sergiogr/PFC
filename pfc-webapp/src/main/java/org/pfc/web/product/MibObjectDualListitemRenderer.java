package org.pfc.web.product;

import org.pfc.business.mibobject.MibObject;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class MibObjectDualListitemRenderer extends DualListitemRenderer {

	@Override
	protected void doRender(Listitem item, Object data) throws Exception {
		MibObject mibObject = (MibObject) data;
		new Listcell(mibObject.getMibObjectName()).setParent(item);
		new Listcell(mibObject.getDescription()).setParent(item);
		
	}
	
	public Listhead getListhead() {
		Listhead lh = new Listhead();
		new Listheader("Name", null, "80px").setParent(lh);
		new Listheader("Description", null, "80px").setParent(lh);
		return lh;
		
	}

}
