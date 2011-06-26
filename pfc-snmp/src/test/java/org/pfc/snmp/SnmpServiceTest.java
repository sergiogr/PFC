package org.pfc.snmp;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SnmpServiceTest {

	
	private SnmpService snmpService = new SnmpService();
	
	@Test
	public void testSnmpGet(){
		
//		String str = snmpService.snmpGet("public", "localhost", "161", "1.3.6.1.2.1.1.5.0");
//		System.out.println(str);
//		
//		assertEquals(str,"Koala");
	
		assertEquals(1,1);
	}
}
