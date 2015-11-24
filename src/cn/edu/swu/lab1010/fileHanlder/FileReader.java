package cn.edu.swu.lab1010.fileHanlder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 这个类要完成对需要匹配的文章的一些操作
 * @author csd
 *
 */
public class FileReader {

	private String filePath;

	public FileReader(String filePath) {
		this.filePath = filePath;
	}

	public final StringBuilder read() throws IOException {
		BufferedReader reader = new BufferedReader(
									new InputStreamReader(
										new FileInputStream(filePath),"UTF-8"));
		
		StringBuilder stringBuilder = new StringBuilder();
		String strLine;
		while(true){
			strLine = reader.readLine();
			if (null==strLine) 
				break;
			stringBuilder.append(strLine);
		}
		reader.close();
//		System.out.println(stringBuilder.toString());
		
		return stringBuilder;
	}
	
	
}
