package com.HaroldLIU;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Test;

public class PerformanceManagerTest {



	@Test
	public void testStart() throws InterruptedException, IOException {
		long delay=1000;
		int count=10;
		PerformanceManager performanceManager = new PerformanceManager("E:\\",  delay);
        performanceManager.start();
        performanceManager.successTime = 10;
        performanceManager.failTime=5;
        
        for(int i=0;i<count;i++){
        	String currentMins = new SimpleDateFormat("yyyy_MM_dd HH mm ss").format(Calendar.getInstance().getTime());
            System.out.println(currentMins);
            Thread.sleep(delay);
            File file = new File("E:\\"+currentMins+"Report.txt");
            Reader reader=new InputStreamReader(new FileInputStream(file));
            int tempchar;
            
            String text="";
            while ((tempchar = reader.read()) != -1) {
               text+=(char)tempchar;
            }
            
            assertEquals(text,
            		"Valid Time:"+performanceManager.successTime + "\t" +"Invalid Time:"+ performanceManager.failTime + "\t" +"Total:"+(performanceManager.successTime+performanceManager.failTime)+"\t"+"Date:"+ currentMins + "\n");

            reader.close();
        }
	}

	

}
