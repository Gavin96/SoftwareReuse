package clearfilefolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sun.jmx.snmp.tasks.ThreadService;

//import com.sun.jmx.snmp.tasks.ThreadService;

public class Clear {
	
	 public static double getDirSize(File file) {     
	        //判断文件是否存在     
	        if (file.exists()) {     
	            //如果是目录则递归计算其内容的总大小    
	            if (file.isDirectory()) {     
	                File[] children = file.listFiles();     
	                double size = 0;     
	                for (File f : children)     
	                    size += getDirSize(f);     
	                return size;     
	            } else {//如果是文件则直接返回其大小,以“KB”为单位   
	                double size = (double) file.length() / 1024;        
	                return size;     
	            }     
	        } else {     
	            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");     
	            return 0.0;     
	        }     
	    }
	 
	 
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	
	
	 public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	            // delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	 
	 
	 public static void main(String[] args) throws IOException{
		 
			final long timeInterval = 60000;// 一分钟运行一次,因为文件的大小以及文件夹的大小变换频率至多每分钟一次
			final ThreadService threadService = new ThreadService(100);
			Runnable runnable = new Runnable() {
				
				public void run() {					
					while (true) {						
						
						double foldersize = Clear.getDirSize(new File("I:\\ForReuse"));
				    	System.out.println(foldersize);
				    	//long k = Long.parseLong(ReadJson.GetConfig("file folder size", "sets.txt"));
						if(foldersize>400){//当文件夹尺寸大于400KB时候清空文件夹
					      Clear.delFolder("I:\\ForReuse");
					      System.out.println("delete the filefolder");
					    }
						String fileName=new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
						String s="Server"+fileName+"ReceivedMsgRecord.txt";
						double filesize = Clear.getDirSize(new File("I:\\ForReuse\\"+s));
						try{
							
							//String fileName=new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
//							String s="Server"+fileName+"ReceivedMsgRecord.txt";
							//Long.parseLong(ReadJson.GetConfig("file size", "sets.txt"))
							//if(filesize>0.012){//测试的时候使用的
							if(filesize>12){//当当天储存消息的txt文件大小大于12KB时候清空该文件
						
							File f = new File("I:\\ForReuse\\"+s);
							FileWriter fw =  new FileWriter(f);
							//给指定文件中写入空字符，等同于清空文件
							fw.write("");
							fw.close();
							System.out.println("empty the txtfile");
						 }
						}catch (Exception ex) {
							ex.printStackTrace();
						}
						try {
							Thread.sleep(timeInterval);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			Thread thread = new Thread(runnable);
			thread.start();
		}
}
