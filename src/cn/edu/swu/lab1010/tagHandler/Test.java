package cn.edu.swu.lab1010.tagHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;

public class Test {
	
	
	private static String TAGFILEPATH = "C:\\Users\\csd\\Desktop\\CitrusTest1.1_modified杜.rdf";
	private static String FILEPATH = "C:\\Users\\csd\\Desktop\\testfile.txt";
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		RdfDao rdfdao = new RdfDao(TAGFILEPATH);
		FileDao filedao = new FileDao(FILEPATH);
		
		HashSet<String> labelSet = rdfdao.listObjectWithlabel();

		try {
			StringBuilder stringBuilder = filedao.read();
			for (String label : labelSet) {
				RdfMatch matcher = new RdfMatch(label, stringBuilder);
				if (matcher.match()) {
					//	debug:
					System.out.println("此次匹配是成功的");
					HashSet<ResultData> resultSet = rdfdao.searchByLabel(label);
					XmlWriter writer = new XmlWriter(matcher.addLocation(resultSet));
	
					//	debug:			
					writer.printToConsole();
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("错误的文章地址");
		}
		long end = System.currentTimeMillis();
		System.out.println("搜索一次标签所花费时间为 : "+(end-start));
		
	}
}
