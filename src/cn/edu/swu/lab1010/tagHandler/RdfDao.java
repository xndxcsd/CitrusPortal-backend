package cn.edu.swu.lab1010.tagHandler;

import java.io.InputStream;
import java.util.*;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

import com.github.jsonldjava.core.RDFDataset.BlankNode;

/**
 * @author csd
 */
public class RdfDao {

	Model model;

	/**
	 * 传入文件路径，将该rdf文件读入jena model中，以便后续的操作。
	 * 
	 * @param filePath
	 */
	public RdfDao(String filePath) {
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
	 */
	public final ArrayList<ResultData> searchByLabel(String label) {

		ArrayList<ResultData> resultList = new ArrayList<ResultData>();
		StmtIterator selfStmtIter = model.listStatements(new SimpleSelector(null, null, label));

		while (selfStmtIter.hasNext()) {
			Statement selfStmt = selfStmtIter.nextStatement();
			Resource self = selfStmt.getSubject();
			StmtIterator preStmtIter = this.getPre(self);
			while (preStmtIter.hasNext()) {
				Statement preStmt = preStmtIter.nextStatement();
				Resource preRes = preStmt.getSubject();
				StmtIterator prePreStmtIter = this.getPre(preRes);
				while (prePreStmtIter.hasNext()) {
					Statement prePreStmt = prePreStmtIter.nextStatement();
					Resource prePreRes = prePreStmt.getSubject();
					StmtIterator subStmtIter = this.getSub(self);
					while (subStmtIter.hasNext()) {
						Statement subStmt = subStmtIter.nextStatement();
						RDFNode subNode =subStmt.getObject();
												
						String selfLabel = " ";
						String preLabel = " ";
						String prePreLabel = " ";
						String subLabel = " ";
						if(selfStmt.getObject()!=null)
						selfLabel = selfStmt.getObject().toString();
						if(preRes.getProperty(RDFS.label).getObject()!=null)
						preLabel = preRes.getProperty(RDFS.label).getObject().toString();
						if(prePreRes.getProperty(RDFS.label).getObject()!=null)
						prePreLabel = prePreRes.getProperty(RDFS.label).getObject().toString();
						
						if(subNode==null) {
							subLabel =" ";
						}else if(subNode instanceof BlankNode) {
							subLabel = " ";
						}else if(subNode instanceof Resource) {
							Resource subRes = (Resource)subNode;
							subLabel = subRes.getProperty(RDFS.label).getObject().toString();
						}else 
							subLabel = subNode.toString();
						
						ResultData result = new ResultData(selfLabel, preLabel, prePreLabel, subLabel);
						resultList.add(result);
					}
				}
			}

		}
		return resultList;
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
}
