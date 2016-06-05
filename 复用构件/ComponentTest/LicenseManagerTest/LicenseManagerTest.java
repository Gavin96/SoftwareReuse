package com.HaroldLIU;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LicenseManagerTest {
	LicenseManager licenseManager;
	@Before
	public void setUp() throws Exception {
	licenseManager = new LicenseManager();
	}


	@Test
	public void testThroughputCheck() {
		//case 1
		licenseManager.ThroughputInit(3,1000,0);
		for (int i = 0;i<5;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println(licenseManager.ThroughputCheck());
        }
		
		//case2
		licenseManager.ThroughputInit(20,1000,1);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertTrue(licenseManager.ThroughputCheck());
        }
		
		//case3
		licenseManager.ThroughputInit(10,1000,5);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertTrue(licenseManager.ThroughputCheck());
        }
		
		//case4
		licenseManager.ThroughputInit(15,800,3);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertTrue(licenseManager.ThroughputCheck());
        }
		
		//case5
		licenseManager.ThroughputInit(40,1000,20);
		for (int i = 0;i<20;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertTrue(licenseManager.ThroughputCheck());
        }
		 
	}

	@Test
	public void testCapacityCheck() {
		//case 1
		licenseManager.CapacityInit(10, 0);
		 for (int i = 0; i<10;i++)
	        {
			 assertTrue(licenseManager.CapacityCheck());
	        }
		 
		 //case 2
		 licenseManager.CapacityInit(20, 0);
		 for (int i = 0; i<10;i++)
	        {
			 assertTrue(licenseManager.CapacityCheck());
	        }
		 
		 //case 3
		 licenseManager.CapacityInit(20, 3);
		 for (int i = 0; i<10;i++)
	        {
			 assertTrue(licenseManager.CapacityCheck());
	        }
		 
		 //case 4
		 licenseManager.CapacityInit(10, 5);
		 for (int i = 0; i<5;i++)
	        {
			 assertTrue(licenseManager.CapacityCheck());
	        }

		 
	}

}
