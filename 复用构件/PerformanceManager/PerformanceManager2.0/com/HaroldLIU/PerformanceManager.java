package com.HaroldLIU;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class PerformanceManager {

    public int successTime = 0, failTime = 0;
    private static  String path;
    private String zipPath;
	private final long delay;
	private static String zipName;
	private static String oldPath;

    private class WriterTask extends TimerTask {
        //写入文件
        private  boolean writeToFile(int successTime,int failTime) {
        	String timeCheck=new SimpleDateFormat("HH_mm").format(Calendar.getInstance().getTime());
        	if(timeCheck.equals("00_00")){
        		System.out.println("zipPath: "+zipPath+ " name: "+zipName);
        		zip(zipName,zipPath);
        	}
            String currentMins = new SimpleDateFormat("yyyy_MM_dd HH mm ss").format(Calendar.getInstance().getTime());
            String needToWrite = "Valid Time:"+successTime + "\t" +"Invalid Time:"+ failTime + "\t" +"Total:"+(successTime+failTime)+"\t"+"Date:"+ currentMins + "\n";
            try {
            	 System.out.println(path);
                FileWriter writer = new FileWriter(path+"\\"+currentMins+"Report.txt", true);
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
    
    public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}
    public static void setPath(String newPath)
    {
    	//临时未归档输出文件放入指定路径的yyyy_MM_dd文件夹下，归档时，压缩包在指定路径下
    	 String fileName = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
         zipName=fileName+".zip";
    	 path = newPath+"\\"+fileName;
        File file =new File(path); 
        if(!file.exists()&& !file .isDirectory()){
        	 file.mkdir(); 
        	 
        }
    }

    public PerformanceManager(String _path,String _zipPath,long _delay)
    {
        setPath(_path);
        oldPath=_path;
        zipPath=_zipPath;
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
    /**
     * FileName: 压缩文件名
     * zipPath：压缩存放目录
     */
    public static void zip(String FileName, String zipPath) { 
    	if(new File(path).exists()){
        File outFile = new File(zipPath+"\\"+FileName);  
        try {  
            outFile.createNewFile();    
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(  
                    new FileOutputStream(outFile));  
            ArchiveOutputStream out = new ArchiveStreamFactory()  
                    .createArchiveOutputStream(ArchiveStreamFactory.JAR,  
                            bufferedOutputStream);  
            if (path.charAt(path.length() - 1) != '/') {  
            	path += '/';  
            }  
  
            Iterator<File> files = FileUtils.iterateFiles(new File(path),  
                    null, true);  
            while (files.hasNext()) {  
                File file = files.next();  
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file,  
                        file.getPath().replace(path.replace("/", "\\"), ""));  
                out.putArchiveEntry(zipArchiveEntry);  
                FileInputStream in =new FileInputStream(file);
                IOUtils.copy(in, out);  
                out.closeArchiveEntry();  
                in.close();
            }  
            out.finish();  
            out.close();  
            bufferedOutputStream.close();
            recurDelete(new File(path));
            setPath(oldPath);
        } catch (IOException e) {  
            System.err.println("创建文件失败");  
        } catch (ArchiveException e) {  
            System.err.println("不支持的压缩格式");  
        }  
    	}
    }  
    /**
     * f：删除的文件夹\文件
     */
	public static void recurDelete(File f){
	    for(File fi:f.listFiles()){
	        if(fi.isDirectory()){
	            recurDelete(fi);
	        }
	        else{
	            fi.delete();
	        }
	    }
	    f.delete();
	}
    public static void main(String[] args) {
        PerformanceManager performanceManager = new PerformanceManager("E:\\","E:\\", 5 * 1000);
        performanceManager.start();
        performanceManager.successTime = 12;
    }
}
