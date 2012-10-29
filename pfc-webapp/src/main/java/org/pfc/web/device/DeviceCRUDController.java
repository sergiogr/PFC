package org.pfc.web.device;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.ProductDTO;
import org.pfc.business.webservice.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapDropEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@SuppressWarnings("serial")
public class DeviceCRUDController extends GenericForwardComposer {

	private enum Action {LIST, CREATE, EDIT};
	public static Double centerLat = 43.354891546397745;
	public static Double centerLng = -8.416385650634766;
	
	private Action action;

	private Gmaps gmap;
	private Gmarker marker;
	private Listbox deviceLb;
	private Listbox productLb;
	private Listbox projectLb;
	private Label projectName;
	private Textbox deviceNameTb;
	private Textbox descriptionTb;
	private Textbox ipAddressTb;
	private Textbox pubCommunityTb;
	private Textbox snmpPortTb;
	private Doublebox latitudeDb;
	private Doublebox longitudeDb;
	private Grid deviceForm;
	
	private List<ProductDTO> productModel = new ArrayList<ProductDTO>();
	private List<ProjectDTO> projectModel = new ArrayList<ProjectDTO>();
	
	private DeviceDTO selected;
	private ProductDTO selectedProd;
	private ProjectDTO selectedProj;
		
	@Autowired
	private IDeviceWebService deviceWSClient;
	
