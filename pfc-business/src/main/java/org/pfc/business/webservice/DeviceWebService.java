package org.pfc.business.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

@WebService
public class DeviceWebService implements IDeviceWebService {

	@Autowired
	private IDeviceService deviceService;
	
	@Autowired
	private IProductService productService;
	
	@Override
	public DeviceDTO createDevice(DeviceDTO deviceDTO) throws DuplicateInstanceException {
		GeometryFactory geom = new GeometryFactory();
		Point position = geom.createPoint(new Coordinate(deviceDTO.getLat(), deviceDTO.getLng()));
		Device device = new Device(deviceDTO.getDeviceName(), deviceDTO.getDescription(),
				deviceDTO.getIpAddress(),deviceDTO.getPublicCommunity(),deviceDTO.getSnmpPort(),position);
		if (deviceDTO.getProductId() != null) {
			try {
				device.setProduct(productService.findProduct(deviceDTO.getProductId()));
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return toDeviceDTO(deviceService.createDevice(device));

	}
	
	@Override
	public void removeDevice(Long deviceId) throws InstanceNotFoundException {

		deviceService.removeDevice(deviceId);
		
	}
	
	@Override
	public void updateDevice(DeviceDTO deviceDTO) throws InstanceNotFoundException, DuplicateInstanceException {

		GeometryFactory geom = new GeometryFactory();
		Point position = geom.createPoint(new Coordinate(deviceDTO.getLat(), deviceDTO.getLng()));
		
		Device device = deviceService.findDevice(deviceDTO.getDeviceId());
		device.setDeviceName(deviceDTO.getDeviceName());
		device.setDescription(deviceDTO.getDescription());
		device.setIpAddress(deviceDTO.getIpAddress());
		device.setPublicCommunity(deviceDTO.getPublicCommunity());
		device.setSnmpPort(deviceDTO.getSnmpPort());
		device.setPosition(position);
		if (deviceDTO.getProductId() != null) {
			device.setProduct(productService.findProduct(deviceDTO.getProductId()));
		}
		deviceService.updateDevice(device);
		
	}
	
	@Override
	public DeviceDTO findDevice(Long deviceId) throws InstanceNotFoundException {

		return toDeviceDTO(deviceService.findDevice(deviceId));
		
	}
	
	@Override
	public DeviceDTO findDeviceByName(String deviceName) throws InstanceNotFoundException {

		return toDeviceDTO(deviceService.findDeviceByName(deviceName));
		
	}
	
	@Override
	public DevicesFindResponse findAllDevice() {
		List<Device> devices = deviceService.findAllDevice();
		List<DeviceDTO> deviceDTOs = new ArrayList<DeviceDTO>();

		for (Device d : devices) {
			deviceDTOs.add(toDeviceDTO(d));
		}

		return new DevicesFindResponse(deviceDTOs);
	}
	
//	public void updateDevice(Device device) throws InstanceNotFoundException,
//			DuplicateInstanceException {
//
//		deviceService.updateDevice(device);
//		
//	}
//	
//	public Device findDevice(Long deviceId) throws InstanceNotFoundException {
//
//		return deviceService.findDevice(deviceId);
//		
//	}
//	
//	public List<Device> findAllDevice() {
//
//		return deviceService.findAllDevice();
//		
//	}
//	
	@Override
	public List<MibObject> getMibObjects(Long deviceId)
			throws InstanceNotFoundException {

		return deviceService.getMibObjects(deviceId);
		
	}
//
//	public Device findDeviceByName(String deviceName)
//			throws InstanceNotFoundException {
//
//		return deviceService.findDeviceByName(deviceName);
//		
//	}

	private DeviceDTO toDeviceDTO(Device device) {
		Long productId = null;
		if (device.getProduct() != null) {
			productId = device.getProduct().getProductId();
		}

		return new DeviceDTO(device.getDeviceId(),device.getDeviceName(), device.getDescription(),
				device.getIpAddress(),device.getPublicCommunity(),device.getSnmpPort(),
				device.getPosition().getX(),device.getPosition().getY(),productId);
		
	}
}
