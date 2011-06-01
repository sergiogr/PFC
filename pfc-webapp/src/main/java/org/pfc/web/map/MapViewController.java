package org.pfc.web.map;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.MapModelList;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

@SuppressWarnings("serial")
public class MapViewController extends GenericForwardComposer {
	
	
	private Gmaps map;
	private MapModelList mapModelList;
	private Textbox deviceNameTb;
	private Doublebox latitudeDb;
	private Doublebox longitudeDb;
	
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

			deviceNameTb.setValue(marker.getContent());
			latitudeDb.setValue(marker.getLat());
			longitudeDb.setValue(marker.getLng());
//			System.out.println(marker.getContent()+","+marker.getLat()+", "+marker.getLng());
			marker.setOpen(true);
		}
	}

}
