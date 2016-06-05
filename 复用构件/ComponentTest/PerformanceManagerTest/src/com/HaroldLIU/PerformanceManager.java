package com.HaroldLIU;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PerformanceManager {

    public int successTime = 0, failTime = 0;
    private final String path;
    private final long delay;

    private class WriterTask extends TimerTask {
        //写入文件
        private  boolean writeToFile(int successTime,int failTime) {
            String currentMins = new SimpleDateFormat("yyyy_MM_dd HH mm ss").format(Calendar.getInstance().getTime());
            String needToWrite = "Valid Time:"+successTime + "\t" +"Invalid Time:"+ failTime + "\t" +"Total:"+(successTime+failTime)+"\t"+"Date:"+ currentMins + "\n";
            try {
                FileWriter writer = new FileWriter(path+currentMins+"Report.txt", true);
                writer.write(needToWrite);
                System.out.println(needToWrite);
                writer.close();
            } catch (final IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        public void run(){writeToFile(successTime,failTime);}
    }

    public PerformanceManager(String _path, long _delay)
    {
        path = _path;
        delay = _delay;
    }
    /**
     * path: 文件的所在的目录，文件夹
     * delay: 多久写入一次 (单位为毫秒)
     */
    public  void start()
    {
        Timer timer = new Timer("Write Performance");
        timer.cancel();
        timer = new Timer("Write Performance");
        WriterTask task = new WriterTask();
        Date executionDate = new Date();
        timer.scheduleAtFixedRate(task,executionDate,delay);
    }

    public static void main(String[] args) {

        PerformanceManager performanceManager = new PerformanceManager("/Users/Harold_LIU/Desktop/", 5 * 1000);
        performanceManager.start();
        performanceManager.successTime = 12;

    }
}
