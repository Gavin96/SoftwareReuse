package Configuration;

import static org.junit.Assert.*;
import java.lang.*;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {
	Configuration configuration;

	@Before
	public void setUp() throws Exception {
		configuration=new Configuration();
	}

	@Test
	public void testGetPort() {
		String port = configuration.getPort();
		assertEquals("61616",port);
	}

	@Test
	public void testGetTimeGap() {
		String timeGap = configuration.getTimeGap();
		assertEquals("1000",timeGap);
	}

	@Test
	public void testGetMaxRequestTimes() {
		String maxRequestTimes=configuration.getMaxRequestTimes();
		assertEquals("5",maxRequestTimes);
	}

	@Test
	public void testGetPath() {
		String path=configuration.getPath();
		assertEquals("C:\\",path);
	}

	@Test
	public void testReadFile() {
		boolean retval=configuration.path.endsWith(".txt");
		assertTrue(retval);
	}


}
