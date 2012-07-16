package org.pfc.web.device;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapDropEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;


@SuppressWarnings("serial")
public class DeviceCRUDController extends GenericForwardComposer {

	private enum Action {CREATE, EDIT};
	public static Double centerLat = 43.354891546397745;
	public static Double centerLng = -8.416385650634766;
	
	private Action action;

	private Gmaps gmap;
	private Gmarker marker;
	private Listbox deviceLb;
	private Listbox productLb;
	private Textbox deviceNameTb;
	private Textbox descriptionTb;
	private Textbox ipAddressTb;
	private Textbox pubCommunityTb;
	private Textbox snmpPortTb;
	private Doublebox latitudeDb;
	private Doublebox longitudeDb;
	private Grid deviceForm;
	
	private List<DeviceDTO> model = new ArrayList<DeviceDTO>();
	private List<ProductDTO> productModel = new ArrayList<ProductDTO>();
	
	private DeviceDTO selected;
	private ProductDTO selectedProd;
	private DeviceDTO deviceBk = new DeviceDTO();
	
	@Autowired
	private IDeviceWebService deviceWSClient;
	
	@Autowired
	private IProductWebService productWSClient;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId(), this, true);
		
		model.addAll(deviceWSClient.findAllDevice().getDeviceDTOs());
		productModel.add(null);
		productModel.addAll(productWSClient.findAllProducts().getProductDTOs());
	}
	
	public List<DeviceDTO> getModel() {
		return model;
	}
	
	public List<ProductDTO> getProductModel() {
		return productModel;
	}
	
	public DeviceDTO getSelected() {
		return selected;
	}
	
	public void setSelected(DeviceDTO selected) {
		this.selected = selected;
	}
		
	public ProductDTO getSelectedProd() throws InstanceNotFoundException {
		return selectedProd;		
	}

	public void setSelectedProd(ProductDTO selectedProd) {
		this.selectedProd = selectedProd;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void onClick$addDeviceBtn() {
		
		deviceLb.clearSelection();
		selected = null;
		selectedProd = null;
		restoreDeviceGrid();
		
		this.setAction(Action.CREATE);
		
		gmap.setCenter(centerLat, centerLng);
		marker.setLat(gmap.getLat());
		marker.setLng(gmap.getLng());
		latitudeDb.setValue(gmap.getLat());
		longitudeDb.setValue(gmap.getLng());
		
		goToDeviceForm();
	}
	
	public void onClick$editDeviceBtn() throws InstanceNotFoundException {
		
		if (selected == null) {
			alert("Please, select the device you want to edit.");
		}
		else {
			this.setAction(Action.EDIT);
			backupOrRestoreDevice(selected, deviceBk);
			productLb.clearSelection();

			if (selected.getProductId() != null) {
				selectedProd = productWSClient.findProduct(selected.getProductId());
				System.out.println("PRODUCT: "+selectedProd.getProductName());
				for (int i=0;i< productLb.getModel().getSize();i++) {
					System.out.println("Index: "+i);
					if ((productModel.get(i)!= null) && (selectedProd.equals(productModel.get(i)))){
						System.out.println("Index: "+i+" SELECTED");
						productLb.setSelectedIndex(i);	
					}
				}
			}
			else {
				System.out.println("NO PRODUCT ");
				selectedProd = null;
			}
			System.out.println(selected.getDeviceId()+" - "+selected.getDeviceName());
			goToDeviceForm();
			
		}
	}
	
	public void onClick$deleteDeviceBtn() {
		
		if (selected == null) {
			alert("Please, select the device you want to delete.");
		}
		else {
			try {
				deviceWSClient.removeDevice(selected.getDeviceId());
				model.remove(selected);
				restoreDeviceGrid();
				
			} catch (InstanceNotFoundException e) {
				alert("This device was not found in the DB.");
			}
		}
	}
	
	public void onClick$addTestData() throws DuplicateInstanceException {

//		GeometryFactory geom = new GeometryFactory();
//		
//        Point pos1 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
//        Point pos2 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
//        Point pos3 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
//        Point pos4 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
//        Point pos5 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
//               
//		deviceService.createDevice(new Device("AP1", "Punto de acceso 1", "127.0.0.1","public","161",pos1));
//		deviceService.createDevice(new Device("AP2", "Punto de acceso 2", "127.0.0.1","public","161",pos2));
//		deviceService.createDevice(new Device("AP3", "Punto de acceso 3", "127.0.0.1","public","161",pos3));
//		deviceService.createDevice(new Device("AP4", "Punto de acceso 4", "127.0.0.1","public","161",pos4));
//		deviceService.createDevice(new Device("AP5", "Punto de acceso 5", "127.0.0.1","public","161",pos5));
		deviceWSClient.createDevice(new DeviceDTO(null, "AP1", "Punto de acceso 1", "127.0.0.1","public","161",43.354891546397745,-8.416385650634766, null));
		deviceWSClient.createDevice(new DeviceDTO(null, "AP2", "Punto de acceso 2", "127.0.0.1","public","161",43.354891546397745,-8.416385650634766, null));
		deviceWSClient.createDevice(new DeviceDTO(null, "AP3", "Punto de acceso 3", "127.0.0.1","public","161",43.354891546397745,-8.416385650634766, null));
		deviceWSClient.createDevice(new DeviceDTO(null, "AP4", "Punto de acceso 4", "127.0.0.1","public","161",43.354891546397745,-8.416385650634766, null));
		deviceWSClient.createDevice(new DeviceDTO(null, "AP5", "Punto de acceso 5", "127.0.0.1","public","161",43.354891546397745,-8.416385650634766, null));
		model.addAll(deviceWSClient.findAllDevice().getDeviceDTOs());
	}

	public void onClick$saveBtn() throws InstanceNotFoundException {
//		GeometryFactory geom = new GeometryFactory();
//		Point position = geom.createPoint(new Coordinate(latitudeDb.getValue(), longitudeDb.getValue()));
//		
		if (this.getAction() == Action.CREATE) {
			try {
				selected =  new DeviceDTO(null, deviceNameTb.getValue(),descriptionTb.getValue(),
						ipAddressTb.getValue(),pubCommunityTb.getValue(), snmpPortTb.getValue(), 
						latitudeDb.getValue(), longitudeDb.getValue(), null);
				if (productLb.getSelectedItem() != null) {
						selected.setProductId(((ProductDTO) productLb.getSelectedItem().getValue()).getProductId());
						selectedProd = productWSClient.findProduct(selected.getProductId());
					}
				selected = deviceWSClient.createDevice(selected);
				model.add(selected);
				goToDeviceLb();
			} catch (DuplicateInstanceException e) {
				alert("There is another Device with this name: "+ selected.getDeviceName());
			}
		}
		else if (this.getAction() == Action.EDIT) {

			try {
				selected.setDeviceName(deviceNameTb.getValue());
				selected.setDescription(descriptionTb.getValue());
				selected.setIpAddress(ipAddressTb.getValue());
				
				if (productLb.getSelectedItem() != null){
					selected.setProductId(((ProductDTO) productLb.getSelectedItem().getValue()).getProductId());
					selectedProd = productWSClient.findProduct(selected.getProductId());
				}
				
				selected.setPublicCommunity(pubCommunityTb.getValue());
				selected.setSnmpPort(snmpPortTb.getValue());
				selected.setLat(latitudeDb.getValue());
				selected.setLng(longitudeDb.getValue());
				deviceWSClient.updateDevice(selected);
				goToDeviceLb();
			} catch (DuplicateInstanceException e1) {
				alert("There is another Device with this name:  "+selected.getDeviceName()+". Please, choose another name");
			}		
		}
				
	}

	public void onClick$cancelBtn() {
		deviceLb.clearSelection();
		if (this.getAction() == Action.CREATE) {
			selected=null;
			selectedProd=null;
		}
		else if (this.getAction() == Action.EDIT) {
	
			backupOrRestoreDevice(deviceBk, selected);
			selected=null;
			selectedProd=null;
		}
		
		restoreDeviceGrid();
		goToDeviceLb();

	}
	
	public void onMapDrop$gmap(MapDropEvent event) throws InterruptedException {
		latitudeDb.setValue(event.getLat());
		longitudeDb.setValue(event.getLng());		 	 
    }
	
	public void onSelect$deviceLb() throws InstanceNotFoundException {
		ProductDTO product = null;
		System.out.println("DEVICE: "+selected.getDeviceId()+" - "+selected.getDeviceName());
		if (selected.getProductId() != null) {
			product = productWSClient.findProduct(selected.getProductId());
			System.out.println("PRODUCT: "+product.getProductName());
		}
//		selectedProd = product;
	}
	
	private void restoreDeviceGrid() {
		deviceNameTb.setValue(null);
		descriptionTb.setValue(null);
		productLb.clearSelection();
		ipAddressTb.setValue(null);
		pubCommunityTb.setValue("public");
		snmpPortTb.setValue("161");
		latitudeDb.setValue(null);
		longitudeDb.setValue(null);
//		selectedProd = null;
	}
	
	private void goToDeviceForm() {
		deviceLb.setVisible(false);
		deviceForm.setVisible(true);
	}
	
	private void goToDeviceLb() {
		deviceForm.setVisible(false);
		deviceLb.setVisible(true);
	}
	
	private void backupOrRestoreDevice(DeviceDTO device, DeviceDTO backup) {
		backup.setDeviceId(device.getDeviceId());
		backup.setDeviceName(device.getDeviceName());
		backup.setDescription(device.getDescription());
		backup.setIpAddress(device.getIpAddress());
//		backup.setPosition(device.getPosition());
		backup.setLat(device.getLat());
		backup.setLng(device.getLng());
		backup.setPublicCommunity(device.getPublicCommunity());
		backup.setSnmpPort(device.getSnmpPort());
		backup.setProductId(device.getProductId());
	}
		
}
