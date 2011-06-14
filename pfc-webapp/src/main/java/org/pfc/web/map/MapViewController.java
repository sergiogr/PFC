package org.pfc.web.map;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.mibobject.MibObject;
import org.pfc.snmp.SnmpService;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.MapModelList;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

@SuppressWarnings("serial")
public class MapViewController extends GenericForwardComposer {
	
	
	private Gmaps map;
	private MapModelList mapModelList;
	private Label deviceNameLbl;
	private Label descriptionLbl;
	private Label ipAddressLbl;
	private Label latitudeLbl;
	private Label longitudeLbl;
	private Grid snmpGrid;
	private Rows snmpRows;
	
	private IDeviceService deviceService; 
	
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		List<Device> devices = deviceService.findAllDevice();
		mapModelList = new MapModelList();

		for (Device d:devices) {
			Gmarker marker = new Gmarker(d.getDeviceName().toString(),d.getPosition().getX(), d.getPosition().getY());
			marker.setOpen(false);
			marker.setDraggingEnabled(false);
			mapModelList.add(marker);
			
		}
		map.setDoubleClickZoom(true);
		map.setScrollWheelZoom(true);
		map.setModel(mapModelList);
		map.setCenter(43.354891546397745,-8.416385650634766);
	}
	
	public void onMapClick(ForwardEvent e) {
	
		MapMouseEvent mme = (MapMouseEvent) e.getOrigin();
		if (mme.getGmarker() != null) {

			Gmarker marker = mme.getGmarker();

			Device dev = deviceService.findDeviceByName(marker.getContent());
			List<MibObject> mos = dev.getProduct().getMibObjects();
			deviceNameLbl.setValue(dev.getDeviceName());
			descriptionLbl.setValue(dev.getDescription());
			ipAddressLbl.setValue(dev.getIpAddress());
			latitudeLbl.setValue(((Double) dev.getPosition().getX()).toString());
			longitudeLbl.setValue(((Double) dev.getPosition().getY()).toString());
			SnmpService snmp = new SnmpService();

			if (dev.getProduct() == null) {
			
			} else if (dev.getProduct().getMibObjects() == null){
				
			} else {
				
				snmpGrid.getRows().getChildren().clear();

				for (MibObject m:mos){
					Row row = new Row();
					row.setParent(snmpRows);
					new Label(m.getMibObjectName()).setParent(row);
					new Label(snmp.snmpGet(dev.getPublicCommunity(), dev.getIpAddress(), dev.getSnmpPort(), m.getOid())).setParent(row);
				}
			}
			
			marker.setOpen(true);	
		}
	}

}
