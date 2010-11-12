package org.pfc.business.test.deviceservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pfc.business.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.pfc.business.util.GlobalNames.SPRING_CONFIG_FILE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class DeviceServiceTest {
	
	private IDeviceDao deviceDao;

	@Autowired
	public void setDeviceDao(IDeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	private IDeviceService deviceService;
	
	@Autowired
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	@Test
	public void testCreateDevice() throws InstanceNotFoundException {

		GeometryFactory geom = new GeometryFactory();
        Point position = geom.createPoint(new Coordinate(43.354891546397745, -8.416385650634766));
		Device device = deviceService.createDevice(new Device("AP1","descripcion","10.0.0.1", "public", "161", position, 0, 0));

		assertTrue(deviceDao.exists(device.getId()));
	}

	@Test
	public void testFindAllDevice(){
		GeometryFactory geom = new GeometryFactory();
        Point position = geom.createPoint(new Coordinate(5, 5));
		deviceDao.save(new Device("APtest","Desc","1.1.1.1","public", "161",position, 0, 0));
		List<Device> devices;
		devices=deviceService.findAllDevice();
		System.out.println("Number of Devices: " + devices.size());
		assertTrue(devices.size() == 1);
		// Se comprueba que el posición se almacena correctamente.
		assertEquals(devices.get(0).getPosition(), position);
	}
	
}
