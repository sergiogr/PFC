package org.pfc.web.product;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.pfc.business.webservice.ProductDTO;
import org.pfc.web.widgets.duallistbox.DualListbox;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@SuppressWarnings("serial")
public class ProductCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */

	private enum Action {CREATE, EDIT};

	private Action action;
	private Listbox productList;
	private Grid productForm;
	private Textbox name;
	private Textbox manufacturer;
	private Textbox description;
	private DualListbox dualLBox;
		
	private ProductDTO current = new ProductDTO();
	private ProductDTO newProd;
	
	@Autowired
	private IProductWebService productWSClient;
	
	public ProductDTO getCurrent() {
		return current;
	}
	
	public void setCurrent(ProductDTO current) {
		this.current = current;
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		System.out.println("*** Products size"+productWSClient.findAllProducts().getProductDTOs().size());
		dualLBox.setModel(new ListModelList(productWSClient.findAllMibObjects().getMibObjectDTOs()));
		dualLBox.setRenderer(new MibObjectDualListitemRenderer());
		productForm.setVisible(false);
	}
	
	public List<ProductDTO> getProducts() {
		return productWSClient.findAllProducts().getProductDTOs();
	}
	
	public void onClick$addProduct() {
		this.setAction(Action.CREATE);
		goToAddForm();
	}
	
	public void onClick$addTestData() {
		productWSClient.createProduct(new ProductDTO(null,"MP.11 5054-RS", "Estación base WiMax", "Proxim"),null);	
		productWSClient.createProduct(new ProductDTO(null,"MP.11 5054-R", "Estación base WiMax Tsunami MP.11 5054-R", "Proxim"),null);
		productWSClient.createProduct(new ProductDTO(null,"MP.11 5054-SUI", "Estación suscriptora WiMax Tsunami MP.11 5054-SUI", "Proxim"),null);
		productWSClient.createProduct(new ProductDTO(null,"AP-700", "Punto de acceso Wifi", "Proxim"),null);
		productWSClient.createProduct(new ProductDTO(null,"AP-4000", "Punto de acceso Wifi", "Proxim"),null);
		productWSClient.createProduct(new ProductDTO(null,"AP-4000MR", "Punto de acceso Wifi Mesh", "Proxim"),null);
	}

	@SuppressWarnings("unchecked")
	public void onClick$save() {
		newProd.setProductName(name.getValue());
		newProd.setManufacturer(manufacturer.getValue());
		newProd.setDescription(description.getValue());
		
		if (this.getAction() == Action.CREATE) {
			productWSClient.createProduct(newProd, dualLBox.getChosenDataList());
		}
		else if (this.getAction() == Action.EDIT) {
			try {
				productWSClient.updateProduct(newProd, dualLBox.getChosenDataList());
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
		newProd = new ProductDTO();

		List<MibObjectDTO> candidate = productWSClient.findAllMibObjects().getMibObjectDTOs();
		dualLBox.setModel(candidate, new ArrayList<MibObjectDTO>());
		
		productList.setVisible(false);
		productForm.setVisible(true);
	}
	
	private void goToEditForm() {
		newProd = current;
		List<MibObjectDTO>	chosen = new ArrayList<MibObjectDTO>();
		
		for (MibObjectDTO mo : productWSClient.findMibObjectsByProductId(current.getProductId()).getMibObjectDTOs()) {
			chosen.add(mo);
		}
		List<MibObjectDTO> candidate = productWSClient.findAllMibObjects().getMibObjectDTOs();
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
			
				productWSClient.removeProduct(current.getProductId());
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
			this.setAction(Action.EDIT);
			goToEditForm();
		}
		else {
			alert("Selecciona el producto que desea editar");
		}
	}
	
}
