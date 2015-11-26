package cn.edu.swu.lab1010.matchHandler;

import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDFS;

import cn.edu.swu.lab1010.dataHolder.Data;
import cn.edu.swu.lab1010.dataHolder.FatherData;
import cn.edu.swu.lab1010.dataHolder.GrandPaData;
import cn.edu.swu.lab1010.dataHolder.SelfData;
import cn.edu.swu.lab1010.dataHolder.SonData;

public class MatchInRdf {

	
	public MatchInRdf() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	private String label;
	private int start;
	private int end;
	private String MappedString;
	private Model model;
	
	/*
	 *	返回的HashSet 
	 */
	private ArrayList<SonData> sonList;
	private ArrayList<SelfData> selfList;
	private ArrayList<FatherData> fatherList;
	private ArrayList<GrandPaData> grandPaList;

	

	public ArrayList<SonData> getSonList() {
		return sonList;
	}




	public ArrayList<SelfData> getSelfList() {
		return selfList;
	}




	public ArrayList<FatherData> getFatherList() {
		return fatherList;
	}




	public ArrayList<GrandPaData> getGrandPaList() {
		return grandPaList;
	}




	/**	Label 表示直接匹配成功的标签
	 *	start 是匹配到的始位置
	 * 	end 是匹配到的末位置
	 * 	MappedString 是被匹配到字符串	
	 */ 
	public MatchInRdf(String label,int start,int end,String MappedString,Model model) {
		this.label = label;
		this.start = start;
		this.end = end;
		this.MappedString = MappedString;
		this.model = model;
	}
	
	
	
	
	private final void searchSelfByLabel(String label) {
		Data.setStaticMappedString(label);
		ArrayList<SelfData> resultSet = new ArrayList<SelfData>();
		StmtIterator selfStmtIter = model.listStatements(new SimpleSelector(null, null, label));
		while (selfStmtIter.hasNext()) {
			//得到直接匹配的资源
			Statement selfStmt = selfStmtIter.nextStatement();
			Resource self = selfStmt.getSubject();
			
			//将直接匹配的信息存放在SelfData对象中，并放进resultSet中
			String uriStr = self.getURI();
			SelfData aSelfData = new SelfData(uriStr, label, MappedString, start, end);
			resultSet.add(aSelfData);
			
//			//得到前驱并遍历
			StmtIterator preStmtIter = this.getPre(self);
			this.searchFatherData(preStmtIter);
			
			//得到后继并遍历
			StmtIterator sonStmtIter = this.getSub(self);
			this.searchSonData(sonStmtIter);
		}
		
		this.selfList = resultSet;
		
	}
	
	private final void searchFatherData(StmtIterator preStmtIter) {
		if (preStmtIter.equals(null)) return;
		ArrayList<FatherData> resultSet = new ArrayList<>();
		while (preStmtIter.hasNext()) {
			//得到前驱资源
			Statement preStmt = preStmtIter.nextStatement();
			Resource preRes = preStmt.getSubject();
			if (preRes == null) break;
			//得到属性值为rdfs:label的陈述，加入判断避免空指针异常
			Statement labelStmt = preRes.getProperty(RDFS.label);
			String selfLabel;
			if (labelStmt==null) {
				selfLabel ="label : null"; 
					
			}
			else {
				selfLabel = labelStmt.getObject().toString(); 
			}
			
			String uriStr = preRes.getURI();
			//新建一个FatherOrSonData类的对象来存储这条数据
			FatherData aFatherOrSonData = new FatherData(uriStr, selfLabel);
			//将前驱的信息存放在FatherData对象中，并放进resultSet中
			resultSet.add(aFatherOrSonData);
			//得到前驱的前驱并遍历
			StmtIterator prePreStmtIter = this.getPre(preRes);
			this.searchGrandPaData(prePreStmtIter);
		}
		this.fatherList = resultSet;
	}
	
