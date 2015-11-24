package cn.edu.swu.lab1010.mainTes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;

import cn.edu.swu.lab1010.fileHanlder.FileReader;
import cn.edu.swu.lab1010.fileHanlder.RdfParser;
import cn.edu.swu.lab1010.matchHandler.MatchInFile;
import cn.edu.swu.lab1010.xmlWriter.XmlWriter;

public class Test {
	
	
	private static String TAGFILEPATH = "C:\\Users\\Administrator\\Desktop\\实验室数据与文件\\CitrusTest1.3_modified.rdf";
	private static String FILEPATH = "C:\\Users\\Administrator\\Desktop\\实验室数据与文件\\desTest.rdf";
	public static void main(String[] args) throws Exception {
		long startime = System.currentTimeMillis();//figure time
		
		RdfParser rdfdao = new RdfParser(TAGFILEPATH);//RDF source;
		FileReader filedao = new FileReader(FILEPATH);//Match source;
		
		HashSet<String> labelSet = rdfdao.listObjectWithlabel();

		try {
			StringBuilder stringBuilder = filedao.read();
			for (String label : labelSet) {
				MatchInFile matcher = new MatchInFile(label, stringBuilder);
				if (matcher.match()) {
					//	debug:
					System.out.println("此次匹配是成功的");
					//每一个 label匹配的数据都放在一个HashSet中
					HashSet<Data> resultSet = rdfdao.searchByLabel(label);
					int startOfMatch = matcher.getStartOfMatch();
					int endOfMatch = matcher.getEndOfMatch();
					String stringOfMatch = matcher.getStringOfMatch();
					
					
					//	debug:			
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("错误的文章地址");
		}
		long end = System.currentTimeMillis();
		System.out.println("搜索一次标签所花费时间为 : "+(end-startime));
		
	}
}
