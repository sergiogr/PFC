package org.pfc.business.util.genericdao;

import java.io.Serializable;

import org.pfc.business.util.exceptions.InstanceNotFoundException;


public interface IGenericDao <E, PK extends Serializable>{

	void save(E entity);

	E find(PK id) throws InstanceNotFoundException;

	boolean exists(PK id);

	void remove(PK id) throws InstanceNotFoundException;

}