package cn.edu.swu.lab1010.matchHandler;

import java.util.HashSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.function.library.max;
import org.apache.jena.vocabulary.RDFS;

import cn.edu.swu.lab1010.dataHolder.FatherOrSonData;
import cn.edu.swu.lab1010.dataHolder.SelfData;
import cn.edu.swu.lab1010.mainTes.Data;

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
	
	public final HashSet<SelfData> searchSelfByLabel(String label) {
		Data.setStaticMappedString(label);
		HashSet<SelfData> resultSet = new HashSet<SelfData>();
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
//			StmtIterator preStmtIter = this.getPre(self);
		}
		return resultSet;
		
	}
	
	public final HashSet<FatherOrSonData> searchFatherOrSonData(StmtIterator preOrPostStmtIter) {
		HashSet<FatherOrSonData> resultSet = new HashSet<>();
		while (preOrPostStmtIter.hasNext()) {
			//得到前驱资源
			Statement preStmt = preOrPostStmtIter.nextStatement();
			Resource preOrPostRes = preStmt.getSubject();
			//得到属性值为rdfs:label的陈述，加入判断避免空指针异常
			Statement labelStmt = preOrPostRes.getProperty(RDFS.label);
			String selfLabel;
			if (labelStmt!=null) {
				selfLabel = labelStmt.getObject().toString(); 
			}else {
				selfLabel = new String("null");
			}
			String uriStr = preOrPostRes.getURI();
			//新建一个FatherOrSonData类的对象来存储这条数据
			FatherOrSonData aFatherOrSonData = new FatherOrSonData(uriStr, selfLabel);
			//将前驱的信息存放在ResultData对象中，并放进resultSet中
			
			//得到前驱的前驱并遍历
		}
		
		
		return null;
	}
	
//	/**
//	 * 传入匹配成功的label，返回由其对应的资源和其前驱，前驱的前驱，后继组成的jena Model
//	 * 
//	 * @param String
//	 * @return List<ResultData>
//	 * @throws Exception 
//	 */
//	public final HashSet<Data> searchByLabel(String label) {
//		//将resultData类的静态变量MappedString设置成label
//		//在下面找到的所有数据的直接匹配都是这个label
//		
//		Data.setStaticMappedString(label);
//		HashSet<Data> resultSet = new HashSet<Data>();
//		StmtIterator selfStmtIter = model.listStatements(new SimpleSelector(null, null, label));
//		
//		
//		while (selfStmtIter.hasNext()) {
//			//得到直接匹配的资源
//			Statement selfStmt = selfStmtIter.nextStatement();
//			Resource self = selfStmt.getSubject();
//			
//			//将直接匹配的信息存放在ResultData对象中，并放进resultSet中
////			String selfLabel = self.getProperty(RDFS.label).getObject().toString();
////			String uriStr = self.getURI();
////			ResultData selfData = new ResultData(0, uriStr, selfLabel);
//			Data selfData = this.extractDataFromResToResultData(self, 0);
//			resultSet.add(selfData);
//			
//			//得到前驱并遍历
//			StmtIterator preStmtIter = this.getPre(self);
//			while (preStmtIter.hasNext()) {
//				//得到前驱资源
//				Statement preStmt = preStmtIter.nextStatement();
//				Resource preRes = preStmt.getSubject();
//				
//				//将前驱的信息存放在ResultData对象中，并放进resultSet中
//				Data preData = this.extractDataFromResToResultData(preRes, 1);
//				resultSet.add(preData);
//				
//				//得到前驱的前驱并遍历
//				StmtIterator prePreStmtIter = this.getPre(preRes);
//				while (prePreStmtIter.hasNext()) {
//					//得到前前驱资源
//					Statement prePreStmt = prePreStmtIter.nextStatement();
//					Resource prePreRes = prePreStmt.getSubject();
//					//将前前驱的信息存放在ResultData对象中，并放进resultSet中
//					Data prePreData = this.extractDataFromResToResultData(prePreRes, 2);
//					resultSet.add(prePreData);
//					
//					
//				}
//			}
//			StmtIterator subStmtIter = this.getSub(self);
//			while (subStmtIter.hasNext()) {
//				//得到后驱资源
//				Statement subStmt = subStmtIter.nextStatement();
//				RDFNode subNode =subStmt.getObject();
//				//将后驱的信息存放在ResultData对象中，并放进resultSet中
//				if (subNode instanceof Resource) {
//					Data subData = this.extractDataFromResToResultData((Resource)subNode, 1);
//					resultSet.add(subData);
//				}
//			}
//		}
//		return resultSet;
//	}
//
//
//	/**
//	 * 得到某一个资源的前驱,返回该资源作为宾语的陈述迭代器
//	 * 
//	 * @param Resource
//	 * @return StmtIterator
//	 */
//	private final StmtIterator getPre(Resource res) {
//
//		StmtIterator preStmtIter = model.listStatements(new SimpleSelector(null, null, res));
//		return preStmtIter;
//	}
//
//	/**
//	 * 得到某一个资源的后继，返回该资源作为主语的陈述迭代器
//	 * 
//	 * @param Resource
//	 * @return StmtIterator
//	 */
//	private final StmtIterator getSub(Resource res) {
//		StmtIterator subStmtIter = model.listStatements(new SimpleSelector(res, null, (RDFNode) null));
//		return subStmtIter;
//	}
//
//	/**
//	 * 返回该对象处理的模型
//	 * 
//	 * @return (jena.)Model
//	 */
//	public Model getModel() {
//		return model;
//	}
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

