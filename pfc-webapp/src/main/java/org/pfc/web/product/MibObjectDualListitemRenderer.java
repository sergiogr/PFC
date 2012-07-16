package org.pfc.web.product;

import org.pfc.business.webservice.MibObjectDTO;
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
public class MibObjectDualListitemRenderer extends DualListitemRenderer {

	@Override
	protected void doRender(Listitem item, Object data) throws Exception {
		MibObjectDTO mibObject = (MibObjectDTO) data;
		new Listcell(mibObject.getMibObjectName()).setParent(item);
		new Listcell(mibObject.getDescription()).setParent(item);
		new Listcell(mibObject.getMib()).setParent(item);
		new Listcell(mibObject.getOid()).setParent(item);
		
	}
	
	public Listhead getListhead() {
		Listhead lh = new Listhead();
		new Listheader("Name", null, "20%").setParent(lh);
		new Listheader("Description", null, "30%").setParent(lh);
		new Listheader("MIB", null, "15%").setParent(lh);
		new Listheader("OID", null, "35%").setParent(lh);
		return lh;
		
	}

}
