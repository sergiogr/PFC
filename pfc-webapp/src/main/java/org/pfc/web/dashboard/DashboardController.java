package org.pfc.web.dashboard;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.EventDTO;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IEventWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.MapModelList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Label;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

@SuppressWarnings("serial")
public class DashboardController extends GenericForwardComposer {

	public static Double centerLat = 43.354891546397745;
	public static Double centerLng = -8.416385650634766;
	
	@Autowired
	private IEventWebService eventWSClient;
	
	@Autowired
	private IDeviceWebService deviceWSClient;
	
	private Gmaps map;
	private Label devTotal;
	private Label devUp;
	private Label devDown;
//	private Listbox eventLb;
	private List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
	private MapModelList mapModel = new MapModelList();
	private Flashchart mychart;
	private Flashchart mychart2;
	private Flashchart mybarchart;
	
	public List<EventDTO> getEvents() {
		return eventWSClient.findAllEvents().getEventDTOs();
	}
	
	public void doAfterCompose(Component comp) throws Exception {

		int up = 0;
		int down = 0;
		
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId(), this, true);
		devices.addAll(deviceWSClient.findAllDevice().getDeviceDTOs());
		
		map.setDoubleClickZoom(true);
		map.setScrollWheelZoom(true);
		map.setCenter(centerLat, centerLng);
		map.setZoom(5);
        PieModel model = new SimplePieModel();
		for(int j=0; j < 4; ++j) {
			 model.setValue("c"+j, new Double(j));
		}
        mychart.setModel(model);
        PieModel model2 = new SimplePieModel();
		for(int j=0; j < 4; ++j) {
			 model2.setValue("c"+j, new Double(j));
		}
		mychart2.setModel(model2);
		mychart2.setChartStyle("legend-display=bottom");
        int year = new java.util.Date().getYear() + 1900;
		CategoryModel categorymodel = new SimpleCategoryModel();
        categorymodel.setValue(year - 2 + "", "Q1", new Integer(17));
        categorymodel.setValue(year - 2 + "", "Q2", new Integer(36));
        categorymodel.setValue(year - 2 + "", "Q3", new Integer(39));
        categorymodel.setValue(year - 2 + "", "Q4", new Integer(49));
        categorymodel.setValue(year - 1 + "", "Q1", new Integer(20));
        categorymodel.setValue(year - 1 + "", "Q2", new Integer(35));
        categorymodel.setValue(year - 1 + "", "Q3", new Integer(40));
        categorymodel.setValue(year - 1 + "", "Q4", new Integer(55));
        categorymodel.setValue(year + "", "Q1", new Integer(40));
        categorymodel.setValue(year + "", "Q2", new Integer(60));
        categorymodel.setValue(year + "", "Q3", new Integer(70));
        categorymodel.setValue(year + "", "Q4", new Integer(90));
        mybarchart.setModel(categorymodel);
		
		for (DeviceDTO d: devices) {
			Gmarker marker = new Gmarker(d.getDeviceName().toString(),d.getLat(), d.getLng());
			marker.setOpen(false);
			if (InetAddress.getByName(d.getIpAddress()).isReachable(1000)) {
				marker.setIconImage("/common/img/wifi.png");
				up += 1;
			}
			else {
				marker.setIconImage("/common/img/wifi_red.png");
				down += 1;

			}
			marker.setDraggingEnabled(false);
			mapModel.add(marker);
			
		}
		devUp.setValue(" Online "+up);
		devDown.setValue(" Offline "+down);
		devTotal.setValue(" Total "+devices.size());
	}
	
	public MapModelList getMapModel() {
		return mapModel;
	}
}
