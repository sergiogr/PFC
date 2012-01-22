package org.pfc.web.widgets.duallistbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@SuppressWarnings("serial")
public class DualListbox extends Div implements IdSpace {
	
	private Listbox candidateLb;
	private Listbox chosenLb;
	private ListModelList candidateModel;
	private ListModelList chosenDataModel;
	
	public DualListbox() {
		Executions.createComponents("/widgets/listbox/dual_listbox/v_duallistbox.zul", this, null);
		Components.wireVariables(this, this);
		Components.addForwards(this, this);
		chosenLb.setModel(chosenDataModel = new ListModelList());
	}
	
	@SuppressWarnings("rawtypes")
	public void onClick$chooseBtn() {
		Set set = choseOne();
		Events.postEvent(new ChooseEvent(this, set));
	}
	
	@SuppressWarnings("rawtypes")
	public void onClick$removeBtn() {
        Set set = unchooseOne();
        Events.postEvent(new ChooseEvent(this, set));
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set choseOne() {
		Set set = candidateLb.getSelectedItems();
        for (Object obj : new ArrayList(set)) {
            Object data = ((Listitem) obj).getValue();
            chosenDataModel.add(data);
            candidateModel.remove(data);
        }
        return set;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set unchooseOne() {
	     Set set = chosenLb.getSelectedItems();
	     for (Object obj : new ArrayList(set)) {
	    	 Object data = ((Listitem) obj).getValue();
	    	 candidateModel.add(data);
	    	 chosenDataModel.remove(data);  
	     }
	     return set;	// TODO Auto-generated method stub
	}
	
	 // Set Renderer of candidate list and chosen list (Used in Java)
    @SuppressWarnings("unchecked")
	public void setRenderer(DualListitemRenderer dataRenderer) {
        candidateLb.setItemRenderer(dataRenderer);
        chosenLb.setItemRenderer(dataRenderer);
        candidateLb.getChildren().add(dataRenderer.getListhead());
        chosenLb.getChildren().add(dataRenderer.getListhead());
    }
 
    // Set Renderer of candidate list and chosen list by string (Used in ZUML)
    public void setRenderer(String rendererClass) {
        try {
            DualListitemRenderer renderer = (DualListitemRenderer) Class.forName(rendererClass).newInstance();
            setRenderer(renderer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Set new candidate ListModelList.
     * 
     * @param candidate is the data of candidate list model
     */
    @SuppressWarnings("rawtypes")
	public void setModel(List candidate) {
        candidateLb.setModel(this.candidateModel = new ListModelList(candidate));
        chosenDataModel.clear();
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setModel(List candidate, List chosen) {
    	for (Object obj : new ArrayList(chosen)) {
    		
    		candidate.remove(obj);
    		
    	}
    	candidateLb.setModel(this.candidateModel = new ListModelList(candidate));
    	chosenLb.setModel(this.chosenDataModel = new ListModelList(chosen));
    }
    
    /**
     * @return current chosen data list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List getChosenDataList() {
        return new ArrayList(chosenDataModel);
    }
	
	// Customized Event
    public class ChooseEvent extends Event {
        @SuppressWarnings("rawtypes")
		public ChooseEvent(Component target, Set data) {
            super("onChoose", target, data);
        }
    }

}
