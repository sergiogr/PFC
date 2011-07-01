package org.pfc.web.device;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

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

	public enum Action {CREATE, EDIT};
	
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
	
	private IDeviceService deviceService; 
	
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}	
	
	private IProductService productService;
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
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
		
		gmap.setCenter(43.354891546397745,-8.416385650634766);
		marker.setLat(gmap.getLat());
		marker.setLng(gmap.getLng());
		latitudeDb.setValue(gmap.getLat());
		longitudeDb.setValue(gmap.getLng());
		
		goToDeviceForm();
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
	
	public void onClick$editDeviceBtn() {
		
		if (selected == null) {
			alert("Please, select the device you want to edit.");
		}
		else {
			this.setAction(Action.EDIT);
			deviceBk.setDeviceName(selected.getDeviceName());
			deviceBk.setDescription(selected.getDescription());
			deviceBk.setIpAddress(selected.getIpAddress());
			deviceBk.setPublicCommunity(selected.getPublicCommunity());
			deviceBk.setSnmpPort(selected.getSnmpPort());
			deviceBk.setProduct(selected.getProduct());
			deviceBk.setPosition(selected.getPosition());
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
        Point pos1 = geom.createPoint(new Coordinate(0, 0));
        Point pos2 = geom.createPoint(new Coordinate(0, 0));
        Point pos3 = geom.createPoint(new Coordinate(0, 0));
        Point pos4 = geom.createPoint(new Coordinate(0, 0));
        Point pos5 = geom.createPoint(new Coordinate(0, 0));
        Point pos6 = geom.createPoint(new Coordinate(0, 0));
        Point pos7 = geom.createPoint(new Coordinate(0, 0));
        Point pos8 = geom.createPoint(new Coordinate(0, 0));
        Point pos9 = geom.createPoint(new Coordinate(0, 0));
        Point pos10 = geom.createPoint(new Coordinate(0, 0));
        
		deviceService.createDevice(new Device("Base1", "Estaci—n base 1", "1.1.1.20","public","161",pos1));
		deviceService.createDevice(new Device("Base2", "Estaci—n base 2", "1.1.1.22","public","161",pos2));
		deviceService.createDevice(new Device("Base3", "Estaci—n base 3", "1.1.1.25","public","161",pos3));
		deviceService.createDevice(new Device("SU1", "Estaci—n suscriptora 1", "1.1.1.21","public","161",pos4));
		deviceService.createDevice(new Device("SU2", "Estaci—n suscriptora 2", "1.1.1.23","public","161",pos5));
		deviceService.createDevice(new Device("SU3", "Estaci—n suscriptora 3", "1.1.1.24","public","161",pos6));
		deviceService.createDevice(new Device("SU4", "Estaci—n suscriptora 4", "1.1.1.26","public","161",pos7));

		deviceService.createDevice(new Device("AP1", "AP wifi 1", "1.1.1.40","public","161",pos8));
		deviceService.createDevice(new Device("AP2", "AP wifi 2", "1.1.1.41","public","161",pos9));
		deviceService.createDevice(new Device("AP3", "AP wifi 3", "1.1.1.42","public","161",pos10));		
		
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
			System.out.println(deviceBk.getDeviceName());
			System.out.println(selected.getDeviceName());
			selected.setDeviceName(deviceBk.getDeviceName());
			selected.setDescription(deviceBk.getDescription());
			selected.setIpAddress(deviceBk.getIpAddress());
			selected.setPosition(deviceBk.getPosition());
			selected.setPublicCommunity(deviceBk.getPublicCommunity());
			selected.setSnmpPort(deviceBk.getSnmpPort());
			selected.setProduct(deviceBk.getProduct());
			selected=null;
		}
		
		restoreDeviceGrid();
		
		goToDeviceLb();

	}
	
	private void goToDeviceForm() {
		deviceLb.setVisible(false);
		deviceForm.setVisible(true);
	}
	
	private void goToDeviceLb() {
		deviceForm.setVisible(false);
		deviceLb.setVisible(true);
	}
	
	 public void onMapDrop$gmap(MapDropEvent event) throws InterruptedException {
		 latitudeDb.setValue(event.getLat());
		 longitudeDb.setValue(event.getLng());
		 	 
    }
}
