package org.pfc.business.test.util;

import static org.pfc.business.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.pfc.business.util.GlobalNames.SPRING_CONFIG_FILE;

import org.pfc.business.device.Device;
import org.pfc.business.device.IDeviceDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class DbUtil {

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE});

		transactionManager = (PlatformTransactionManager) context
				.getBean("transactionManager");
		deviceDao = (IDeviceDao) context.getBean("deviceDao");
	}

	private static Long testDeviceId;
	
	private static Device testDevice;
	private static Point testPosition;
	
	private static IDeviceDao deviceDao;
	private static PlatformTransactionManager transactionManager;

	public static Long getTestDeviceId() {
		return testDeviceId;
	}

	public static Device getTestDevice(){
		return testDevice;
	}
	
	public static Point getTestPosition(){
		return testPosition;
	}
	
	public static void populateDb() throws Throwable {
		/*
		 * Since this method is supposed to be called from a @BeforeClass
		 * method, it works directly with "TransactionManager", since
		 * @BeforeClass methods with Spring TestContext do not run in the
		 * context of a transaction (which is required for DAOs to work).
		 */

		TransactionStatus transactionStatus = transactionManager
				.getTransaction(null);

		GeometryFactory testGeom = new GeometryFactory();
        testPosition = testGeom.createPoint(new Coordinate(1, 1));
		testDevice = new Device("testDevice","Device de prueba","1.1.1.1","public","161",testPosition, 0, 0);

		try {
			deviceDao.save(testDevice);
			testDeviceId = testDevice.getId();
			transactionManager.commit(transactionStatus);
		} catch (Throwable e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}

	}

	public static void cleanDb() throws Throwable {
		/*
		 * For the same reason as "populateDb" (with regard to @AfterClass
		 * methods), this method works directly with "TransactionManager".
		 */

		TransactionStatus transactionStatus = transactionManager
				.getTransaction(null);

		try {
			deviceDao.remove(testDeviceId);
			testDeviceId = null;
			testPosition = null;
			testDevice = null;
			transactionManager.commit(transactionStatus);
		} catch (Throwable e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}

	}

}
