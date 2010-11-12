package org.pfc.web;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import org.pfc.snmp.SnmpService;

public class DeviceController extends GenericForwardComposer{
	

	private static final long serialVersionUID = -1995761470857496355L;
	
	private Device current = new Device();
	
	private Gmaps map;
	private Textbox name;
	private Textbox description;
	private Textbox ipAddress;
	private Textbox pubCommunity;
	private Textbox snmpPort;
	private Doublebox lat;
	private Doublebox lng;
	private Label snmpGet;
	private Textbox oid;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		renderMap(map);
	}	

	public Device getCurrent() {
		return current;
	}

	@Autowired
	public void setCurrent(Device current) {
		this.current = current;
	}

	private void renderMap(Gmaps m) {
		m.setDoubleClickZoom(true);
		m.setScrollWheelZoom(true);
		m.getChildren().clear();
		List<Device> devices = deviceService.findAllDevice();
		for (Device d:devices){
			m.appendChild(new Gmarker(d.getName(),d.getLat(),d.getLng()));
		}
	}
	
	public List<Device> getAllDevices() {
		return deviceService.findAllDevice();
	}
	private IDeviceService deviceService;
	
	//@Autowired
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	public String snmpQuery() {
	
		SnmpService snmp = new SnmpService();
		return snmp.snmpGet(current.getPublicCommunity(), current.getIpAddress(), current.getSnmpPort(), oid.getValue());
	}
		
	public void onClick$add() {
//		GeometryFactory geom = new GeometryFactory(new PrecisionModel(0.000000005));
//		Point position = geom.createPoint(new Coordinate(43.333333333,-8.43));

		Device newDev = new Device(name.getValue(), description.getValue(), ipAddress.getValue(), pubCommunity.getValue(), 
				snmpPort.getValue(), null, lat.getValue(), lng.getValue());
        deviceService.createDevice(newDev);
        current = newDev;
        name.setValue(null);
        description.setValue(null);
        ipAddress.setValue(null);
        pubCommunity.setValue(null);
        snmpPort.setValue(null);
        renderMap(map);
	}

	public void onClick$delete() {
		
		try {
			deviceService.removeDevice(current.getId());
			renderMap(map);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick$update() {
		snmpGet.setValue("SNMP request to: "+current.getName()+" - Response: "+ this.snmpQuery());
	}
	

}