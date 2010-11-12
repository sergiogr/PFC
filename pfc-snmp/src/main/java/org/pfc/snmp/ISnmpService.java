package org.pfc.snmp;

public interface ISnmpService {
	
	public String snmpGet(String community, String address, String port, String oid);

}