	@Autowired
	private IProductWebService productWSClient;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId(), this, true);
		marker.setIconImage("/common/img/wifi_blue.png");

		productModel.add(0,null);
		productModel.addAll(productWSClient.findAllProducts().getProductDTOs());
		projectModel.add(0,null);
		projectModel.addAll(deviceWSClient.findAllProjects().getProjectDTOs());

		this.setAction(Action.LIST);
	}
	
	public List<DeviceDTO> getDevices() {
		return deviceWSClient.findAllDevice().getDeviceDTOs();
	}
	
	public List<ProductDTO> getProductModel() {
		return productModel;
	}

	public List<ProjectDTO> getProjectModel() {
		return projectModel;
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

	public ProjectDTO getSelectedProj() {
		return selectedProj;
	}

	public void setSelectedProj(ProjectDTO selectedProj) {
		this.selectedProj = selectedProj;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void onClick$addDeviceBtn() {
		
		this.setAction(Action.CREATE);

		deviceLb.clearSelection();

		projectLb.setSelectedIndex(0);
		productLb.setSelectedIndex(0);
		
		projectName.setValue("None");

		deviceNameTb.setValue(null);
		descriptionTb.setValue(null);
		ipAddressTb.setValue(null);
		pubCommunityTb.setValue("public");
		snmpPortTb.setValue("161");
		
		gmap.setDoubleClickZoom(true);
		gmap.setScrollWheelZoom(true);
		gmap.setCenter(centerLat, centerLng);
		marker.setLat(centerLat);
		marker.setLng(centerLng);
		latitudeDb.setValue(centerLat);
		longitudeDb.setValue(centerLng);
		
		goToDeviceForm();
	}
	
	public void onClick$editDeviceBtn() throws InstanceNotFoundException {
		
		if (selected == null) {
			alert("Please, select the device you want to edit.");
		}
		else {
			this.setAction(Action.EDIT);
			productLb.clearSelection();
			projectName.setValue("None");
			deviceNameTb.setValue(selected.getDeviceName());
			descriptionTb.setValue(selected.getDescription());
			ipAddressTb.setValue(selected.getIpAddress());
			pubCommunityTb.setValue(selected.getPublicCommunity());
			snmpPortTb.setValue(selected.getSnmpPort());
			gmap.setCenter(selected.getLat(), selected.getLng());
			marker.setLat(selected.getLat());
			marker.setLng(selected.getLng());
			latitudeDb.setValue(selected.getLat());
			longitudeDb.setValue(selected.getLng());

			projectLb.setSelectedIndex(0);
			productLb.setSelectedIndex(0);
			
			if (selected.getProductId() != null) {
				this.setSelectedProd(productWSClient.findProduct(selected.getProductId()));
				System.out.println("PRODUCT: "+selectedProd.getProductName());
				for (int i=0;i< productLb.getModel().getSize();i++) {
					System.out.println("Index: "+i+" - "+productModel.get(i));
					if ((productModel.get(i)!= null) && (selectedProd.equals(productModel.get(i)))){
						System.out.println("Index: "+i+" SELECTED");
						productLb.setSelectedIndex(i);	
						break;
					}
				}
			}
			
			if (selected.getProjectId() != null) {
				this.setSelectedProj(deviceWSClient.findProject(selected.getProjectId()));
				System.out.println("Project: "+selectedProj.getProjectName());
				for (int i=0;i< projectLb.getModel().getSize();i++) {
					System.out.println("Index: "+i+" - "+projectModel.get(i));
					if ((projectModel.get(i)!= null) && (selectedProj.equals(projectModel.get(i)))){
						System.out.println("Index: "+i+" SELECTED");
						projectLb.setSelectedIndex(i);	
						projectName.setValue(selectedProj.getProjectName());
						break;
					}
				}			
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
				
			} catch (InstanceNotFoundException e) {
				alert("This device was not found in the DB.");
			}
		}
	}
	
	public void onClick$addTestData() {

		try {
			int n = 5;
			for (int i=1; i<=n; i++) {
				double x = centerLat - Math.random() % 0.5;
				double y = centerLng + Math.random() % 0.5;

				DeviceDTO d = new DeviceDTO(null,"AP"+i,"AP de prueba "+i,"127.0.0.1","public","161",x,y);
				deviceWSClient.createDevice(d);
			}
		} catch (DuplicateInstanceException e) {
			alert("This device already exists.");
		}
		
	}

	public void onClick$saveBtn() throws InstanceNotFoundException {

		if (this.getAction() == Action.CREATE) {
			try {
				selected =  new DeviceDTO(null, deviceNameTb.getValue(),descriptionTb.getValue(),
					ipAddressTb.getValue(),pubCommunityTb.getValue(), snmpPortTb.getValue(), 
					latitudeDb.getValue(), longitudeDb.getValue());
					
				if (productLb.getSelectedItem().getValue() != null) {
					selected.setProductId(((ProductDTO) productLb.getSelectedItem().getValue()).getProductId());
					selectedProd = productWSClient.findProduct(selected.getProductId());
				}
				
				selected = deviceWSClient.createDevice(selected);
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
				
				if (productLb.getSelectedItem().getValue() != null){
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
		ProjectDTO project = projectModel.get(projectLb.getSelectedIndex());
		if (project != null) {
			deviceWSClient.addDeviceToProject(selected, project);
		}
		else {
			deviceWSClient.delDeviceFromProject(selected);
		}
				
	}

	public void onClick$cancelBtn() {
		deviceLb.clearSelection();
		projectLb.clearSelection();
		productLb.clearSelection();
		
		this.setAction(Action.LIST);
		goToDeviceLb();

	}
	
	public void onMapDrop$gmap(MapDropEvent event) throws InterruptedException {
		latitudeDb.setValue(event.getLat());
		longitudeDb.setValue(event.getLng());		 	 
    }
	
	public void onChange$latitudeDb() {
		marker.setLat(latitudeDb.getValue().doubleValue());
		gmap.setLat(marker.getLat());
	}
	
	public void onChange$longitudeDb() {
		marker.setLng(longitudeDb.getValue().doubleValue());
		gmap.setLng(marker.getLng());
		
	}
	
	public void onSelect$projectLb() {
		ProjectDTO project = (ProjectDTO) projectLb.getModel().getElementAt(projectLb.getSelectedIndex());
		projectName.setValue("None");
		if (project != null) {
			projectName.setValue(project.getProjectName());	
		}
	}
	
	private void goToDeviceForm() {
		deviceLb.setVisible(false);
		deviceForm.setVisible(true);
		deviceNameTb.setConstraint("no empty,/.+/:You need to set a name for this device");
		descriptionTb.setConstraint("no empty,/.+/:You need to set a description for this device");
		ipAddressTb.setConstraint("no empty,/[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+/:You must put a valid IP address");
		
	}
	
	private void goToDeviceLb() {
		deviceForm.setVisible(false);
		deviceLb.setVisible(true);
		deviceNameTb.setConstraint("");
		descriptionTb.setConstraint("");
		ipAddressTb.setConstraint("");
	}
	
}
