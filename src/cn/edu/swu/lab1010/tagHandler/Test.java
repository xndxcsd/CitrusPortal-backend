package cn.edu.swu.lab1010.tagHandler;

import org.apache.jena.rdf.model.Model;

public class Test {
	
	
	private static final String TAGFILEPATH = "C:\\Users\\Alexi\\Desktop" + "\\陈思定用工具\\CitrusTest1.3_modified.rdf";

	public static void main(String[] args) {
		RdfDao rdfdao = new RdfDao(TAGFILEPATH);
		Model model = null;
		try {
			//将储存着标签的rdf读到model中
			//得到迭代标签的迭代器，测试代码在方法体中
			//
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
