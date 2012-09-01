package org.pfc.business.project;

import java.util.List;

import org.pfc.business.project.Project;
import org.pfc.business.util.genericdao.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Repository("projectDao")
public class ProjectDao extends GenericDao<Project, Long> implements IProjectDao {

	@SuppressWarnings("unchecked")
	public List<Project> getAllProjects() {

		return getSession().createQuery(
				"SELECT p FROM Project p ORDER BY p.projectName").list();
	}

}
