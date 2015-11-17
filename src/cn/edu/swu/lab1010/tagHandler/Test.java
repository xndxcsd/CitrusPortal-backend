package cn.edu.swu.lab1010.tagHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;

public class Test {
	
	
	private static String TAGFILEPATH = "C:\\Users\\csd\\Desktop\\CitrusTest1.1_modified杜.rdf";
	private static String FILEPATH = "C:\\Users\\csd\\Desktop\\testfile.txt";
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		RdfDao rdfdao = new RdfDao(TAGFILEPATH);
		FileDao filedao = new FileDao(FILEPATH);
		
		HashSet<String> labelSet = rdfdao.listObjectWithlabel();

		try {
			StringBuilder stringBuilder = filedao.read();
			for (String label : labelSet) {
				RdfMatch matcher = new RdfMatch(label, stringBuilder);
				if (matcher.match()) {
//					System.out.println("此次匹配是成功的");
					ArrayList<ResultData> resultList = rdfdao.searchByLabel(label);
					for (ResultData resultData : resultList) {//测试该列表中是否有数据
//						System.out.println("self : "+resultData.getSelf());
//						System.out.println("pre : "+resultData.getPre());
//						System.out.println("prepre : "+resultData.getPrepre());
//						System.out.println("sub : "+resultData.getSub());
					}
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
