package extent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveMsgtoFile {
	
	public static void SavetoFile(String path,String FileName,String msg) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path+FileName), true));
		writer.write(msg+"\r\n");
		writer.close();
	}
	
	public static void main(String[] args) throws IOException{
		SaveMsgtoFile.SavetoFile("E:\\", "test.txt", "5");
		SaveMsgtoFile.SavetoFile("E:\\", "test.txt", "6");
		SaveMsgtoFile.SavetoFile("E:\\", "test.txt", "7");
	}
}
