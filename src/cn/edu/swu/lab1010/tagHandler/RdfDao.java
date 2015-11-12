package cn.edu.swu.lab1010.tagHandler;

import java.io.InputStream;
import java.util.HashSet;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

/**
 * @author Alexi
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
		// 返回该规则集
		return labelSet;
	}

	/**
	 * 传入匹配成功的label，返回由其对应的资源和其前驱，前驱的前驱，后继组成的jena Model
	 * @param label
	 */
	public final void searchByLabel(String label) {
		//resultModel用来存放查找的结果，这里我们将查找的结果重新构造成一张图
		Model resultModel = ModelFactory.createDefaultModel();
		//返回一个属性值为label的所有属性的迭代器
		ResIterator resIter = model.listResourcesWithProperty(RDFS.label, label);
		//将这个迭代器中的所有资源添加到resultModel中
		while (resIter.hasNext()){
			Resource res = resIter.next();
			
		}
		
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
