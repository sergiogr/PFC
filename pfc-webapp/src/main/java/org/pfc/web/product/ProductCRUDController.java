package org.pfc.web.product;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.web.widgets.duallistbox.DualListbox;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class ProductCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336437974384651519L;

	private Listbox productList;
	private Grid productForm;
	private Textbox name;
	private Textbox manufacturer;
	private Textbox description;
	private DualListbox dualLBox;
	
	private Intbox nMibObjs;
	
	private Product current = new Product();
	private Product newProd;
	
	private IProductService productService;
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

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
		productService.createProduct(new Product("AP-700", "Punto de acceso Wifi", "Proxim"));
		productService.createProduct(new Product("AP-4000", "Punto de acceso Wifi", "Proxim"));
		productService.createProduct(new Product("AP-4000MR", "Punto de acceso Wifi Mesh", "Proxim"));
		
	}

	@SuppressWarnings("unchecked")
	public void onClick$save() {
		newProd.setProductName(name.getValue());
		newProd.setManufacturer(manufacturer.getValue());
		newProd.setDescription(description.getValue());
		newProd.setMibObjects(dualLBox.getChosenDataList());
		
		productService.createProduct(newProd);
		
//		productService.addMibObjectsToProduct(newProd.getProductId(), dualLBox.getChosenDataList());
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
		List<MibObject>	chosen = current.getMibObjects();
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
	
	
	public void onSelect$productList() {
		nMibObjs.setValue(current.getMibObjects().size());

	}
}
