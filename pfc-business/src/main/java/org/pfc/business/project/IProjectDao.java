package org.pfc.business.project;

import java.util.List;

import org.pfc.business.project.Project;
import org.pfc.business.util.genericdao.IGenericDao;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
public interface IProjectDao extends IGenericDao<Project, Long> {

	/**
	 * 
	 * @return The list of all {@link Project} entities found.
	 */
	public List<Project> getAllProjects();
}
