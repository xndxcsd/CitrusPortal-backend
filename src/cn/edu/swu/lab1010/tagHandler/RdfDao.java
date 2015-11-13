package cn.edu.swu.lab1010.tagHandler;

import java.io.InputStream;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

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

//		for (String string : labelSet) {
//			System.out.println(string);
//		}

		// 返回该规则集
		return labelSet;
	}

	/**
	 * 传入匹配成功的label，返回由其对应的资源和其前驱，前驱的前驱，后继组成的jena Model
	 * 
	 * @param String
	 * @return Model
	 */
	public final Model searchByLabel(String label) {
		// resultModel用来存放查找的结果，这里我们将查找的结果重新构造成一张图
		Model resultModel = ModelFactory.createDefaultModel();
		// 返回一个宾语为label的所有陈述的迭代器
		StmtIterator stmtIter = model.listStatements(new SimpleSelector(null, null, label));
		// 将其加入到resultModel中
		resultModel.add(stmtIter);
		// 得到前驱和前驱的前驱的循环
		while (stmtIter.hasNext()) {
			// 得到标签值为label的资源
			Resource res = stmtIter.nextStatement().getSubject();
			// 得到宾语为该资源的陈述，主语为前驱的迭代器，并将其加入resultModel中
			StmtIterator preStmtIter = this.getPre(res);
			resultModel.add(preStmtIter);
			// 遍历该迭代器
			while (preStmtIter.hasNext()) {
				// 重复以上操作得到存储前驱的前驱的陈述，将其加入到resultModel中
				Resource preRes = preStmtIter.nextStatement().getSubject();
				StmtIterator prePreIter = this.getPre(preRes);
				resultModel.add(prePreIter);
			}
		}
		// 得到后继的循环
		while (stmtIter.hasNext()) {
			Resource res = stmtIter.nextStatement().getSubject();
			StmtIterator subStmtIter = this.getSub(res);
			resultModel.add(subStmtIter);
		}

		return resultModel;
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
