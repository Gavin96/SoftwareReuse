package com.HaroldLIU;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class test {
	public static String path;
	public static void unzip(String warPath, String unzipPath) {  
        File warFile = new File(warPath);  
        try {  
            //��������  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(  
                    new FileInputStream(warFile));  
            ArchiveInputStream in = new ArchiveStreamFactory()  
                    .createArchiveInputStream(ArchiveStreamFactory.JAR,  
                            bufferedInputStream);  
            JarArchiveEntry entry = null;  
            //ѭ��������ѹ  
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
            System.err.println("δ�ҵ�war�ļ�");  
        } catch (ArchiveException e) {  
            System.err.println("��֧�ֵ�ѹ����ʽ");  
        } catch (IOException e) {  
            System.err.println("�ļ�д�뷢������");  
        }  
    }  
		 
	
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
           // setPath(oldPath);
        } catch (IOException e) {  
            System.err.println("�����ļ�ʧ��");  
        } catch (ArchiveException e) {  
            System.err.println("��֧�ֵ�ѹ����ʽ");  
        }  
    	}
    }  
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
	public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
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
		public static void main(String[] args){
			test t=new test();
			t.path="E:\\test";
			//t.zip("test.zip","E:\\");
			//t.unzip("E:\\test.zip","E:\\a\\");File file =new File(path); 
			//System.out.println(t.getWeekOfDate(new Date()));
			String [] fileName = getFileName("E:\\");
			int i=0;
			String start="",end="";
	        for(String name:fileName)
	        {
	           if(name.indexOf(".zip")!=-1){
	        	   if(i==0) start=name.substring(0,name.indexOf('.'));
	        	   end=name.substring(0,name.indexOf('.'));
	        	   i++;
	        	   System.out.println("E:\\"+name);
	        	   t.unzip("E:\\"+name,"E:\\data\\"); 
	           }
	        }
	        t.path="E:\\data";
	        t.zip(start+"-"+end+".zip", "E:\\");
			
		}

}
