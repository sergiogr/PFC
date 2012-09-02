package org.pfc.business.webservice;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

@WebService(targetNamespace="http://webservice.business.pfc.org/" )
public interface IDeviceWebService {

	@WebMethod
	public DeviceDTO createDevice(DeviceDTO deviceDTO) throws DuplicateInstanceException;
	
	@WebMethod
	public void removeDevice(Long deviceId) throws InstanceNotFoundException;
	
	@WebMethod
	public void updateDevice(DeviceDTO deviceDTO) throws InstanceNotFoundException,DuplicateInstanceException;
	
	@WebMethod
	public DeviceDTO findDevice(Long deviceId) throws InstanceNotFoundException;

	@WebMethod
	public DeviceDTO findDeviceByName(String deviceName) throws InstanceNotFoundException;
	
	@WebMethod
	public DevicesFindResponse findDevicesByProject(Long projectId);

	@WebMethod
	public DevicesFindResponse findDevicesByProduct(Long productId);
	
	@WebMethod
	public DevicesFindResponse findDevicesByArea(double lat1, double lng1, double lat2, double lng2);

	@WebMethod(operationName = "findAllDevice")
	@WebResult(name = "DevicesFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public DevicesFindResponse findAllDevice();
	
	@WebMethod
	public ProjectDTO createProject(ProjectDTO project);
	
	@WebMethod
	public ProjectDTO findProject(Long projectId) throws InstanceNotFoundException;

	@WebMethod
	public void removeProject(Long projectId) throws InstanceNotFoundException;
	
	@WebMethod
	public void updateProject(ProjectDTO projectDTO) throws InstanceNotFoundException;

	@WebMethod(operationName = "findAllProjects")
	@WebResult(name = "ProjectsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public ProjectsFindResponse findAllProjects();
	
	@WebMethod
	public void addDeviceToProject(DeviceDTO device, ProjectDTO project) throws InstanceNotFoundException;

	@WebMethod
	public void delDeviceFromProject(DeviceDTO device) throws InstanceNotFoundException;
	
//	
//	public void updateDevice(Device device) throws InstanceNotFoundException,DuplicateInstanceException;
//		
//	public Device findDevice(Long deviceId) throws InstanceNotFoundException;
//	
//	public List<Device> findAllDevice();
//	
//	@WebMethod
//	public MibObjectsFindResponse getMibObjects(Long deviceId) throws InstanceNotFoundException;
//    
//    public Device findDeviceByName(String deviceName) throws InstanceNotFoundException;


}
