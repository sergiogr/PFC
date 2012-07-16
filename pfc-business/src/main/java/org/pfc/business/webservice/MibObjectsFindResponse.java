package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MibObjectsFindResponse", namespace="http://webservice.business.pfc.org/" )
public class MibObjectsFindResponse {
	
	@XmlElementWrapper(name="mibObjectDTOs")
	private List<MibObjectDTO> mibObjectDTOs;

	public MibObjectsFindResponse() {
	
	}

	public MibObjectsFindResponse(List<MibObjectDTO> mibObjectDTOs) {
		this.mibObjectDTOs = mibObjectDTOs;
	}

	public List<MibObjectDTO> getMibObjectDTOs() {
		return mibObjectDTOs;
	}

	public void setMibObjectDTOs(List<MibObjectDTO> mibObjectDTOs) {
		this.mibObjectDTOs = mibObjectDTOs;
	}
	
	

}
