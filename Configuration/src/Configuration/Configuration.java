package Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSONObject;

public class Configuration {
	public static String path = "C:\\sets";
	
	public static String getPort(){
		String sets=ReadFile(path);//���json�ļ�������
		JSONObject jo=JSONObject.fromObject(sets);//��ʽ����json����
		
		return jo.getString("port");

	}
	public static String getTimeGap(){
		String sets=ReadFile(path);//���json�ļ�������
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//��ʽ����json����
		
		return jo.getString("timeGap");
	}
	public static String getMaxRequestTimes(){
		String sets=ReadFile(path);//���json�ļ�������
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//��ʽ����json����
		
		return jo.getString("maxRequestTimes");
	}
	public static String getPath(){
		String sets=ReadFile(path);//���json�ļ�������
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//��ʽ����json����
		
		return jo.getString("path");
	}
	
	//�ṩ��ѯ�ӿ�
	public static String getConfigurationFileInfo(){
		String sets=ReadFile(path);//���json�ļ�������
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//��ʽ����json����
		
		return "�˿�:"+jo.getString("port")+" ʱ����:"+jo.getString("timeGap")+" ����������:"+jo.getString("maxRequestTimes")+" �ļ��洢·��:"+jo.getString("path");
	}
	
	public static String ReadFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	     //System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
	     reader = new BufferedReader(new FileReader(file));
	     String tempString = null;
	     //int line = 1;
	     //һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	     while ((tempString = reader.readLine()) != null) {
	      //��ʾ�к�
	      //System.out.println("line " + line + ": " + tempString);
	      laststr = laststr + tempString;
	      //line++ ;
	     }
	     reader.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } catch (IOException e1) {
	      }
	     }
	    }
	    return laststr;
	}

}