	private final void searchGrandPaData(StmtIterator prePreStmtIter) {
		ArrayList<GrandPaData> resultSet = new ArrayList<>();
		while (prePreStmtIter.hasNext()) {
			//得到前驱资源
			Statement prePreStmt = prePreStmtIter.nextStatement();
			Resource prePreRes = prePreStmt.getSubject();
			//得到属性值为rdfs:label的陈述，加入判断避免空指针异常
			Statement labelStmt = prePreRes.getProperty(RDFS.label);
			String selfLabel;
			if (labelStmt!=null) {
				selfLabel = labelStmt.getObject().toString(); 
			}else {
				selfLabel = new String("null");
			}
			String uriStr = prePreRes.getURI();
			//新建一个FatherOrSonData类的对象来存储这条数据
			GrandPaData aGrandPaData = new GrandPaData(uriStr, selfLabel);
			//将前驱的前驱的信息存放在GrandData对象中，并放进resultSet中
			resultSet.add(aGrandPaData);
			//得到前驱的前驱并遍历
		}
		this.grandPaList = resultSet;
	}
	
	private final void searchSonData(StmtIterator sonStmtIter) {
		
		ArrayList<SonData> resultSet = new ArrayList<>();
		while (sonStmtIter.hasNext()) {
			Statement sonStmt = sonStmtIter.nextStatement();
			RDFNode sonNode =sonStmt.getObject();
			
			if (sonNode instanceof Resource) {
				Statement labelStmt = ((Resource) sonNode).getProperty(RDFS.label);
				String selfLabel;
				if (labelStmt!=null) {
					selfLabel = labelStmt.getObject().toString();
				}else {
					selfLabel = "null";
				}
				String uriStr = ((Resource) sonNode).getURI();
				SonData aSonData = new SonData(uriStr, selfLabel);
				
			}
			
		}
		this.sonList = resultSet;				
	}
	
	
	/**
	 * 传入匹配成功的label，返回由其对应的资源和其前驱，前驱的前驱，后继组成的jena Model
	 * 
	 * @param String
	 * @return List<ResultData>
	 * @throws Exception 
	 */
	public final void searchByLabel() {
		//调用searchSelfByLabel(label) 方法中有嵌套 ， 遍历前驱后继。
		this.searchSelfByLabel(this.label);
	}


	/**
	 * 得到某一个资源的前驱,返回该资源作为宾语的陈述迭代器
	 * 
	 * @param Resource
	 * @return StmtIterator
	 */
	private final StmtIterator getPre(Resource res) {

		StmtIterator preStmtIter = model.listStatements(new SimpleSelector(null, null, res));
		return preStmtIter;
	}

	/**
	 * 得到某一个资源的后继，返回该资源作为主语的陈述迭代器
	 * 
	 * @param Resource
	 * @return StmtIterator
	 */
	private final StmtIterator getSub(Resource res) {
		StmtIterator subStmtIter = model.listStatements(new SimpleSelector(res, null, (RDFNode) null));
		return subStmtIter;
	}

	/**
	 * 返回该对象处理的模型
	 * 
	 * @return (jena.)Model
	 */
	public Model getModel() {
		return model;
	}
//	private Data extractDataFromResToResultData(Resource res, int relation) {
//		// TODO Auto-generated method stub
//		//selfLabel 存放的是res这个资源的label
//		String selfLabel;
//		if (null != res.getProperty(RDFS.label))
//			selfLabel = res.getProperty(RDFS.label).getObject().toString();
//		else 
//			selfLabel = "null";
//		String uriStr = res.getURI();
//		Data resData = new Data(relation, uriStr, selfLabel);
//		return resData;
//		
//	}
//	
//	
//	
////
////	public ResultData extractDataFromResToResultData(Resource res,int relation) {
////		String selfLabel = res.getProperty(RDFS.label).getObject().toString();
////		String uriStr = res.getURI();
////		ResultData resData = new ResultData(relation, uriStr, selfLabel);
////		
////		return resData;
////	}
//	
////	public ResultData extractDataFromResToResultData(Resource res,int relation,int row,int position) {
////		String selfLabel = res.getProperty(RDFS.label).getObject().toString();
////		String uriStr = res.getURI();
////		ResultData aData = new ResultData(0, uriStr, selfLabel);
////		
////		return null;
////	}
////	
//	
//	
//	
//	
//	
//	
//	
	

	}

