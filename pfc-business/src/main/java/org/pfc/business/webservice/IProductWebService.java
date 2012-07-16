package org.pfc.business.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.pfc.business.util.exceptions.InstanceNotFoundException;

@WebService(targetNamespace="http://webservice.business.pfc.org/" )
public interface IProductWebService {
		
//	public ProductDTO createProduct(ProductDTO productDTO);
	
	public ProductDTO createProduct(ProductDTO productDTO, List<MibObjectDTO> mibObjectDTOs);

	public MibObjectDTO createMibObject(MibObjectDTO mibObjectDTO, List<ProductDTO> productDTOs);

	public ProductDTO updateProduct(ProductDTO productDTO, List<MibObjectDTO> mibObjectDTOs) throws InstanceNotFoundException;
	
	public MibObjectDTO updateMibObject(MibObjectDTO mibObjectDTO, List<ProductDTO> productDTOs) throws InstanceNotFoundException;

	public void assignMibObjectToProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException;

	public void unassignMibObjectFromProduct(Long mibObjectId, Long productId) throws InstanceNotFoundException;
	
	public void removeProduct(Long productId) throws InstanceNotFoundException;
	
	public void removeMibObject(Long mibObjectId) throws InstanceNotFoundException;

	public ProductDTO findProduct(Long productId) throws InstanceNotFoundException;
	
	@WebMethod(operationName = "findAllProducts")
	@WebResult(name = "ProductsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public ProductsFindResponse findAllProducts();
		
	public MibObjectDTO findMibObject(Long mibObjectId) throws InstanceNotFoundException;

	@WebMethod(operationName = "findAllMibObjects")
	@WebResult(name = "MibObjectsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public MibObjectsFindResponse findAllMibObjects();
	
	@WebMethod(operationName = "findProductsByMibObjectId")
	@WebResult(name = "ProductsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public ProductsFindResponse findProductsByMibObjectId(Long mibObjectId);
	
	@WebMethod(operationName = "findMibObjectsByProductId")
	@WebResult(name = "MibObjectsFindResponse", targetNamespace="http://webservice.business.pfc.org/" )
	public MibObjectsFindResponse findMibObjectsByProductId(Long productId);

}
