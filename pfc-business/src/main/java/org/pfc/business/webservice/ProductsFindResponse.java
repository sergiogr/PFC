package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProductsFindResponse", namespace="http://webservice.business.pfc.org/" )
public class ProductsFindResponse {
	
	@XmlElementWrapper(name="productDTOs")
	private List<ProductDTO> productDTOs;

	public ProductsFindResponse() {

	}

	public ProductsFindResponse(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

}
