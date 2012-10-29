package org.pfc.web.monitor;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.DataDTO;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDataWebService;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.business.webservice.IProductWebService;
import org.pfc.business.webservice.MibObjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

@SuppressWarnings("serial")
public class MonitorController extends GenericForwardComposer {

	@Autowired
	private IDataWebService dataWSClient;

	@Autowired
	private IDeviceWebService deviceWSClient;
	
	@Autowired
	private IProductWebService productWSClient;

	private Listbox deviceLb;
	private Grid dataGrid;
	private Grid historyGrid;
	private Rows dataRows;
	private Rows historyRows;
	private Label historyLabel;
	
	private DeviceDTO selected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		comp.setAttribute(comp.getId(), this, true);
		dataGrid.setEmptyMessage("Select a device to check its data...");
		dataGrid.setAutopaging(true);
		historyGrid.setEmptyMessage("No device selected.");
		historyGrid.setAutopaging(true);
	
	}

	public List<DeviceDTO> getDevices() {
		return deviceWSClient.findAllDevice().getDeviceDTOs();
	}

	public List<DataDTO> getData() {
		if (selected != null) {
			return dataWSClient.findDataByDeviceId(selected.getDeviceId()).getDataDTOs();
		}
		else {
			return new ArrayList<DataDTO>();
		}
	}

	public void onSelect$deviceLb() {

		try {
			selected = deviceWSClient.findDeviceByName(((DeviceDTO) deviceLb.getSelectedItem().getValue()).getDeviceName());
			dataGrid.getRows().getChildren().clear();
			List<MibObjectDTO> mibObjects = productWSClient.findMibObjectsByProductId(selected.getProductId()).getMibObjectDTOs();
			dataGrid.setEmptyMessage("Device "+selected.getDeviceName()+" is not being monitored.");
			historyGrid.setEmptyMessage("Select a Mib Object to view its history");
			historyLabel.setValue(selected.getDeviceName());
			for (final MibObjectDTO mo : mibObjects) {
				Row row = new Row();
				DataDTO data = dataWSClient.getMostRecentValue(selected.getDeviceId(), mo.getMibObjectId());
				row.setParent(dataRows);
				new Label(mo.getMibObjectName()).setParent(row);
				if (data != null) {
					new Label(data.getValue()).setParent(row);	
					new Label(data.getDate().getTime().toString()).setParent(row);		
					
				}
				else {
					new Label("No data found").setParent(row);	
					new Label("-").setParent(row);	
				}
				Button b = new Button("History");
				b.addEventListener("onClick", new EventListener() {
					public void onEvent(Event event) {
						List<DataDTO> history = dataWSClient.findDataByDeviceIdAndMibObjectId(selected.getDeviceId(), mo.getMibObjectId()).getDataDTOs();
						historyGrid.getRows().getChildren().clear();
						historyGrid.setEmptyMessage("There is no "+mo.getMibObjectName()+" for "+selected.getDeviceName());
						historyLabel.setValue(selected.getDeviceName()+" > "+mo.getMibObjectName());
						for (DataDTO h : history) {
							Row hrow = new Row();
							hrow.setParent(historyRows);
							new Label(h.getValue()).setParent(hrow);	
							new Label(h.getDate().getTime().toString()).setParent(hrow);
						}

					}
				});
				b.setParent(row);

			}
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}