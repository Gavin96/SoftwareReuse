package Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSONObject;

public class Configuration {
	//public static String path = "C:\\sets";
	
	public static String getConfig(String path, String id){
		String sets=ReadFile(path);//获得json文件的内容
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return jo.getString(id);
	}
	
	/*public static String getPort(){
		String sets=ReadFile(path);//获得json文件的内容
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return jo.getString("port");

	}
	public static String getTimeGap(){
		String sets=ReadFile(path);//获得json文件的内容
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return jo.getString("timeGap");
	}
	public static String getMaxRequestTimes(){
		String sets=ReadFile(path);//获得json文件的内容
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return jo.getString("maxRequestTimes");
	}
	public static String getPath(){
		String sets=ReadFile(path);//获得json文件的内容
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return jo.getString("path");
	}

	//提供查询接口
	public static String getConfigurationFileInfo(){
		String sets=ReadFile(path);//获得json文件的内容
		//System.out.println(sets);
		JSONObject jo=JSONObject.fromObject(sets);//格式化成json对象

		return "端口:"+jo.getString("port")+" 时间间隔:"+jo.getString("timeGap")+" 最大请求次数:"+jo.getString("maxRequestTimes")+" 文件存储路径:"+jo.getString("path");
	}*/

	public static String ReadFile(String path){
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//int line = 1;
			//一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				//显示行号
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