package org.pfc.business.webservice;


import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://webservice.business.pfc.org/" )
public interface IDataWebService {

	public void addNewData(DataDTO dataDTO);
	
	@WebMethod(operationName = "findDataByDeviceId")
	@WebResult(name = "DataFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public DataFindResponse findDataByDeviceId(Long deviceId);
	
	@WebMethod(operationName = "findDataByDeviceIdAndMibObjectId")
	@WebResult(name = "DataFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public DataFindResponse findDataByDeviceIdAndMibObjectId(Long deviceId, Long mibObjectId);
	
	public DataDTO getMostRecentValue(Long deviceId, Long mibObjectId);
	
}
