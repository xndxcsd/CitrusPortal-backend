package cn.edu.swu.lab1010.mainTes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import org.apache.jena.rdf.model.Model;

import cn.edu.swu.lab1010.dataHolder.FatherData;
import cn.edu.swu.lab1010.dataHolder.GrandPaData;
import cn.edu.swu.lab1010.dataHolder.SelfData;
import cn.edu.swu.lab1010.dataHolder.SonData;
import cn.edu.swu.lab1010.fileHanlder.FileReader;
import cn.edu.swu.lab1010.fileHanlder.RdfParser;
import cn.edu.swu.lab1010.matchHandler.MatchInFile;
import cn.edu.swu.lab1010.matchHandler.MatchInRdf;
import cn.edu.swu.lab1010.xmlWriter.XmlWriter;

public class Test {
	
	
	private static String TAGFILEPATH = "C:\\Users\\csd\\Desktop\\CitrusTest1.1_modified杜.rdf";
	private static String FILEPATH = "C:\\Users\\csd\\Desktop\\lab1010\\柑桔实用栽培技术.doc";
	public static void main(String[] args) throws Exception {
		String as = null;
		String sa=null;
		
		System.out.println("objects.equals : "+Objects.equals(as, sa));
		System.out.println(" == :"+as==sa);
		
		
		long startime = System.currentTimeMillis();//figure time
		
		RdfParser rdfdao = new RdfParser(TAGFILEPATH);//RDF source;
		Model model = rdfdao.getModel();//得到模型
		FileReader filedao = new FileReader(FILEPATH);//Match source;
		
		HashSet<String> labelSet = rdfdao.listObjectWithlabel();

		try {
			StringBuilder stringBuilder = filedao.read();
			for (String label : labelSet) {
				MatchInFile matcher = new MatchInFile(label, stringBuilder);
				if (matcher.match()) {
					//	debug:
					System.out.println("此次匹配是成功的");
					//每一个 label匹配的数据都放在一个ArrayList中
					int startOfMatch = matcher.getStartOfMatch();
					int endOfMatch = matcher.getEndOfMatch();
					String stringOfMatch = matcher.getStringOfMatch();
					//新建匹配
					MatchInRdf matchInRdf = new MatchInRdf(label, startOfMatch, endOfMatch, stringOfMatch, model);					
					//执行匹配
					matchInRdf.searchByLabel();
					ArrayList<SonData> sonList = matchInRdf.getSonList();
					ArrayList<SelfData> selfList = matchInRdf.getSelfList();
					ArrayList<FatherData> fatherList = matchInRdf.getFatherList();
					ArrayList<GrandPaData> grandPaList = matchInRdf.getGrandPaList();
					
					XmlWriter xmlWriter = new XmlWriter(sonList, selfList, fatherList, grandPaList);
					xmlWriter.write();
					//	debug: 只循环一次	
//					break;
				}
//				else System.out.println("此次匹配是不成功的");
				//else watch一下匹配不成功的情况
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
