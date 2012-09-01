package org.pfc.web.device;



import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.snmp.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.event.MapDropEvent;
import org.zkoss.gmaps.event.MapMoveEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public class HomeController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3190271104080945929L;
	
//	private Window win;
	private Listbox deviceList;
	private Grid deviceForm;
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
	private Listbox productList;
	private Listbox mibObjectList;
	
	private Device current = new Device();
	private Device newDev;
	private MibObject mibObject = new MibObject();
	private Product selectedProduct;
	
	@Autowired
	private IDeviceService deviceService; 
	
	@Autowired
	private IProductService productService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		renderMap(map);
		map.setCenter(43.354891546397745,-8.416385650634766);
	}

	public Device getCurrent() {
		return current;
	}

	public void setCurrent(Device current) {
		this.current = current;
	}
	
	public Device getNewDev() {
		return newDev;
	}

	public void setNewDev(Device newDev) {
		this.newDev = newDev;
	}
	
	public MibObject getMibObject() {
		return mibObject;
	}


	public void setMibObject(MibObject mibObject) {
		this.mibObject = mibObject;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}


	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}


	public List<Device> getDevices() {
		return deviceService.findAllDevice();
	}
	
	public List<Product> getProducts() {
		return productService.findAllProducts();
	}
	
	public List<MibObject> getMibObjects() throws InstanceNotFoundException {
		if (current.getProduct() == null) {
			return new ArrayList<MibObject>();
		}else {
			return deviceService.getMibObjects(current.getDeviceId());
		}
	}
	
	/**
	 * Realiza una consulta SNMP del OID especificado sobre el dispositivo seleccionado de la lista.
	 * @return
	 */
	public String snmpQuery() {
		SnmpService snmp = new SnmpService();
		return snmp.snmpGet(current.getPublicCommunity(), current.getIpAddress(), current.getSnmpPort(), oid.getValue());
	}

	private void renderMap(Gmaps m) {
		m.setDoubleClickZoom(true);
		m.setScrollWheelZoom(true);
		m.getChildren().clear();
		List<Device> devices = deviceService.findAllDevice();
		for (Device d:devices){
			Gmarker marker = new Gmarker(d.getDeviceName(),d.getPosition().getX(),d.getPosition().getY());
			marker.setDraggingEnabled(false);
			m.appendChild(marker);
		}
	}
	
	/**
	 * Cuando arrastramos un Marker en el mapa actualiza 'lat' y 'lng' y centra el mapa en estar coordenadas.
	 * @param event
	 */
	public void onMapDrop$map(MapDropEvent event) {
		lat.setValue(event.getLat());
		lng.setValue(event.getLng());
		
		map.panTo(lat.getValue(), lng.getValue());

	}
	
	/**
	 * Cuando nos movemos por el mapa actualiza 'lat' y 'lng'
	 * @param event
	 */
	public void onMapMove$map(MapMoveEvent event) {
		lat.setValue(event.getLat());
		lng.setValue(event.getLng());
	}
	
	/**
	 * Genera n dispositivos posicionados de forma aleatoria.
	 */
	public void onClick$addTestData() {
		
		int n = 5;
		for (int i=1; i<=n; i++) {
			double x = map.getLat() + Math.random() % 0.1;
			double y = map.getLng() + Math.random() % 0.1;
			GeometryFactory geom = new GeometryFactory();
	        Point pos = geom.createPoint(new Coordinate(x, y));
			Device d = new Device("AP"+i,"AP de prueba "+i,"127.0.0.1","public","161",
					pos);
			try {
				deviceService.createDevice(d);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		renderMap(map);
	}	
	
	/**
	 * Muestra el formulario para añadir un nuevo dispositivo y posiciona 
	 * un Marker en el mapa para situarlo en la posición correcta.
	 */
	public void onClick$addDevice() {
		newDev = new Device();
		lat.setValue(map.getLat());
		lng.setValue(map.getLng());
		Gmarker m = new Gmarker("newDevice", lat.getValue(), lng.getValue());
		
		map.appendChild(m);

		deviceList.setVisible(false);
		deviceForm.setVisible(true);
	}
	
	/**
	 * Muestra el formulario para editar el dispositivo seleccionado.
	 * Si no tenemos ningún dispositivo seleccionado nos muestra una alerta.
	 */
	public void onClick$editDevice() {

		if (current.getDeviceId() != null) {
			newDev = current;
			name.setValue(current.getDeviceName());
			description.setValue(current.getDescription());
			
			selectedProduct = current.getProduct();

			ipAddress.setValue(current.getIpAddress());
			pubCommunity.setValue(current.getPublicCommunity());
			snmpPort.setValue(current.getSnmpPort());
			lat.setValue(current.getPosition().getX());
			lng.setValue(current.getPosition().getY());
			map.getChildren().clear();
			Gmarker m = new Gmarker(current.getDeviceName(),current.getPosition().getX(),current.getPosition().getY());
			m.setDraggingEnabled(true);
			map.setCenter(current.getPosition().getX(), current.getPosition().getY());
			map.appendChild(m);
			
			deviceList.setVisible(false);
			deviceForm.setVisible(true);	
		}
		else {
			alert("Selecciona el dispositivo que quieres editar");	
		}
	}
	
	/**
	 * Crea o actualiza en BD un dispositivo.
	 */
	public void onClick$save() {

		newDev.setDeviceName(name.getValue());
		newDev.setDescription(description.getValue());
		newDev.setIpAddress(ipAddress.getValue());
		newDev.setPublicCommunity(pubCommunity.getValue());
		newDev.setSnmpPort(snmpPort.getValue());
		if (productList.getSelectedItem() != null) {
			newDev.setProduct((Product) productList.getSelectedItem().getValue());
		}
		GeometryFactory geom = new GeometryFactory();
        Point position = geom.createPoint(new Coordinate(lat.getValue(), lng.getValue()));
		newDev.setPosition(position);
		
		try {
			deviceService.createDevice(newDev);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        name.setValue(null);
        description.setValue(null);
        ipAddress.setValue(null);
        pubCommunity.setValue("public");
        snmpPort.setValue("161");
        lat.setValue(null);
        lng.setValue(null);
        
        renderMap(map);
        
		deviceList.setVisible(true);
		deviceForm.setVisible(false);
	
	}

	/**
	 * Cancela una acción de añadir o editar un dispositivo.
	 */
	public void onClick$cancel() {
		
		name.setValue(null);
        description.setValue(null);
        ipAddress.setValue(null);
        pubCommunity.setValue("public");
        snmpPort.setValue("161");
        snmpPort.setValue("161");
        lat.setValue(null);
        lng.setValue(null);
        
		deviceList.setVisible(true);
		deviceForm.setVisible(false);
        renderMap(map);
	
	}
	
	/**
	 * Elimina el dispositivo seleccionado.
	 */
	public void onClick$delDevice() {
		
		try {
			
			if (current.getDeviceId() != null) {
				deviceService.removeDevice(current.getDeviceId());
				current.setDeviceId(null);
				renderMap(map);
			}
			else {
				alert("Selecciona el dispositivo que quieres eliminar");
			}
		} catch (InstanceNotFoundException e) {
			alert("No se ha encontrado el dispositivo seleccionado en la BD");
		}
	}
	
	/**
	 * Realiza una consulta SNMP del MibObject seleccionado sobre el Device seleccionado.
	 */
	public void onClick$query() {
		if (current.getDeviceId() != null) {
			snmpGet.setValue("SNMP request to: "+current.getDeviceName()+" - Response: "+ this.snmpQuery());
		}
		else {
			alert("Selecciona el dispositivo que quieres consultar");
		}
	}
	
	public void onSelect$mibObjectList() {
		MibObject curMibObject = (MibObject) mibObjectList.getSelectedItem().getValue();
		oid.setValue(curMibObject.getOid());
	}
}
