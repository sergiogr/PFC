package org.pfc.web.device;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

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
	
	private List<Device> model = new ArrayList<Device>();
	private List<Product> productModel = new ArrayList<Product>();
	
	private Device selected;
	private Device deviceBk = new Device();
	
	@Autowired
	private IDeviceService deviceService; 
	
	@Autowired
	private IProductService productService;
		
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId(), this, true);
		
		model.addAll(deviceService.findAllDevice());
		productModel.add(null);
		productModel.addAll(productService.findAllProducts());
		
	}
	
	public List<Device> getModel() {
		return model;
	}
	
	public List<Product> getProductModel() {
		return productModel;
	}
	
	public Device getSelected() {
		return selected;
	}
	
	public void setSelected(Device selected) {
		this.selected = selected;
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
		restoreDeviceGrid();
		
		this.setAction(Action.CREATE);
		
		gmap.setCenter(centerLat, centerLng);
		marker.setLat(gmap.getLat());
		marker.setLng(gmap.getLng());
		latitudeDb.setValue(gmap.getLat());
		longitudeDb.setValue(gmap.getLng());
		
		goToDeviceForm();
	}
	
	public void onClick$editDeviceBtn() {
		
		if (selected == null) {
			alert("Please, select the device you want to edit.");
		}
		else {
			this.setAction(Action.EDIT);
			backupOrRestoreDevice(selected, deviceBk);
			goToDeviceForm();
			
		}
	}
	
	public void onClick$deleteDeviceBtn() {
		
		if (selected == null) {
			alert("Please, select the device you want to delete.");
		}
		else {
			try {
				deviceService.removeDevice(selected.getDeviceId());
				model.remove(selected);
				restoreDeviceGrid();
				
			} catch (InstanceNotFoundException e) {
				alert("This device was not found in the DB.");
			}
		}
	}
	
	public void onClick$addTestData() throws DuplicateInstanceException {
		GeometryFactory geom = new GeometryFactory();
		
        Point pos1 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
        Point pos2 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
        Point pos3 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
        Point pos4 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
        Point pos5 = geom.createPoint(new Coordinate(43.354891546397745,-8.416385650634766));
               
		deviceService.createDevice(new Device("AP1", "Punto de acceso 1", "127.0.0.1","public","161",pos1));
		deviceService.createDevice(new Device("AP2", "Punto de acceso 2", "127.0.0.1","public","161",pos2));
		deviceService.createDevice(new Device("AP3", "Punto de acceso 3", "127.0.0.1","public","161",pos3));
		deviceService.createDevice(new Device("AP4", "Punto de acceso 4", "127.0.0.1","public","161",pos4));
		deviceService.createDevice(new Device("AP5", "Punto de acceso 5", "127.0.0.1","public","161",pos5));

		model.addAll(deviceService.findAllDevice());
	}

	public void onClick$saveBtn() throws InstanceNotFoundException {
		GeometryFactory geom = new GeometryFactory();
		Point position = geom.createPoint(new Coordinate(latitudeDb.getValue(), longitudeDb.getValue()));
		
		if (this.getAction() == Action.CREATE) {
			try {
				selected =  new Device(deviceNameTb.getValue(),descriptionTb.getValue(),
						ipAddressTb.getValue(),pubCommunityTb.getValue(), snmpPortTb.getValue(), 
						position);
				if (productLb.getSelectedItem() != null) {
						selected.setProduct((Product) productLb.getSelectedItem().getValue());
					}
				deviceService.createDevice(selected);
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
					selected.setProduct((Product) productLb.getSelectedItem().getValue());
				}
				
				selected.setPublicCommunity(pubCommunityTb.getValue());
				selected.setSnmpPort(snmpPortTb.getValue());
				selected.setPosition(position);
				deviceService.updateDevice(selected);
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
		}
		else if (this.getAction() == Action.EDIT) {
	
			backupOrRestoreDevice(deviceBk, selected);
			selected=null;
		}
		
		restoreDeviceGrid();
		goToDeviceLb();

	}
	
	public void onMapDrop$gmap(MapDropEvent event) throws InterruptedException {
		latitudeDb.setValue(event.getLat());
		longitudeDb.setValue(event.getLng());		 	 
    }
	
	public void onSelect$deviceLb() throws InstanceNotFoundException {
		Product product = null;
		if (selected.getProduct() != null) {
			product = productService.findProduct(selected.getProduct().getProductId());
			System.out.println(product.getProductName());
		}
		selected.setProduct(product);
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
	}
	
	private void goToDeviceForm() {
		deviceLb.setVisible(false);
		deviceForm.setVisible(true);
	}
	
	private void goToDeviceLb() {
		deviceForm.setVisible(false);
		deviceLb.setVisible(true);
	}
	
	private void backupOrRestoreDevice(Device device, Device backup) {
		backup.setDeviceName(device.getDeviceName());
		backup.setDescription(device.getDescription());
		backup.setIpAddress(device.getIpAddress());
		backup.setPosition(device.getPosition());
		backup.setPublicCommunity(device.getPublicCommunity());
		backup.setSnmpPort(device.getSnmpPort());
		backup.setProduct(device.getProduct());
	}
		
}
