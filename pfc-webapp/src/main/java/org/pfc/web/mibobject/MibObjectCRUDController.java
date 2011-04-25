package org.pfc.web.mibobject;

import java.util.List;

import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.web.product.DualListbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class MibObjectCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336437974384651519L;

	private Listbox mibObjectList;
	private Grid mibObjectForm;
	private Textbox name;
	private Textbox description;
	private Textbox oid;
	private Textbox mib;
	private DualListbox mibObjDualLb;
	
	private Intbox nProducts;
	private Intbox nChosen;
	
	private MibObject current = new MibObject();
	private MibObject newMibObj;
	
	private IProductService productService;
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

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
		nChosen.setValue(mibObjDualLb.getChosenDataList().size());
	}
	
	public List<MibObject> getMibObjects() {
		return productService.findAllMibObjects();
	}
	
	public void onClick$addMibObject() {
		goToAddForm();
	}
	
	public void onClick$addTestData() {
		productService.createMibObject(new MibObject("sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II"));
		productService.createMibObject(new MibObject("sysLocation", "Localización del sistema", "1.3.6.1.2.1.1.6.0", "MIB-II"));
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$save() throws InstanceNotFoundException {
		System.out.println(">>> **N Objects to Add: "+ mibObjDualLb.getChosenDataList().size());
		
		newMibObj.setMibObjectName(name.getValue());
		newMibObj.setDescription(description.getValue());
		newMibObj.setOid(oid.getValue());
		newMibObj.setMib(mib.getValue());
		newMibObj.setProducts(mibObjDualLb.getChosenDataList());
		
		System.out.println(">>> **newMibObj "+newMibObj.getMibObjectId()+" N Products: "+newMibObj.getProducts().size());
		current = productService.createMibObject(newMibObj);
		
		System.out.println(">>> **newMibObj "+current.getMibObjectId()+" N Products after Add: "+current.getProducts().size());
		System.out.println(">>> **newMibObj "+current.getMibObjectId()+" N Products on DB: "+productService.findMibObject(current.getMibObjectId()).getProducts().size());
//		System.out.println("MibObjId: "+newMibObj.getMibObjectId());
//		System.out.println("Products list size: "+newMibObj.getProducts().size());
//		productService.addProductsToMibObject(newMibObj.getMibObjectId(), mibObjDualLb.getChosenDataList());
//		System.out.println("Products list size added: "+newMibObj.getProducts().size());

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
		List<Product> chosen = current.getProducts();
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
	
	public void onSelect$mibObjectList() {
		nProducts.setValue(current.getProducts().size());
	}
	
}

