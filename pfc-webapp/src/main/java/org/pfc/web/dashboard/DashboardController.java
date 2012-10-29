package org.pfc.web.dashboard;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.EventDTO;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IEventWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.ProductDTO;
import org.pfc.business.webservice.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.MapModelList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Label;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

@SuppressWarnings("serial")
public class DashboardController extends GenericForwardComposer {

	public static Double centerLat = 43.354891546397745;
	public static Double centerLng = -8.416385650634766;
	
	@Autowired
	private IEventWebService eventWSClient;
	
	@Autowired
	private IDeviceWebService deviceWSClient;
	
	@Autowired
	private IProductWebService productWSClient;
	
	private Gmaps map;
	private Label devTotal;
	private Label devUp;
	private Label devDown;
	private List<DeviceDTO> devices = new ArrayList<DeviceDTO>();
	private MapModelList mapModel = new MapModelList();
	private Flashchart projectsChart;
	private Flashchart productsChart;
	private Tree projectTree;
	private Tree productTree;
		
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

        projectsChart.setModel(generateProjectChart());
		projectsChart.setChartStyle("legend-display=left");

		productsChart.setModel(generateProductChart());
		productsChart.setChartStyle("legend-display=left");
		
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
		
		generateTreeByProject();
		generateTreeByProduct();

	}
	
	public MapModelList getMapModel() {
		return mapModel;
	}
	
	private PieModel generateProjectChart() {
        PieModel m = new SimplePieModel();
        int nDevices = 0;

         for (ProjectDTO p : deviceWSClient.findAllProjects().getProjectDTOs()){
        	 int n = deviceWSClient.findDevicesByProject(p.getProjectId()).getDeviceDTOs().size();
        	 m.setValue(p.getProjectName(), n);
        	 nDevices += n;
         }
         m.setValue("No product", deviceWSClient.findAllDevice().getDeviceDTOs().size() - nDevices);

         return m;
	}
	
	private PieModel generateProductChart() {
        PieModel m = new SimplePieModel();
        int nDevices = 0;
        for (ProductDTO p : productWSClient.findAllProducts().getProductDTOs()){
        	int n = deviceWSClient.findDevicesByProduct(p.getProductId()).getDeviceDTOs().size();
        	 m.setValue(p.getProductName(), n);
        	 nDevices += n;
        }
         m.setValue("No product", deviceWSClient.findAllDevice().getDeviceDTOs().size() - nDevices);
         return m;
	}

	private void generateTreeByProject() {
		Treechildren treeChildren = new Treechildren();
		for (ProjectDTO p : deviceWSClient.findAllProjects().getProjectDTOs()){
			Treeitem itemProj = new Treeitem();
			Treerow proj = new Treerow();
			treeChildren.appendChild(itemProj);
			itemProj.appendChild(proj);
			proj.appendChild(new Treecell(p.getProjectName()));
//			proj.appendChild(new Treecell(p.getDescription()));
			
			Treechildren treeDev = new Treechildren();
			itemProj.appendChild(treeDev);
			for (DeviceDTO d : deviceWSClient.findDevicesByProject(p.getProjectId()).getDeviceDTOs()){
				Treeitem itemDev = new Treeitem();
				Treerow dev = new Treerow();
				treeDev.appendChild(itemDev);
				itemDev.appendChild(dev);
				dev.appendChild(new Treecell(d.getDeviceName()));
				dev.appendChild(new Treecell(d.getDescription()));
			}
			
		}
		treeChildren.setParent(projectTree);
	
	}

	private void generateTreeByProduct() {
		Treechildren treeChildren = new Treechildren();
		for (ProductDTO p : productWSClient.findAllProducts().getProductDTOs()){
			Treeitem itemProd = new Treeitem();
			Treerow prod = new Treerow();
			treeChildren.appendChild(itemProd);
			itemProd.appendChild(prod);
			prod.appendChild(new Treecell(p.getProductName()));
//			prod.appendChild(new Treecell(p.getDescription()));
			
			Treechildren treeDev = new Treechildren();
			itemProd.appendChild(treeDev);
			for (DeviceDTO d : deviceWSClient.findDevicesByProduct(p.getProductId()).getDeviceDTOs()){
				Treeitem itemDev = new Treeitem();
				Treerow dev = new Treerow();
				treeDev.appendChild(itemDev);
				itemDev.appendChild(dev);
				dev.appendChild(new Treecell(d.getDeviceName()));
				dev.appendChild(new Treecell(d.getDescription()));
			}
			
		}
		treeChildren.setParent(productTree);
	
	}

}
