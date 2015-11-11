package cn.edu.swu.lab1010.tagHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.*;
import org.apache.jena.vocabulary.RDFS;

/**
 * @author Alexi
 */
public class RdfDao {

	/**
	 * read the rdf to a jena Model .
	 * 
	 * @return Model
	 */
	public final Model readTagRdf(String filePath) throws Exception {
		/*
		 * make the rdf to a jena model.TAGFILEPATH is a temporary filepath for
		 * test.
		 */

		Model model = ModelFactory.createDefaultModel();

		InputStream in = FileManager.get().open(filePath);
		if (null == in) {
			throw new IllegalArgumentException("File :" + filePath + "not found");
		}
		model.read(in, null);
		return model;
	}

	/**
	 * 返回迭代标签值的迭代器
	 * 
	 * @param model
	 * @retu NodeIterator
	 */
	public final NodeIterator listObjectWithlabel(Model model) {

		if (model.isEmpty()) {
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

		return iter;
	}

	
	
	
}
