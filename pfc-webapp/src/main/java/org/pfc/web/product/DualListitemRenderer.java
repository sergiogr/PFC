package org.pfc.web.product;

import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public abstract class DualListitemRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		item.setValue(data);
		doRender(item, data);
	}
	
	public Listhead getListhead() {
		return null;
	}

	protected abstract void doRender(Listitem item, Object data) throws Exception;
	
}
