package com.HaroldLIU;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
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
	private static long begin;
	private static long beginZipSpace;
	private static long beginZipFiled;
	private static long zipSpaceTime;
	private static long zipFiledTime;

	public static long getBegin() {
		return begin;
	}
	public static void setBegin(long begin) {
		PerformanceManager.begin = begin;
		PerformanceManager.beginZipSpace = begin;
		PerformanceManager.beginZipFiled = begin;
		
	}
	public static long getBeginZipSpace() {
		return beginZipSpace;
	}
	public static void setBeginZipSpace(long beginZipSpace) {
		PerformanceManager.beginZipSpace = beginZipSpace;
	}
	public static long getBeginZipFiled() {
		return beginZipFiled;
	}
	public static void setBeginZipFiled(long beginZipFiled) {
		PerformanceManager.beginZipFiled = beginZipFiled;
	}
	public static long getZipSpaceTime() {
		return zipSpaceTime;
	}
	public static void setZipSpaceTime(long zipSpaceTime) {
		PerformanceManager.zipSpaceTime = zipSpaceTime;
	}
	public static long getZipFiledTime() {
		return zipFiledTime;
	}
	public static void setZipFiledTime(long zipFiledTime) {
		PerformanceManager.zipFiledTime = zipFiledTime;
	}
	
    private class WriterTask extends TimerTask {
        //д���ļ�
        private  boolean writeToFile(int successTime,int failTime) {
        	String timeCheck=new SimpleDateFormat("HH_mm").format(Calendar.getInstance().getTime());
        	Calendar now=Calendar.getInstance();
        	long date=now.getTimeInMillis();
			  long zipspace=(date-beginZipSpace)/1000;
        	if(zipspace==zipSpaceTime){
        		beginZipSpace=date;
        		//System.out.println("zipPath: "+zipPath+ " name: "+zipName);
        		//ÿ�ܹ鵵�����գ���һ���ܶ������������ģ����壬����
        		long zipfiled=(date-beginZipFiled)/1000;
            	if(zipfiled==zipFiledTime){
            		zipFiledTime=date;
            		weeklyZip();
            		recurDelete(new File(zipPath+"\\data\\"));
            		
            	}
        		
        		zip(path,zipName,zipPath);
        		recurDelete(new File(path));
                setPath(oldPath);
        	}
        	
            String currentMins = new SimpleDateFormat("yyyy_MM_dd HH mm ss").format(Calendar.getInstance().getTime());
            String needToWrite = "Valid Time:"+successTime + "\t" +"Invalid Time:"+ failTime + "\t" +"Total:"+(successTime+failTime)+"\t"+"Date:"+ currentMins + "\n";
            try {
            	 //System.out.println(path);
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
    
    public void weeklyZip(){
    	String [] fileName = getFileName(zipPath);
		int i=0;
		String start="",end="";
        for(String name:fileName)
        {
           if(name.indexOf(".zip")!=-1){
        	   if(i==0) start=name.substring(0,name.indexOf('.'));
        	   end=name.substring(0,name.indexOf('.'));
        	   i++;
        	   unzip(zipPath+name,zipPath+"\\data\\"); //��ѹ7��ÿ��ѹ����
           }
        }
       
        zip(zipPath+"\\data\\",start+"-"+end+".zip", zipPath);//�鵵Ϊ��ѹ����
		
	}
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
    
    public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}
    public static void setPath(String newPath)
    {
    	//��ʱδ�鵵����ļ�����ָ��·����yyyy_MM_dd�ļ����£��鵵ʱ��ѹ������ָ��·����
    	 String fileName = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
         zipName=fileName+".zip";
    	 path = newPath+fileName;
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
     * path: �ļ������ڵ�Ŀ¼���ļ���
     * delay: ���д��һ�� (��λΪ����)
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
     * zippath: ��Ҫ��ѹ�����ļ�Ŀ¼
     * unzipPath��ѹ�����Ŀ¼
     **/
    public static void unzip(String zipPath, String unzipPath) {  
        File warFile = new File(zipPath);  
        try {  
            
            BufferedInputStream bufferedInputStream = new BufferedInputStream(  
                    new FileInputStream(warFile));  
            ArchiveInputStream in = new ArchiveStreamFactory()  
                    .createArchiveInputStream(ArchiveStreamFactory.JAR,  
                            bufferedInputStream);  
            JarArchiveEntry entry = null;  
            
            while ((entry = (JarArchiveEntry) in.getNextEntry()) != null) {  
                if (entry.isDirectory()) {  
                    new File(unzipPath, entry.getName()).mkdir();  
                } else {  
                    OutputStream out = FileUtils.openOutputStream(new File(  
                            unzipPath, entry.getName()));  
                    IOUtils.copy(in, out);  
                    out.close();  
                }  
            }  
            in.close();  
        } catch (FileNotFoundException e) {  
            System.err.println("δ�ҵ�zip�ļ�");  
        } catch (ArchiveException e) {  
            System.err.println("��֧�ֵ�ѹ����ʽ");  
        } catch (IOException e) {  
            System.err.println("�ļ�д�뷢������");  
        }  
    }  
		 
    /**
     * path: ��Ҫѹ�����ļ�Ŀ¼
     * FileName: ѹ���ļ���
     * zipPath��ѹ�����Ŀ¼
     */
    public static void zip(String path,String FileName, String zipPath) { 
    	if(new File(path).exists()){
        File outFile = new File(zipPath+"\\"+FileName);  
        try {  
            outFile.createNewFile();    
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(  
                    new FileOutputStream(outFile));  
            ArchiveOutputStream out = new ArchiveStreamFactory()  
                    .createArchiveOutputStream(ArchiveStreamFactory.JAR,  
                            bufferedOutputStream);  
           /* if (path.charAt(path.length() - 1) != '/') {  
            	path += '/';  
            }  
  */
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
            
        } catch (IOException e) {  
            System.err.println("�����ļ�ʧ��");  
        } catch (ArchiveException e) {  
            System.err.println("��֧�ֵ�ѹ����ʽ");  
        }  
    	}
    }  
    /**
     * f��ɾ�����ļ���\�ļ�
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
	
	/**
     * ��ȡ��ǰ���������ڼ�<br>
     * 
     * @param dt
     * @return ��ǰ���������ڼ�
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    public static void main(String[] args) {
        PerformanceManager performanceManager = new PerformanceManager("E:\\","E:\\", 10);
       long begin=Calendar.getInstance().getTimeInMillis();
        performanceManager.setBeginZipSpace(begin);
        performanceManager.setZipSpaceTime(30);
        performanceManager.setBeginZipFiled(begin);
        performanceManager.setZipFiledTime(60);
        performanceManager.start();
        performanceManager.successTime = 12;
    }
}
