package org.pfc.business.dataservice;

import java.util.Calendar;

public class DataInfo {
	
	private String mibObjectName;
	private String value;
	private Calendar date;
	
	public DataInfo() {
		
	}
	
	public DataInfo(String mibObjectName, String value, Calendar date) {
	
		this.mibObjectName = mibObjectName;
		this.value = value;
		this.date = date;
	}

	public String getMibObjectName() {
		return mibObjectName;
	}

	public String getValue() {
		return value;
	}

	public Calendar getDate() {
		return date;
	}
	
	
	
	

}
