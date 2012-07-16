package org.pfc.snmp;

import java.io.IOException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import org.pfc.snmp.ProcessAction;

public class TrapReceiver implements CommandResponder {

	private ThreadPool threadPool;
	private MessageDispatcher mDispathcher;
	private Snmp snmp;
	
	private String ipAddress;
	private String port;
	private ProcessAction processAction;
	
	public TrapReceiver(String ipAddress, String port, ProcessAction processAction) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.processAction = processAction;
		
	}
	
	/**
	 * Init Trap Listener
	 */
	public synchronized void init() throws IOException {
		
		TransportIpAddress address = new UdpAddress(ipAddress+"/"+port);
		AbstractTransportMapping transport = new DefaultUdpTransportMapping((UdpAddress) address);
	
		threadPool = ThreadPool.create("DispatcherPool", 10);
		mDispathcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

		// add message processing models
		mDispathcher.addMessageProcessingModel(new MPv1());
		mDispathcher.addMessageProcessingModel(new MPv2c());

//		// add all security protocols
//		SecurityProtocols.getInstance().addDefaultProtocols();
//		SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());
//
//		// Create Target
//		CommunityTarget target = new CommunityTarget();
//		target.setCommunity(new OctetString("public"));

		snmp = new Snmp(mDispathcher, transport);
		snmp.addCommandResponder(this);

		snmp.listen();
		System.out.println("Listening on " + address);

	}

	/**
	 * This method will be called whenever a pdu is received on the given port
	 * specified in the listen() method
	 */
	public synchronized void processPdu(CommandResponderEvent cmdRespEvent) {
		System.out.println("Received PDU...");
		PDU pdu = cmdRespEvent.getPDU();
		System.out.println("IP = " + cmdRespEvent.getPeerAddress());
		if (pdu != null) {
			System.out.println("Trap Type = " + pdu.getType());
			System.out.println("Variables = " + pdu.getVariableBindings());	
			System.out.println("PDU = " + pdu.toString());	
			
			processAction.processPDUAction(cmdRespEvent.getPeerAddress().toString(),
					PDU.getTypeString(pdu.getType()),pdu.getVariableBindings().toString(),pdu.toString());
		
		}
	}	
}