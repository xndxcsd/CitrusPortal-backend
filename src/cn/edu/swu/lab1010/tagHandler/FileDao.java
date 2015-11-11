package cn.edu.swu.lab1010.tagHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileDao {

	private static String filePath;

	public FileDao(String filePath) {
		this.filePath = filePath;
	}

	protected final StringBuilder read() throws IOException {
		//给一个流读文件
		DataInputStream in = new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(new File(filePath))));
		//给一个字符串构造器将文件读入
		StringBuilder stringBuilder = new StringBuilder();
		String content="";
		while (content!=null) {
			
			
			if (content==null)
				break;
			
			
		}
		
		
		return null;
	}

}
