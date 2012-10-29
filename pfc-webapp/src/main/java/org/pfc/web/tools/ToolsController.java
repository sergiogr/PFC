package org.pfc.web.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.pfc.business.webservice.DevicesFindResponse;
import org.pfc.business.webservice.IDeviceWebService;
import org.pfc.snmp.SnmpResponse;
import org.pfc.snmp.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@SuppressWarnings("serial")
public class ToolsController extends GenericForwardComposer {
	
	private Listbox pingLb;
	private Label snmpGetLbl;
	private Label snmpGetNextLbl;
	private Listbox snmpWalkLb;

	private Textbox ipAddressPing;
	private Textbox ipAddressGet;
	private Textbox portGet;
	private Textbox communityGet;
	private Textbox oidGet;
	private Textbox ipAddressGetNext;
	private Textbox portGetNext;
	private Textbox communityGetNext;
	private Textbox oidGetNext;
	private Textbox ipAddressWalk;
	private Textbox portWalk;
	private Textbox communityWalk;
	private Textbox oidWalk;
	
	private Listbox devicesLb;
	
	@Autowired
	private IDeviceWebService deviceWSClient;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		
		super.doAfterCompose(comp);
		
	}
	
	public void onClick$pingBtn() {
		try {
			pingLb.getItems().clear();
			for (String line : ping(ipAddressPing.getValue())) {
				pingLb.appendItem(line, line);
			}
			pingLb.setVisible(true);
		} catch (WrongValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick$clearPingBtn() {
		
		ipAddressPing.setValue("");
		pingLb.getItems().clear();
		pingLb.setVisible(false);
	}
	
	public void onClick$snmpGetBtn() {
			
		SnmpService snmp = new SnmpService();
//		snmp.snmpGet(communityGet.getValue(), ipAddressGet.getValue(), portGet.getValue(), oidGet.getValue());
		snmpGetLbl.setValue("SNMP request to: "+ipAddressGet.getValue()+" - Response: "
				+ snmp.snmpGet(communityGet.getValue(), ipAddressGet.getValue(), portGet.getValue(), oidGet.getValue()));
			
	}
	
	public void onClick$clearGetBtn() {
		
		ipAddressGet.setValue("");
		snmpGetLbl.setValue("");
	}

	public void onClick$snmpGetNextBtn() {
		
		SnmpService snmp = new SnmpService();
		snmpGetNextLbl.setValue("SNMP request to: "+ipAddressGet.getValue()+" - Response: "
				+ snmp.snmpGetNext(communityGetNext.getValue(), ipAddressGetNext.getValue(), portGetNext.getValue(), oidGetNext.getValue()));
			
	}
	
	public void onClick$clearGetNextBtn() {
		
		ipAddressGetNext.setValue("");
		snmpGetNextLbl.setValue("");
	}


	public void onClick$snmpWalkBtn() {
		SnmpService snmp = new SnmpService();
		List<SnmpResponse> response = new ArrayList<SnmpResponse>();
		response.addAll(snmp.snmpWalk(communityWalk.getValue(), ipAddressWalk.getValue(), portWalk.getValue(), oidWalk.getValue()));
		snmpWalkLb.getItems().clear();
		snmpWalkLb.setModel(new ListModelList(response));
		snmpWalkLb.setVisible(true);
		snmpWalkLb.renderAll();
		System.out.println("Just got "+response.size()+" elements.");
	}
	
	public void onClick$clearWalkBtn() {
		
		ipAddressWalk.setValue("");
		snmpWalkLb.getItems().clear();
		snmpWalkLb.setVisible(false);
	}
	
	public void onClick$findDevicesBtn() {
		DevicesFindResponse devices = deviceWSClient.findAllDevice();
		System.out.println("Devices on the area: "+devices.getDeviceDTOs().size());
		devicesLb.setModel(new ListModelList(devices.getDeviceDTOs()));
		devicesLb.setVisible(true);
	}


	
	private List<String> ping(String ipAddress) throws IOException {

		String command[] = {"ping", "-c4", ipAddress};
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process p = pb.start();
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		List<String> result = new ArrayList<String>();
		while((line = in.readLine()) != null) {
			result.add(line);
		}
		return result;

	}
}
