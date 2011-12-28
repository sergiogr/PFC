package org.pfc.snmp;

import java.io.IOException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.security.Priv3DES;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.TransportIpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.AbstractTransportMapping;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class TrapReceiver implements CommandResponder {
//	public static void main(String[] args) {
//		TrapReceiver snmp4jTrapReceiver = new TrapReceiver();
//		try {
//			snmp4jTrapReceiver.listen(new UdpAddress("0.0.0.0/1162"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	private ThreadPool threadPool;
	private MessageDispatcher mDispathcher;
	private Snmp snmp;
	private Address listenAddress;
	
	public TrapReceiver(String ip, String port) {
		super();

		listenAddress=new UdpAddress(ip+"/"+port);
	}
	
	public void start() {
		try {
			this.listen((TransportIpAddress) listenAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void stop() {
		try {
			snmp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Trap Listener
	 */
	public synchronized void listen(TransportIpAddress address) throws IOException {
		AbstractTransportMapping transport;
		if (address instanceof TcpAddress) {
			transport = new DefaultTcpTransportMapping((TcpAddress) address);
		} else {
			transport = new DefaultUdpTransportMapping((UdpAddress) address);
		}

		threadPool = ThreadPool.create("DispatcherPool", 10);
		mDispathcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

		// add message processing models
		mDispathcher.addMessageProcessingModel(new MPv1());
		mDispathcher.addMessageProcessingModel(new MPv2c());

		// add all security protocols
		SecurityProtocols.getInstance().addDefaultProtocols();
		SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

		// Create Target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));

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
		}
	}	
}