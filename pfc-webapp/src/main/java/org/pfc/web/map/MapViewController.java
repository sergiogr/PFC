package org.pfc.web.map;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.DataDTO;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDataWebService;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.MapModelList;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

@SuppressWarnings("serial")
public class MapViewController extends GenericForwardComposer {
	
	public static Double centerLat = 43.354891546397745;
	public static Double centerLng = -8.416385650634766;
	
	private Gmaps map;
//	private MapModelList mapModelList;
	private Label deviceNameLbl;
	private Label descriptionLbl;
	private Label productLbl;
	private Label ipAddressLbl;
	private Label portLbl;
	private Label latitudeLbl;
	private Label longitudeLbl;
	private Grid dataGrid;
	private Rows dataRows;
	private Listbox devicesLb;
	
	@Autowired
	private IDeviceWebService deviceWSClient; 
		
	@Autowired
	private IProductWebService productWSClient;
	
	@Autowired
	private IDataWebService dataWSClient;
	
	private List<DeviceDTO> deviceModel = new ArrayList<DeviceDTO>();
	
	private MapModelList mapModel = new MapModelList();
	
//	public List<DeviceDTO> getDevices() {
//		return deviceWSClient.findDevicesByArea(map.getSwLat(), map.getSwLng(), 
//				map.getNeLat(), map.getNeLng()).getDeviceDTOs();
//	}

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		comp.setAttribute(comp.getId(), this, true);

		map.setDoubleClickZoom(true);
		map.setScrollWheelZoom(true);
		map.setCenter(centerLat, centerLng);
		map.setZoom(8);
		map.setModel(mapModel);

		dataGrid.setEmptyMessage("Select a device to check its data...");
		dataGrid.setAutopaging(true);
		
		deviceModel.clear();
		deviceModel.addAll(deviceWSClient.findDevicesByArea(this.map.getSwLat(), this.map.getSwLng(), 
				this.map.getNeLat(), this.map.getNeLng()).getDeviceDTOs());
		System.out.println(this.map.getSwLat()+","+this.map.getSwLng()+" ** DEVICES: "+deviceModel.size());

		for (DeviceDTO d: deviceModel) {
			Gmarker marker = new Gmarker(d.getDeviceName().toString(),d.getLat(), d.getLng());
			marker.setOpen(false);
			if (InetAddress.getByName(d.getIpAddress()).isReachable(1000)) {
				marker.setIconImage("/common/img/wifi.png");
			}
			else {
				marker.setIconImage("/common/img/wifi_red.png");

			}
			marker.setDraggingEnabled(false);
			mapModel.add(marker);
			
		}
		
	}

	
	public List<DeviceDTO> getDeviceModel() {
		return deviceModel;
	}
	
	public MapModelList getMapModel() {
		return mapModel;
	}
	
	public void onMapMove(ForwardEvent e){

		deviceModel.clear();
		mapModel.clear();

		deviceModel.addAll(deviceWSClient.findDevicesByArea(map.getSwLat(), map.getSwLng(), 
				map.getNeLat(), map.getNeLng()).getDeviceDTOs());
		System.out.println(map.getSwLat()+","+map.getSwLng()+" ** DEVICES: "+deviceModel.size());

		for (DeviceDTO d : deviceModel) {
			Gmarker marker = new Gmarker(d.getDeviceName().toString(),d.getLat(), d.getLng());
			marker.setOpen(false);
			marker.setDraggingEnabled(false);
			try {
				if (InetAddress.getByName(d.getIpAddress()).isReachable(1000)) {
					marker.setIconImage("/common/img/wifi.png");
				}
				else {
					marker.setIconImage("/common/img/wifi_red.png");

				}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mapModel.add(marker);
			System.out.println(d.getDeviceId()+" "+d.getDeviceName());
			
		}
	}
	
	public void onMapClick(ForwardEvent e) {

		MapMouseEvent mme = (MapMouseEvent) e.getOrigin();
		
		if (mme.getGmarker() != null) {

			Gmarker marker = mme.getGmarker();
			
			DeviceDTO dev;
			try {
				dev = deviceWSClient.findDeviceByName(marker.getContent());
				deviceNameLbl.setValue(dev.getDeviceName());
				descriptionLbl.setValue(dev.getDescription());
				if (dev.getProductId() != null) {
					productLbl.setValue(productWSClient.findProduct(dev.getProductId()).getProductName());	
				}
				else {
					productLbl.setValue("No product associated");
				}				ipAddressLbl.setValue(dev.getIpAddress());
				portLbl.setValue(dev.getSnmpPort());
				latitudeLbl.setValue(((Double) dev.getLat()).toString());
				longitudeLbl.setValue(((Double) dev.getLng()).toString());
				dataGrid.setEmptyMessage("Device "+dev.getDeviceName()+" is not being monitored.");
				dataGrid.getRows().getChildren().clear();

				
				List<MibObjectDTO> mos = productWSClient.findMibObjectsByProductId(dev.getProductId()).getMibObjectDTOs();
				
				for (MibObjectDTO mo : mos){
					Row row = new Row();
					row.setParent(dataRows);
					new Label(mo.getMibObjectName()).setParent(row);
					DataDTO data = dataWSClient.getMostRecentValue(dev.getDeviceId(), mo.getMibObjectId());
					if (data != null) {
						new Label(data.getValue()).setParent(row);	
						new Label(data.getDate().getTime().toString()).setParent(row);		
						
					}
					else {
						new Label("No data found").setParent(row);	
						new Label("-").setParent(row);	
					}

				}

			} catch (InstanceNotFoundException e1) {
				alert(marker.getContent() + ": This device wasn't found in the DB.");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			marker.setOpen(true);	
		}			
	}
	
	public void onSelect$devicesLb() {
		DeviceDTO dev = (DeviceDTO) devicesLb.getSelectedItem().getValue();
		try {		
			deviceNameLbl.setValue(dev.getDeviceName());
			descriptionLbl.setValue(dev.getDescription());
			if (dev.getProductId() != null) {
				productLbl.setValue(productWSClient.findProduct(dev.getProductId()).getProductName());	
			}
			else {
				productLbl.setValue("No product associated");
			}
			ipAddressLbl.setValue(dev.getIpAddress());
			portLbl.setValue(dev.getSnmpPort());		
			latitudeLbl.setValue(((Double) dev.getLat()).toString());
			longitudeLbl.setValue(((Double) dev.getLng()).toString());
			dataGrid.setEmptyMessage("Device "+dev.getDeviceName()+" is not being monitored.");
			dataGrid.getRows().getChildren().clear();
		
			List<MibObjectDTO> mos = productWSClient.findMibObjectsByProductId(dev.getProductId()).getMibObjectDTOs();
			
			for (MibObjectDTO mo : mos){
				Row row = new Row();
				row.setParent(dataRows);
				new Label(mo.getMibObjectName()).setParent(row);
				DataDTO data = dataWSClient.getMostRecentValue(dev.getDeviceId(), mo.getMibObjectId());
				if (data != null) {
					new Label(data.getValue()).setParent(row);	
					new Label(data.getDate().getTime().toString()).setParent(row);		
					
				}
				else {
					new Label("No data found").setParent(row);	
					new Label("-").setParent(row);	
				}
				
			}
		
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
