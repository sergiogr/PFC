package org.pfc.web.mibobject;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.pfc.business.webservice.ProductDTO;
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
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@SuppressWarnings("serial")
public class MibObjectCRUDController extends GenericForwardComposer {

	/**
	 * 
	 */
	private enum Action {CREATE, EDIT};

	private Action action;
	
	private Listbox mibObjectList;
	private Grid mibObjectForm;
	private Textbox name;
	private Textbox description;
	private Textbox oid;
	private Textbox mib;
	private DualListbox mibObjDualLb;
		
	private MibObjectDTO current = new MibObjectDTO();
	private MibObjectDTO newMibObj;
	
	@Autowired
	private IProductWebService productWSClient;

	public MibObjectDTO getCurrent() {
		return current;
	}
	
	public void setCurrent(MibObjectDTO current) {
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
		mibObjDualLb.setModel(new ListModelList(productWSClient.findAllProducts().getProductDTOs()));
		mibObjDualLb.setRenderer(new ProductDualListitemRenderer());
		mibObjectForm.setVisible(false);
	}
	
	public List<MibObjectDTO> getMibObjects() {
		return productWSClient.findAllMibObjects().getMibObjectDTOs();
	}
	
	public void onClick$addMibObject() {
		goToAddForm();
	}
	
	public void onClick$addTestData() {
		productWSClient.createMibObject(new MibObjectDTO(null,"sysDesc", "Descripción del equipo", "1.3.6.1.2.1.1.1.0", "MIB-II"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"sysUpTime", "Tiempo desde la última vez que el equipo fue reiniciado", "1.3.6.1.2.1.1.3.0", "MIB-II"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"sysContact", "Información de contacto de la persona que gestiona el eqipo", "1.3.6.1.2.1.1.4.0", "MIB-II"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"sysName", "Nombre del sistema", "1.3.6.1.2.1.1.5.0", "MIB-II"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"sysLocation", "Localización del sistema", "1.3.6.1.2.1.1.6.0", "MIB-II"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"sysServices", "Conjunto de servicios que ofrece el equipo", "1.3.6.1.2.1.1.7.0", "MIB-II"), null);
		
		productWSClient.createMibObject(new MibObjectDTO(null,"channel","Canal en el que está emitiendo el equipo WiMax","1.3.6.1.4.1.11898.2.1.2.1.1.1.6.3","ORiNOCO-MIB"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"nClients","Número de clientes conectados al equipo WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.1.3","ORiNOCO-MIB"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"localSignal","Nivel de señal local del enlace WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.2.3","ORiNOCO-MIB"), null);
		productWSClient.createMibObject(new MibObjectDTO(null,"remoteSignal","Nivel de señal remota del enlace WiMax","1.3.6.1.4.1.11898.2.1.2.5.2.1.4.3","ORiNOCO-MIB"), null);

		productWSClient.createMibObject(new MibObjectDTO(null,"nClientsWifi","Número de clientes conectados a un AP Wifi","1.3.6.1.4.1.11898.2.1.33.3.0","ORiNOCO-MIB"), null);
	
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$save() throws InstanceNotFoundException {
		
		newMibObj.setMibObjectName(name.getValue());
		newMibObj.setDescription(description.getValue());
		newMibObj.setOid(oid.getValue());
		newMibObj.setMib(mib.getValue());
		
		if (this.getAction() == Action.CREATE) {
			productWSClient.createMibObject(newMibObj, mibObjDualLb.getChosenDataList());
		}
		else if (this.getAction() == Action.EDIT) {
			productWSClient.updateMibObject(newMibObj, mibObjDualLb.getChosenDataList());
		}
//		current = productWebService.createMibObject(new MibObjectDTO(null,name.getValue(),description.getValue(),
//				oid.getValue(),mib.getValue()));
//		
//		for (ProductDTO p : (List<ProductDTO>) mibObjDualLb.getChosenDataList()) {
//			productWebService.assignMibObjectToProduct(newMibObj.getMibObjectId(), p.getProductId());
//		}

		goToList();
	}
	
	public void onClick$cancel() {
		goToList();
	}
	
	private void goToAddForm() {
		newMibObj = new MibObjectDTO();
		this.setAction(Action.CREATE);

		mibObjectList.setVisible(false);
		mibObjectForm.setVisible(true);
	}
	
	private void goToEditForm() {
		newMibObj = current;
		List<ProductDTO> chosen = new ArrayList<ProductDTO>();
		for (ProductDTO p : productWSClient.findProductsByMibObjectId(current.getMibObjectId()).getProductDTOs()) {
			chosen.add(p);
		}
		List<ProductDTO> candidate = productWSClient.findAllProducts().getProductDTOs();
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
			
				productWSClient.removeMibObject(current.getMibObjectId());
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
			this.setAction(Action.EDIT);
			goToEditForm();
		}
		else {
			alert("Selecciona el objeto MIB que desea editar");
		}
	}
	
}

