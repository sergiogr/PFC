package org.pfc.web.device;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

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
	
	public void onClick$addDeviceBtn() {
		deviceLb.clearSelection();
		selected = null;
		restoreDeviceGrid();
		
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
	
	public void onClick$saveBtn() throws InstanceNotFoundException {
		GeometryFactory geom = new GeometryFactory();
		Point position = geom.createPoint(new Coordinate(latitudeDb.getValue(), longitudeDb.getValue()));
		if (selected == null) {
			Device newDev =  new Device(deviceNameTb.getValue(),descriptionTb.getValue(),
				ipAddressTb.getValue(),pubCommunityTb.getValue(), snmpPortTb.getValue(), 
				position);
			if (productLb.getSelectedItem() != null) {
				newDev.setProduct((Product) productLb.getSelectedItem().getValue());
			}
			selected = newDev;
			model.add(selected);
		}
		else {
			selected.setDeviceName(deviceNameTb.getValue());
			selected.setDescription(descriptionTb.getValue());
			selected.setIpAddress(ipAddressTb.getValue());
			
			if (productLb.getSelectedItem() != null){
				selected.setProduct((Product) productLb.getSelectedItem().getValue());
			}
			
			selected.setPublicCommunity(pubCommunityTb.getValue());
			selected.setSnmpPort(snmpPortTb.getValue());
			selected.setPosition(position);
		}
		deviceService.createDevice(selected);

		goToDeviceLb();
		
	}

	public void onClick$cancelBtn() {
		deviceLb.clearSelection();
		selected = null;
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
}
