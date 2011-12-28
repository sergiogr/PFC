package org.pfc.snmp;

import java.util.Calendar;

public class SnmpResponse {
	
	private String oid;
	private String value;
	private Calendar date;
	
	public SnmpResponse(String oid, String value,
			Calendar date) {
		this.oid = oid;
		this.value = value;
		this.date = date;
	}
	
	public String getOid() {
		return oid;
	}
	public String getValue() {
		return value;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
}
