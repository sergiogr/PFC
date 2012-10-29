package org.pfc.snmp;

import java.util.List;

public interface ISnmpService {
	
	public String snmpGet(String community, String address, String port, String oid);
	
	public String snmpGetNext(String community, String address, String port, String oid);

	public SnmpResponse snmpGetQuery(String community, String address, String port, String oid);

	public List<String> snmpGetList(String community, String address, String port, List<String> oids);

	public List<SnmpResponse> snmpGetQueryList(String community, String address, String port, List<String> oids);

	public List<SnmpResponse> snmpWalk(String community, String address, String port, String oid);
	
}
