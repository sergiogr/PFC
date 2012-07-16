package org.pfc.alarm;

import java.io.IOException;

import org.pfc.snmp.ProcessAction;
import org.pfc.snmp.TrapReceiver;

public class Alarm {
	
	public static void main(String [] args) {

		ProcessAction processAction = new ProcessActionDB();
		TrapReceiver trapReceiver = new TrapReceiver("0.0.0.0","1162", processAction);
		
		try {
			trapReceiver.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
