package org.pfc.web.product;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.web.widgets.duallistbox.DualListbox;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Sergio Garc’a Ramos <sergio.garcia@udc.es>
 *
 */
@SuppressWarnings("serial")
public class ProductCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */

	private Listbox productList;
	private Grid productForm;
	private Textbox name;
	private Textbox manufacturer;
	private Textbox description;
	private DualListbox dualLBox;
		
	private Product current = new Product();
	private Product newProd;
	
	@Autowired
	private IProductService productService;
	
	public Product getCurrent() {
		return current;
	}
	
	public void setCurrent(Product current) {
		this.current = current;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		System.out.println("*** Products size"+productService.findAllProducts().size());
		dualLBox.setModel(new ListModelList(productService.findAllMibObjects()));
		dualLBox.setRenderer(new MibObjectDualListitemRenderer());
		productForm.setVisible(false);
	}
	
	public List<Product> getProducts() {
		return productService.findAllProducts();
	}
	
	public void onClick$addProduct() {
		goToAddForm();
	}
	
	public void onClick$addTestData() {
		productService.createProduct(new Product("MP.11 5054-R", "Estaci—n base WiMax Tsunami MP.11 5054-R", "Proxim"));
		productService.createProduct(new Product("MP.11 5054-SUI", "Estaci—n suscriptora WiMax Tsunami MP.11 5054-SUI", "Proxim"));
		productService.createProduct(new Product("AP-700", "Punto de acceso Wifi", "Proxim"));
		productService.createProduct(new Product("AP-4000", "Punto de acceso Wifi", "Proxim"));
		productService.createProduct(new Product("AP-4000MR", "Punto de acceso Wifi Mesh", "Proxim"));
		
	}

	@SuppressWarnings("unchecked")
	public void onClick$save() {
		newProd.setProductName(name.getValue());
		newProd.setManufacturer(manufacturer.getValue());
		newProd.setDescription(description.getValue());
		//newProd.setMibObjects((Set<MibObject>) dualLBox.getChosenDataList());
		
		productService.createProduct(newProd);
		
		for (MibObject mo: (List<MibObject>) dualLBox.getChosenDataList()) {

			try {
				productService.assignMibObjectToProduct(mo.getMibObjectId(), newProd.getProductId());
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		goToList();
	}
	
	public void onClick$cancel() {
		goToList();
	}
	
	private void goToAddForm() {
		newProd = new Product();
		
		productList.setVisible(false);
		productForm.setVisible(true);
	}
	
	private void goToEditForm() {
		newProd = current;
		List<MibObject>	chosen = new ArrayList<MibObject>();
		
		for (MibObject mo : productService.findMibObjectsByProductId(current.getProductId())) {
			chosen.add(mo);
		}
		List<MibObject> candidate = productService.findAllMibObjects();
		dualLBox.setModel(candidate, chosen);
		
		name.setValue(current.getProductName());
		manufacturer.setValue(current.getManufacturer());
		description.setValue(current.getDescription());
		
		productList.setVisible(false);
		productForm.setVisible(true);
		
	}
	
	private void goToList() {
		name.setValue(null);
		manufacturer.setValue(null);
		description.setValue(null);
		
		productList.setVisible(true);
		productForm.setVisible(false);
	}
	
	public void onClick$delProduct() {
		try {
			if (current.getProductId() != null) {
			
				productService.removeProduct(current.getProductId());
				current.setProductId(null);
			}
			else {
				alert("Selecciona el producto que quieres eliminar");
			}
		} catch (InstanceNotFoundException e) {
			alert("No se ha encontrado el producto seleccionado en la BD");
		}
	}
	
	public void onClick$editProduct() {
		
		if (current.getProductId() != null) {
			goToEditForm();
		}
		else {
			alert("Selecciona el producto que desea editar");
		}
	}
	
}
