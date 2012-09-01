package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DataFindResponse", namespace="http://webservice.business.pfc.org/" )
public class DataFindResponse {
	
	@XmlElementWrapper(name="dataDTOs")
	private List<DataDTO> dataDTOs;

	public DataFindResponse() {

	}

	public DataFindResponse(List<DataDTO> dataDTOs) {
		this.dataDTOs = dataDTOs;
	}

	public List<DataDTO> getDataDTOs() {
		return dataDTOs;
	}

	public void setDataDTOs(List<DataDTO> dataDTOs) {
		this.dataDTOs = dataDTOs;
	}

}
