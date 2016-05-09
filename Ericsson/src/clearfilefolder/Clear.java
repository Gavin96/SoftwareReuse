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
	        //�ж��ļ��Ƿ����     
	        if (file.exists()) {     
	            //�����Ŀ¼��ݹ���������ݵ��ܴ�С    
	            if (file.isDirectory()) {     
	                File[] children = file.listFiles();     
	                double size = 0;     
	                for (File f : children)     
	                    size += getDirSize(f);     
	                return size;     
	            } else {//������ļ���ֱ�ӷ������С,�ԡ�KB��Ϊ��λ   
	                double size = (double) file.length() / 1024;        
	                return size;     
	            }     
	        } else {     
	            System.out.println("�ļ������ļ��в����ڣ�����·���Ƿ���ȷ��");     
	            return 0.0;     
	        }     
	    }
	 
	 
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //ɾ����������������
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //ɾ�����ļ���
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
	             delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
	            // delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
	             flag = true;
	          }
	       }
	       return flag;
	     }
	 
	 
	 public static void main(String[] args) throws IOException{
		 
			final long timeInterval = 60000;// һ��������һ��,��Ϊ�ļ��Ĵ�С�Լ��ļ��еĴ�С�任Ƶ������ÿ����һ��
			final ThreadService threadService = new ThreadService(100);
			Runnable runnable = new Runnable() {
				
				public void run() {					
					while (true) {						
						
						double foldersize = Clear.getDirSize(new File("I:\\ForReuse"));
				    	System.out.println(foldersize);
				    	//long k = Long.parseLong(ReadJson.GetConfig("file folder size", "sets.txt"));
						if(foldersize>400){//���ļ��гߴ����400KBʱ������ļ���
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
							//if(filesize>0.012){//���Ե�ʱ��ʹ�õ�
							if(filesize>12){//�����촢����Ϣ��txt�ļ���С����12KBʱ����ո��ļ�
						
							File f = new File("I:\\ForReuse\\"+s);
							FileWriter fw =  new FileWriter(f);
							//��ָ���ļ���д����ַ�����ͬ������ļ�
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
