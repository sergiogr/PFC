package org.pfc.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpService implements ISnmpService {

	public String snmpGet(String community, String address, String port, String oid) {
		
		String str = "";
		
		try {

			Address targetAddress = new UdpAddress(address + "/" + port);
			TransportMapping transport = new DefaultUdpTransportMapping();
			//transport.listen();
		
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(community));
			target.setVersion(SnmpConstants.version2c);
			target.setAddress(targetAddress);
			target.setRetries(1);
			target.setTimeout(7200);

			Snmp snmp = new Snmp(transport);
			snmp.listen();
			PDU pdu = new PDU();
			pdu.add(new VariableBinding(new OID(oid)));
			pdu.setType(PDU.GET);

			ResponseEvent response = snmp.get(pdu, target);
			
			if(response != null) {
				if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")) {
					PDU pduresponse=response.getResponse();
					str=pduresponse.getVariableBindings().firstElement().toString();
					if(str.contains("=")){
						int len = str.indexOf("=");
						str=str.substring(len+1, str.length());
						str=str.trim();
					}
				}
			}
			else {
				System.out.println("Feeling like a TimeOut occured ");
			}
			snmp.close();
			
		} catch(Exception e) { e.printStackTrace(); }
			
		return str;
		
	}

}
