package org.pfc.business.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.pfc.business.mibobject.MibObject;
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

	@WebMethod(operationName = "findAllDevice")
	@WebResult(name = "DevicesFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public DevicesFindResponse findAllDevice();

//	
//	public void updateDevice(Device device) throws InstanceNotFoundException,DuplicateInstanceException;
//		
//	public Device findDevice(Long deviceId) throws InstanceNotFoundException;
//	
//	public List<Device> findAllDevice();
//	
	@WebMethod
	public List<MibObject> getMibObjects(Long deviceId) throws InstanceNotFoundException;
//    
//    public Device findDeviceByName(String deviceName) throws InstanceNotFoundException;

}
