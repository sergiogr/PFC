package org.pfc.snmp;

public interface ProcessAction {
	
	public void processPDUAction(String ipSource,String trapType,String variables,String pdu);

}
