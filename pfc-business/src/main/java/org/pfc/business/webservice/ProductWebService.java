package org.pfc.business.webservice;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.device.Device;
import org.pfc.business.deviceservice.IDeviceService;
import org.pfc.business.mibobject.MibObject;
import org.pfc.business.product.Product;
import org.pfc.business.productservice.IProductService;
import org.pfc.business.util.exceptions.DuplicateInstanceException;
import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductWebService implements IProductWebService {

	@Autowired
	private IProductService productService;

	@Autowired
	private IDeviceService deviceService;
	
//	public ProductDTO createProduct(ProductDTO productDTO) {
//		Product product = new Product(productDTO.getProductName(),productDTO.getDescription(),
//				productDTO.getManufacturer());
//		product.setProductId(productDTO.getProductId()); //Si no se usa para update no hace falta 
//		return toProductDTO(productService.createProduct(product));
//	}
//	
	@Override
	public ProductDTO createProduct(ProductDTO productDTO, List<MibObjectDTO> mibObjectDTOs) {
		Product product = productService.createProduct(new Product(productDTO.getProductName(),productDTO.getDescription(),
				productDTO.getManufacturer()));
		if (mibObjectDTOs != null) {
			for (MibObjectDTO mo : mibObjectDTOs) {
				try {
					productService.assignMibObjectToProduct(mo.getMibObjectId(), product.getProductId());
				} catch (InstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return toProductDTO(product);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, List<MibObjectDTO> mibObjectDTOs) throws InstanceNotFoundException {
		Product product = productService.findProduct(productDTO.getProductId());
		product.setProductName(productDTO.getProductName());
		product.setDescription(productDTO.getDescription());
		product.setManufacturer(productDTO.getManufacturer());
		productService.createProduct(product);
		
		if (mibObjectDTOs != null) {
			for (MibObject mo : productService.findMibObjectsByProductId(productDTO.getProductId())) {
				if (mibObjectDTOs.contains(toMibObjectDTO(mo))) {
					productService.unassignMibObjectFromProduct(mo.getMibObjectId(), product.getProductId());
				}
			}
			for (MibObjectDTO mo : mibObjectDTOs) {
				productService.assignMibObjectToProduct(mo.getMibObjectId(), product.getProductId());	
			}
		}
		return toProductDTO(product);
	}
	
	@Override
	public MibObjectDTO createMibObject(MibObjectDTO mibObjectDTO, List<ProductDTO> productDTOs) {
		MibObject mibObject = productService.createMibObject(new MibObject(mibObjectDTO.getMibObjectName(),mibObjectDTO.getDescription(),
				mibObjectDTO.getOid(),mibObjectDTO.getMib()));
		if (productDTOs != null) {
			for (ProductDTO p : productDTOs) {
				try {
					productService.assignMibObjectToProduct(mibObject.getMibObjectId(), p.getProductId());
				} catch (InstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return toMibObjectDTO(mibObject);
		
	}
	
	@Override
	public MibObjectDTO updateMibObject(MibObjectDTO mibObjectDTO, List<ProductDTO> productDTOs) throws InstanceNotFoundException {
		MibObject mibObject = productService.findMibObject(mibObjectDTO.getMibObjectId());
		mibObject.setMibObjectName(mibObjectDTO.getMibObjectName());
		mibObject.setDescription(mibObjectDTO.getDescription());
		mibObject.setMib(mibObjectDTO.getMib());
		mibObject.setOid(mibObjectDTO.getOid());
		productService.createMibObject(mibObject);
		
		if (productDTOs != null) {
			for (Product p : productService.findProductsByMibObjectId(mibObjectDTO.getMibObjectId())){
				if (!productDTOs.contains(toProductDTO(p))) {
					try {
						productService.unassignMibObjectFromProduct(mibObjectDTO.getMibObjectId(), p.getProductId());
					} catch (InstanceNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			for (ProductDTO p : productDTOs) {
				productService.assignMibObjectToProduct(mibObjectDTO.getMibObjectId(), p.getProductId());
			}
		}
		return toMibObjectDTO(mibObject);
	}
	
	@Override
	public void assignMibObjectToProduct(Long mibObjectId, Long productId)
			throws InstanceNotFoundException {

		productService.assignMibObjectToProduct(mibObjectId, productId);
		
	}

	@Override
	public void unassignMibObjectFromProduct(Long mibObjectId, Long productId)
			throws InstanceNotFoundException {

		productService.unassignMibObjectFromProduct(mibObjectId, productId);
		
	}

	@Override
	public void removeProduct(Long productId) throws InstanceNotFoundException {

		for (Device d : deviceService.findDevicesByProduct(productId)) {
			d.setProduct(null);
			try {
				deviceService.updateDevice(d);
			} catch (DuplicateInstanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		productService.removeProduct(productId);
		
	}

	@Override
	public void removeMibObject(Long mibObjectId)
			throws InstanceNotFoundException {

		productService.removeMibObject(mibObjectId);
		
	}

	@Override
	public ProductDTO findProduct(Long productId) throws InstanceNotFoundException {

		return toProductDTO(productService.findProduct(productId));
		
	}

	@Override
	public ProductsFindResponse findAllProducts() {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<Product> products = productService.findAllProducts();
		
		for (Product p: products) {
			productDTOs.add(toProductDTO(p));
		}
		return new ProductsFindResponse(productDTOs);		
	}

	@Override
	public MibObjectDTO findMibObject(Long mibObjectId)
			throws InstanceNotFoundException {

		return toMibObjectDTO(productService.findMibObject(mibObjectId));
		
	}

	@Override
	public MibObjectsFindResponse findAllMibObjects() {

		List<MibObjectDTO> mibObjectDTOs = new ArrayList<MibObjectDTO>();
		List<MibObject> mibObjects = productService.findAllMibObjects();
		
		for (MibObject mo : mibObjects) {
			mibObjectDTOs.add(toMibObjectDTO(mo));
		}
		return new MibObjectsFindResponse(mibObjectDTOs);		
	}

	@Override
	public ProductsFindResponse findProductsByMibObjectId(Long mibObjectId) {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<Product> products =  productService.findProductsByMibObjectId(mibObjectId);
		
		for (Product p: products) {
			productDTOs.add(toProductDTO(p));
		}
		return new ProductsFindResponse(productDTOs);	
	}

	@Override
	public MibObjectsFindResponse findMibObjectsByProductId(Long productId) {

		List<MibObjectDTO> mibObjectDTOs = new ArrayList<MibObjectDTO>();
		List<MibObject> mibObjects =  productService.findMibObjectsByProductId(productId);
		
		for (MibObject mo : mibObjects) {
			mibObjectDTOs.add(toMibObjectDTO(mo));
		}
		return new MibObjectsFindResponse(mibObjectDTOs);
	}
	
	private ProductDTO toProductDTO(Product product) {
		return new ProductDTO(product.getProductId(),product.getProductName(), product.getDescription(),
				product.getManufacturer());
	}
	
	private MibObjectDTO toMibObjectDTO(MibObject mibObject) {
		return new MibObjectDTO(mibObject.getMibObjectId(),mibObject.getMibObjectName(),
				mibObject.getDescription(), mibObject.getOid(), mibObject.getMib());
	}
	
}
