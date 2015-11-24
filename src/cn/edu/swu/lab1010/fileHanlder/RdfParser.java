package cn.edu.swu.lab1010.fileHanlder;

import java.io.InputStream;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.function.library.e;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

import cn.edu.swu.lab1010.mainTes.Data;
import cn.edu.swu.lab1010.matchHandler.MatchInFile;

/**
 * @author csd
 */
public class RdfParser {

	Model model;

	/**
	 * 传入文件路径，将该rdf文件读入jena model中，以便后续的操作。
	 * 
	 * @param filePath
	 */
	public RdfParser(String filePath) {
		// TODO Auto-generated constructor stub

		this.model = ModelFactory.createDefaultModel();

		InputStream in = FileManager.get().open(filePath);
		if (null == in) {
			throw new IllegalArgumentException("File :" + filePath + "not found");
		}
		model.read(in, null);
	}
	
	public RdfParser(String filePath,MatchInFile matcher) {
		// TODO Auto-generated constructor stub
		
		this.model = ModelFactory.createDefaultModel();
		
		InputStream in = FileManager.get().open(filePath);
		if (null == in) {
			throw new IllegalArgumentException("File :" + filePath + "not found");
		}
		model.read(in, null);
	}
	

	/**
	 * 返回迭代标签值的迭代器
	 * 
	 * @param model
	 * @return NodeIterator
	 */
	public final HashSet<String> listObjectWithlabel() {

		if (this.model.isEmpty()) {
			throw new IllegalArgumentException("" + model + "is not exist");
		}

		NodeIterator iter = model.listObjectsOfProperty(RDFS.label);
		// 测试该迭代器是否正常
		// while (iter.hasNext()) {
		// RDFNode object = iter.nextNode();
		//
		// /*
		// * 如果一个属性的标签是一个资源怎么办?
		// *
		// * if (object instanceof Resource)
		// * System.out.println(object.toString());
		// *
		// * 测试中并没有发生一个属性的标签是一个资源的情况
		// */
		//
		// /*
		// * 几乎所有的标签值都是文本，并且将资源URI展现在用户面前并没有任何意义，此处直接过滤掉
		// */
		// if (object instanceof Literal)
		// System.out.println("\""+object.toString()+"\"");;
		//
		// }

		// 将NodeIterator中的结点遍历并转换到规则集（set）中
		HashSet<String> labelSet = new HashSet<>();
		while (iter.hasNext()) {
			RDFNode node = iter.nextNode();
			labelSet.add(node.toString());
		}

		// for (String string : labelSet) {
		// System.out.println(string);
		// }

		// 返回该规则集
		return labelSet;
	}

	/**
	 * 传入匹配成功的label，返回由其对应的资源和其前驱，前驱的前驱，后继组成的jena Model
	 * 
	 * @param String
	 * @return List<ResultData>
	 * @throws Exception 
	 */
	public final HashSet<Data> searchByLabel(String label) {
		//将resultData类的静态变量MappedString设置成label
		//在下面找到的所有数据的直接匹配都是这个label
		
		Data.setStaticMappedString(label);
		HashSet<Data> resultSet = new HashSet<Data>();
		StmtIterator selfStmtIter = model.listStatements(new SimpleSelector(null, null, label));
		
		
		while (selfStmtIter.hasNext()) {
			//得到直接匹配的资源
			Statement selfStmt = selfStmtIter.nextStatement();
			Resource self = selfStmt.getSubject();
			
			//将直接匹配的信息存放在ResultData对象中，并放进resultSet中
//			String selfLabel = self.getProperty(RDFS.label).getObject().toString();
//			String uriStr = self.getURI();
//			ResultData selfData = new ResultData(0, uriStr, selfLabel);
			Data selfData = this.extractDataFromResToResultData(self, 0);
			resultSet.add(selfData);
			
			//得到前驱并遍历
			StmtIterator preStmtIter = this.getPre(self);
			while (preStmtIter.hasNext()) {
				//得到前驱资源
				Statement preStmt = preStmtIter.nextStatement();
				Resource preRes = preStmt.getSubject();
				
				//将前驱的信息存放在ResultData对象中，并放进resultSet中
				Data preData = this.extractDataFromResToResultData(preRes, 1);
				resultSet.add(preData);
				
				//得到前驱的前驱并遍历
				StmtIterator prePreStmtIter = this.getPre(preRes);
				while (prePreStmtIter.hasNext()) {
					//得到前前驱资源
					Statement prePreStmt = prePreStmtIter.nextStatement();
					Resource prePreRes = prePreStmt.getSubject();
					//将前前驱的信息存放在ResultData对象中，并放进resultSet中
					Data prePreData = this.extractDataFromResToResultData(prePreRes, 2);
					resultSet.add(prePreData);
					
					
				}
			}
			StmtIterator subStmtIter = this.getSub(self);
			while (subStmtIter.hasNext()) {
				//得到后驱资源
				Statement subStmt = subStmtIter.nextStatement();
				RDFNode subNode =subStmt.getObject();
				//将后驱的信息存放在ResultData对象中，并放进resultSet中
				if (subNode instanceof Resource) {
					Data subData = this.extractDataFromResToResultData((Resource)subNode, 1);
					resultSet.add(subData);
				}
			}
		}
		return resultSet;
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
	private Data extractDataFromResToResultData(Resource res, int relation) {
		// TODO Auto-generated method stub
		//selfLabel 存放的是res这个资源的label
		String selfLabel;
		if (null != res.getProperty(RDFS.label))
			selfLabel = res.getProperty(RDFS.label).getObject().toString();
		else 
			selfLabel = "null";
		String uriStr = res.getURI();
		Data resData = new Data(relation, uriStr, selfLabel);
		return resData;
		
	}
//
//	public ResultData extractDataFromResToResultData(Resource res,int relation) {
//		String selfLabel = res.getProperty(RDFS.label).getObject().toString();
//		String uriStr = res.getURI();
//		ResultData resData = new ResultData(relation, uriStr, selfLabel);
//		
//		return resData;
//	}
	
//	public ResultData extractDataFromResToResultData(Resource res,int relation,int row,int position) {
//		String selfLabel = res.getProperty(RDFS.label).getObject().toString();
//		String uriStr = res.getURI();
//		ResultData aData = new ResultData(0, uriStr, selfLabel);
//		
//		return null;
//	}
//	
	
	
	
}
