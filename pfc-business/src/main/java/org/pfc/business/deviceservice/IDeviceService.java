package org.pfc.business.deviceservice;

import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.project.Project;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IDeviceService {

	public Device createDevice(Device device) throws DuplicateInstanceException;
	
	public void removeDevice(Long deviceId) throws InstanceNotFoundException;
	
	public void updateDevice(Device device) throws InstanceNotFoundException,DuplicateInstanceException;
		
	public Device findDevice(Long deviceId) throws InstanceNotFoundException;
	
	public List<Device> findAllDevice();
	
    public List<MibObject> getMibObjects(Long deviceId) throws InstanceNotFoundException;
    
    public Device findDeviceByName(String deviceName) throws InstanceNotFoundException;

    public Project createProject(Project project);
    
    public Project findProject(Long projectId) throws InstanceNotFoundException;
    
    public void removeProject(Long projectId) throws InstanceNotFoundException;
    
	public void updateProject(Project project) throws InstanceNotFoundException;
 
	public List<Project> findAllProjects();
	
	public void addDeviceToProject(Long deviceId, Long projectId) throws InstanceNotFoundException;

	public void delDeviceFromProject(Long deviceId) throws InstanceNotFoundException;
}
