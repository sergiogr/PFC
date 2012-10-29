package org.pfc.alarm;

import java.io.IOException;

import org.pfc.snmp.ProcessAction;
import org.pfc.snmp.TrapReceiver;

public class Alarm {
	
	public static void main(String [] args) {

		String ip = "0.0.0.0";
		String port = "1162";
		
		if (args.length != 0) {
			System.out.println("Número de argumentos: "+args.length);
		}
		ProcessAction processAction = new ProcessActionDB();
		System.out.println("******************************");
		System.out.println("Iniciando servidor de alarmas:");
		System.out.println("**Escuchando en: "+ip+"/"+port);
		System.out.println("******************************");

		TrapReceiver trapReceiver = new TrapReceiver(ip,port, processAction);
		
		try {
			trapReceiver.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
