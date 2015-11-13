package cn.edu.swu.lab1010.tagHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 这个类要完成对需要匹配的文章的一些操作
 * @author csd
 *
 */
public class FileDao {

	private String filePath;

	public FileDao(String filePath) {
		this.filePath = filePath;
	}

	public final StringBuilder read() throws IOException {
		BufferedReader reader = new BufferedReader(
									new FileReader(
										new File(filePath)));
		StringBuilder stringBuilder = new StringBuilder();
		String strLine;
		while(true){
			strLine = reader.readLine();
			if (strLine.equals(null)) 
				break;
			stringBuilder.append(reader.readLine());
		}
		return stringBuilder;
	}
	
	
}
