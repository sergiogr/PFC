package org.pfc.web.mibobject;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.web.widgets.duallistbox.DualListbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 * 
 * @author Sergio Garc’a Ramos <sergio.garcia@udc.es>
 *
 */
@SuppressWarnings("serial")
public class MibObjectCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */
	private Listbox mibObjectList;
	private Grid mibObjectForm;
	private Textbox name;
	private Textbox description;
	private Textbox oid;
	private Textbox mib;
	private DualListbox mibObjDualLb;
		
	private MibObject current = new MibObject();
	private MibObject newMibObj;
	
	@Autowired
	private IProductService productService;

	public MibObject getCurrent() {
		return current;
	}
	
	public void setCurrent(MibObject current) {
		this.current = current;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		mibObjDualLb.setModel(new ListModelList(productService.findAllProducts()));
		mibObjDualLb.setRenderer(new ProductDualListitemRenderer());
		mibObjectForm.setVisible(false);
	}
	
	public List<MibObject> getMibObjects() {
		return productService.findAllMibObjects();
	}
	
	public void onClick$addMibObject() {
		goToAddForm();
	}
	
	public void onClick$addTestData() {
		productService.createMibObject(new MibObject("sysDesc", "Descripci—n del equipo", "1.3.6.1.2.1.1.1.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysUpTime", "Tiempo desde la œltima vez que el equipo fue reiniciado", "1.3.6.1.2.1.1.3.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysContact", "Informaci—n de contacto de la persona que gestiona el eqipo", "1.3.6.1.2.1.1.4.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysLocation", "Localizaci—n del sistema", "1.3.6.1.2.1.1.6.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysServices", "Conjunto de servicios que ofrece el equipo", "1.3.6.1.2.1.1.7.0", "MIB-II"));
		
		productService.createMibObject(new MibObject("channel","Canal en el que est‡ emitiendo el equipo WiMax","1.3.6.1.4.1.11898.2.1.2.1.1.1.6.3","ORiNOCO-MIB"));
		productService.createMibObject(new MibObject("nClients","Nœmero de clientes conectados al equipo WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.1.3","ORiNOCO-MIB"));
		productService.createMibObject(new MibObject("localSignal","Nivel de se–al local del enlace WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.2.3","ORiNOCO-MIB"));
		productService.createMibObject(new MibObject("remoteSignal","Nivel de se–al remota del enlace WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.4.3","ORiNOCO-MIB"));

		productService.createMibObject(new MibObject("nClientsWifi","Nœmero de clientes conectados a un AP Wifi","1.3.6.1.4.1.11898.2.1.33.3.0","ORiNOCO-MIB"));
	
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$save() throws InstanceNotFoundException {
		
		newMibObj.setMibObjectName(name.getValue());
		newMibObj.setDescription(description.getValue());
		newMibObj.setOid(oid.getValue());
		newMibObj.setMib(mib.getValue());
		
		current = productService.createMibObject(newMibObj);
		
		for (Product p : (List<Product>) mibObjDualLb.getChosenDataList()) {
			productService.assignMibObjectToProduct(newMibObj.getMibObjectId(), p.getProductId());
		}

		goToList();
	}
	
	public void onClick$cancel() {
		goToList();
	}
	
	private void goToAddForm() {
		newMibObj = new MibObject();
		
		mibObjectList.setVisible(false);
		mibObjectForm.setVisible(true);
	}
	
	private void goToEditForm() {
		newMibObj = current;
		List<Product> chosen = new ArrayList<Product>();
		for (Product p : productService.findProductsByMibObjectId(current.getMibObjectId())) {
			chosen.add(p);
		}
		List<Product> candidate = productService.findAllProducts();
		mibObjDualLb.setModel(candidate, chosen);
		
		name.setValue(current.getMibObjectName());
		description.setValue(current.getDescription());
		oid.setValue(current.getOid());
		mib.setValue(current.getMib());
		
		mibObjectList.setVisible(false);
		mibObjectForm.setVisible(true);
		
	}
	
	private void goToList() {
		name.setValue(null);
		description.setValue(null);
		oid.setValue(null);
		mib.setValue(null);
		
		mibObjectList.setVisible(true);
		mibObjectForm.setVisible(false);
	}
	
	public void onClick$delMibObject() {
		try {
			if (current.getMibObjectId() != null) {
			
				productService.removeMibObject(current.getMibObjectId());
				current.setMibObjectId(null);
			}
			else {
				alert("Selecciona el objeto MIB que quieres eliminar");
			}
		} catch (InstanceNotFoundException e) {
			alert("No se ha encontrado el objeto MIB seleccionado en la BD");
		}
	}
	
	public void onClick$editMibObject() {
		
		if (current.getMibObjectId() != null) {
			goToEditForm();
		}
		else {
			alert("Selecciona el objeto MIB que desea editar");
		}
	}
	
}

