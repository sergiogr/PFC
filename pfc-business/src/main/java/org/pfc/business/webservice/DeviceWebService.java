package org.pfc.business.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.project.Project;
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
	private IDeviceDao deviceDao;
	
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
//	@Override
//	public MibObjectsFindResponse getMibObjects(Long deviceId)
//			throws InstanceNotFoundException {
//		
//		List<MibObject> mibObjects = deviceService.getMibObjects(deviceId);
//		List<MibObjectDTO> mibObjectDTOs = new ArrayList<MibObjectDTO>();
//		
//		for (MibObject mo : mibObjects) {
//			mibObjectDTOs.add(toMibObjectDTO(mo));
//		}
//
//		return new MibObjectsFindResponse();
//		
//	}
//
//	public Device findDeviceByName(String deviceName)
//			throws InstanceNotFoundException {
//
//		return deviceService.findDeviceByName(deviceName);
//		
//	}


	@Override
	public DevicesFindResponse findDevicesByArea(double lat1, double lng1, double lat2, double lng2) {
		List<Device> devices = deviceDao.getDevicesByArea(lat1, lng1, lat2, lng2);
		List<DeviceDTO> deviceDTOs = new ArrayList<DeviceDTO>();
		System.out.println("Device by Area (server side): "+deviceDTOs.size());
		for (Device d : devices) {
			deviceDTOs.add(toDeviceDTO(d));
		}

		return new DevicesFindResponse(deviceDTOs);
	}
	
	public ProjectDTO createProject(ProjectDTO project) {
		Project p = new Project(project.getProjectName(),project.getDescription());
		return toProjectDTO(deviceService.createProject(p));
	}
	
	@Override
	public ProjectDTO findProject(Long projectId) throws InstanceNotFoundException {

		return toProjectDTO(deviceService.findProject(projectId));
		
	}

	@Override
	public ProjectsFindResponse findAllProjects() {
		List<Project> projects = deviceService.findAllProjects();
		List<ProjectDTO> projectDTOs = new ArrayList<ProjectDTO>();

		for (Project p : projects) {
			projectDTOs.add(toProjectDTO(p));
		}

		return new ProjectsFindResponse(projectDTOs);
	}

	@Override
	public void removeProject(Long projectId) throws InstanceNotFoundException {

		deviceService.removeProject(projectId);
		
	}

	@Override
	public void updateProject(ProjectDTO projectDTO)
			throws InstanceNotFoundException {

		Project project = new Project(projectDTO.getProjectName(),projectDTO.getDescription());
		project.setProjectId(projectDTO.getProjectId());
		deviceService.updateProject(project);
		
	}

	@Override
	public void addDeviceToProject(DeviceDTO device, ProjectDTO project) throws InstanceNotFoundException {
		deviceService.addDeviceToProject(device.getDeviceId(), project.getProjectId());
	}
	
	@Override
	public void delDeviceFromProject(DeviceDTO device) throws InstanceNotFoundException {

		deviceService.delDeviceFromProject(device.getDeviceId());
	}
	
	private DeviceDTO toDeviceDTO(Device device) {

		DeviceDTO d = new DeviceDTO(device.getDeviceId(),device.getDeviceName(), device.getDescription(),
				device.getIpAddress(),device.getPublicCommunity(),device.getSnmpPort(),
				device.getPosition().getX(),device.getPosition().getY());
		if (device.getProduct() != null) {
			d.setProductId(device.getProduct().getProductId());
		}
		if (device.getProject() != null) {
			d.setProjectId(device.getProject().getProjectId());
		}
		return d;
		
	}
	
	private ProjectDTO toProjectDTO(Project project) {
		
		return new ProjectDTO(project.getProjectId(),project.getProjectName(),project.getDescription());
		
	}
	
//	private MibObjectDTO toMibObjectDTO(MibObject mibObject) {
//		return new MibObjectDTO(mibObject.getMibObjectId(),mibObject.getMibObjectName(),
//				mibObject.getDescription(), mibObject.getOid(), mibObject.getMib());
//	}

}
