package org.pfc.web.monitor;

import java.util.ArrayList;
import java.util.List;

import org.pfc.business.util.exceptions.InstanceNotFoundException;
import org.pfc.business.webservice.DataDTO;
import org.pfc.business.webservice.DeviceDTO;
import org.pfc.business.webservice.IDataWebService;
import org.pfc.business.webservice.IDeviceWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

@SuppressWarnings("serial")
public class MonitorController extends GenericForwardComposer {

	@Autowired
	private IDataWebService dataWSClient;

	@Autowired
	private IDeviceWebService deviceWSClient;

	private Listbox deviceLb;

	private DeviceDTO selected;

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
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
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}