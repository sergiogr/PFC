package org.pfc.business.device;

import java.util.List;

import org.hibernatespatial.GeometryUserType;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository("deviceDao")
public class DeviceDao extends GenericDao<Device, Long> implements IDeviceDao{

	@SuppressWarnings("unchecked")
	public List<Device> getAllDevices() {
		
		return getSession().createQuery(
				"SELECT d FROM Device d ORDER BY d.deviceName").list();

	}
	
	public Device getDeviceByName(String deviceName) throws InstanceNotFoundException {
		
		Device device = (Device) getSession().createQuery(
				"FROM Device d WHERE d.deviceName = :deviceName")
				.setParameter("deviceName", deviceName)
				.uniqueResult();
		if (device == null) {
			throw new InstanceNotFoundException(deviceName, Device.class.getName());
		}
		else {
			return device;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> getDevicesByProject(Long projectId) {
		return getSession().createQuery(
				"SELECT d FROM Device d WHERE d.project.projectId = :projectId "+
				"ORDER BY d.deviceName").setParameter("projectId", projectId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Device> getDevicesByProduct(Long productId) {
		return getSession().createQuery(
				"SELECT d FROM Device d WHERE d.product.productId = :productId "+
				"ORDER BY d.deviceName").setParameter("productId", productId).list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Device> getDevicesByArea(double lat1, double lng1, double lat2, double lng2) {
		
		WKTReader fromText = new WKTReader();
        Geometry filter = null;

        String area = "POLYGON(("+lat1+" "+ lng1+","+lat1+" "+lng2+","+lat2+" "+lng2+","+lat2+" "+lng1+
        		","+lat1+" "+lng1+"))";
        try{
                filter = fromText.read(area);
        } catch(ParseException e){
                throw new RuntimeException("Not a WKT String:" + area);
        }

        System.out.println("Filter is : " + filter);

		List<Device> devices = getSession().createQuery("from Device d where within(position, ?) = true ORDER BY d.deviceName")
				.setParameter(0, filter, GeometryUserType.TYPE).list();
		return devices;

	}

}
