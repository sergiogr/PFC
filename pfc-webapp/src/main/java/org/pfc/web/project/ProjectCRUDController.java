package org.pfc.web.project;

import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@SuppressWarnings("serial")
public class ProjectCRUDController extends GenericForwardComposer {

	@Autowired
	private IDeviceWebService deviceWSClient;
	
	private enum Action {LIST, CREATE, EDIT};

	private Window listWin;
	private Window editWin;
	private Textbox projectNameTb;
	private Label noProjectName;
	private Textbox descriptionTb;
	private Label noDescription;
	private ProjectDTO selected;
	
	private Action action = Action.LIST;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public ProjectDTO getSelected() {
		return selected;
	}

	public void setSelected(ProjectDTO selected) {
		this.selected = selected;
	}

	public ProjectCRUDController() {

	}	
	
	public ProjectCRUDController(Window listWin, Window editWin) {
		this.listWin = listWin;
		this.editWin = editWin;
	}
	
	public List<ProjectDTO> getProjects() {
		return deviceWSClient.findAllProjects().getProjectDTOs();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		projectNameTb = (Textbox) editWin.getFellowIfAny("projectNameTb");
		noProjectName = (Label) editWin.getFellowIfAny("noProjectName");
		descriptionTb = (Textbox) editWin.getFellowIfAny("descriptionTb");
		noDescription = (Label) editWin.getFellowIfAny("noDescription");
		comp.setAttribute("controller", this, true);
		goToList();

	}
	
	public void addProject() {
		projectNameTb.setValue("");
		descriptionTb.setValue("");
		goToCreateForm();
	}
	
	public void remove() {
		try {
			deviceWSClient.removeProject(selected.getProjectId());

		} catch (InstanceNotFoundException e) {
			alert("The selected project doesn't exist anymore");
		}
		
		goToList();
	}
	
	public void edit() {
		projectNameTb.setValue(selected.getProjectName());
		descriptionTb.setValue(selected.getDescription());
		goToEditForm();
	}
	
	public void save() {
		boolean error = false;
		if (projectNameTb.getValue().isEmpty()) {
			noProjectName.setVisible(true);
			error=true;
		}
		if (descriptionTb.getValue().isEmpty()) {
			noDescription.setVisible(true);
			error=true;
		}
		
		if (error == false) {
			
			if (getAction() == Action.CREATE){
		
				deviceWSClient.createProject(new ProjectDTO(null,projectNameTb.getValue(),descriptionTb.getValue()));
			}
			else if (getAction() == Action.EDIT){
				try {
					selected.setProjectName(projectNameTb.getValue());
					selected.setDescription(descriptionTb.getValue());
					deviceWSClient.updateProject(selected);
				} catch (InstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			goToList();
		}
	}
	
	public void cancel() {
		goToList();
	}
	
	private void goToList() {
		setAction(Action.LIST);

		listWin.setVisible(true);
		editWin.setVisible(false);
		noProjectName.setVisible(false);
		noDescription.setVisible(false);
	}
	
	private void goToCreateForm() {
		setAction(Action.CREATE);
		listWin.setVisible(false);
		editWin.setVisible(true);		
	}
	
	private void goToEditForm() {
		setAction(Action.EDIT);
		listWin.setVisible(false);
		editWin.setVisible(true);		
	}

}
