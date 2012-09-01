package org.pfc.business.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProjectsFindResponse", namespace="http://webservice.business.pfc.org/" )
public class ProjectsFindResponse {

	@XmlElementWrapper(name="projectDTOs")
	private List<ProjectDTO> projectDTOs;

	public ProjectsFindResponse() {

	}

	public ProjectsFindResponse(List<ProjectDTO> projectDTOs) {
		this.projectDTOs = projectDTOs;
	}

	public List<ProjectDTO> getProjectDTOs() {
		return projectDTOs;
	}

	public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
		this.projectDTOs = projectDTOs;
	}
}
