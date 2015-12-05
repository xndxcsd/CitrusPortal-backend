package cn.edu.swu.lab1010.matchHandler;

import java.util.ArrayList;
import java.util.Objects;

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

/**
 * 通过传入匹配到的信息在RDF文件中搜索。
 * 
 * @author csd
 *
 */

/*
 * 很明显这是一个有所不足的类.
 * 
 * 整个搜索的思路相当的简单：搜索self，遍历self，搜索下一层...
 * 
 * 这个类的可扩展性和重用性希望能在以后的版本中得到改进。
 *
 */
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
	 * 下面的查找函数中为他们赋初值
	 */
	private ArrayList<SonData> sonList = null;
	private ArrayList<SelfData> selfList = null;
	private ArrayList<FatherData> fatherList = null;
	private ArrayList<GrandPaData> grandPaList = null;

	/*
	 * 通过getter方法得到存放着数据对象的ArrayList。
	 * 
	 * 在下面的方法中我们已经避免了ArrayList为空的情况，ArrayList要么为null，要么不为空。
	 */

	/**
	 * 得到sonList
	 * 
	 * @return ArrayList 非空的线性表 （表中无数据返回null,决不返回空表）
	 */
	public ArrayList<SonData> getSonList() {
		return sonList;
	}

	/**
	 * 得到selfList
	 * 
	 * @return ArrayList 非空的线性表 （表中无数据返回null,决不返回空表）
	 */
	public ArrayList<SelfData> getSelfList() {
		return selfList;
	}

	/**
	 * 得到fatherList
	 * 
	 * @return ArrayList 非空的线性表 （表中无数据返回null,决不返回空表）
	 */
	public ArrayList<FatherData> getFatherList() {
		return fatherList;
	}

	/**
	 * 得到grandpaList
	 * 
	 * @return ArrayList 非空的线性表 （表中无数据返回null,决不返回空表）
	 */
	public ArrayList<GrandPaData> getGrandPaList() {
		return grandPaList;
	}

	/**
	 * Label 表示直接匹配成功的标签 start 是匹配到的始位置 end 是匹配到的末位置 MappedString 是被匹配到字符串 model
	 * 标签库（RDF）的模型 （我们要在标签库中查找前驱或者后驱，需要传入 标签库的模型）
	 */
	public MatchInRdf(String label, int start, int end, String MappedString, Model model) {
		this.label = label;
		this.start = start;
		this.end = end;
		this.MappedString = MappedString;
		this.model = model;
	}

	/*
	 * 非常糟糕的方法。 在方法中嵌套了搜索前后驱的方法，让程序的执行效率变得很低。
	 * 
	 * 我们将的未来的版本中对他进行优化。
	 */

	private final void searchSelfByLabel(String label) {
		Data.setStaticMappedString(label);
		ArrayList<SelfData> resultList = new ArrayList<SelfData>();
		StmtIterator selfStmtIter = model.listStatements(new SimpleSelector(null, null, label));

		while (selfStmtIter.hasNext()) {
			// System.out.println(" in self :"+count++);

			// 得到直接匹配的资源
			Statement selfStmt = selfStmtIter.nextStatement();
			Resource self = selfStmt.getSubject();

			// 将直接匹配的信息存放在SelfData对象中，并放进resultSet中
			String uriStr = self.getURI();
			SelfData aSelfData = new SelfData(uriStr, label, MappedString, start, end);
			resultList.add(aSelfData);

			// //得到前驱并搜索
			StmtIterator preStmtIter = this.getPre(self);
			this.searchFatherData(preStmtIter);

			// 得到后继并搜索
			StmtIterator sonStmtIter = this.getSub(self);
			this.searchSonData(sonStmtIter);
		}

		this.selfList = resultList;

	}

	private final void searchFatherData(StmtIterator preStmtIter) {
		if (!preStmtIter.hasNext()) {
			// 如果没有father那我们什么都不做。
		} else {
			ArrayList<FatherData> resultList = new ArrayList<>();
			while (preStmtIter.hasNext()) {
				// 得到前驱资源
				Statement preStmt = preStmtIter.nextStatement();
				Resource preRes = preStmt.getSubject();

				// 得到属性值为rdfs:label的陈述，加入判断避免空指针异常
				Statement labelStmt = preRes.getProperty(RDFS.label);
				if (labelStmt != null) {
					if (preRes instanceof Resource) {
						String fatherLabel = labelStmt.getObject().toString();
						String uriStr = preRes.getURI();
						// 新建一个FatherOrSonData类的对象来存储这条数据
						FatherData aFatherOrSonData = new FatherData(uriStr, fatherLabel);
						// 将前驱的信息存放在FatherData对象中，并放进resultSet中
						resultList.add(aFatherOrSonData);
					}
				}
				// 得到前驱的前驱并遍历
				StmtIterator prePreStmtIter = this.getPre(preRes);
				this.searchGrandPaData(prePreStmtIter);
			}
			if (Objects.equals(this.fatherList, null)) {
				this.fatherList = resultList;
			} else {
				this.fatherList.addAll(resultList);
			}
		}
	}

	private final void searchGrandPaData(StmtIterator prePreStmtIter) {
		if (!prePreStmtIter.hasNext()) {
			// 如果没有grandPa 那么什么都不做
		} else {
			ArrayList<GrandPaData> resultSet = new ArrayList<>();
			while (prePreStmtIter.hasNext()) {
				// 得到前驱资源
				Statement prePreStmt = prePreStmtIter.nextStatement();
				Resource prePreRes = prePreStmt.getSubject();
				if (prePreRes instanceof Resource) {
					// 得到属性值为rdfs:label的陈述，加入判断避免空指针异常
					Statement labelStmt = prePreRes.getProperty(RDFS.label);
					String grandPaLabel;
					if (labelStmt != null) {
						grandPaLabel = labelStmt.getObject().toString();
					} else {
						grandPaLabel = new String("null");
					}
					String uriStr = prePreRes.getURI();
					// 新建一个FatherOrSonData类的对象来存储这条数据
					GrandPaData aGrandPaData = new GrandPaData(uriStr, grandPaLabel);
					// 将前驱的前驱的信息存放在GrandData对象中，并放进resultSet中
					resultSet.add(aGrandPaData);
				}
			}
			if (Objects.equals(grandPaList, null)) {
				this.grandPaList = resultSet;
			} else {
				this.grandPaList.addAll(resultSet);
			}
		}
	}

	private final void searchSonData(StmtIterator sonStmtIter) {

		ArrayList<SonData> resultList = new ArrayList<SonData>();
		while (sonStmtIter.hasNext()) {
			Statement sonStmt = sonStmtIter.nextStatement();
			RDFNode sonNode = sonStmt.getObject();

			if (sonNode instanceof Resource) {
				Statement labelStmt = ((Resource) sonNode).getProperty(RDFS.label);
				String selfLabel;
				if (labelStmt != null) {
					selfLabel = labelStmt.getObject().toString();
				} else {
					selfLabel = "null";
				}
				String uriStr = ((Resource) sonNode).getURI();
				SonData aSonData = new SonData(uriStr, selfLabel);
				resultList.add(aSonData);
			}

		}
		if (Objects.equals(sonList, null)) {
			this.sonList = resultList;
		} else {
			this.sonList.addAll(resultList);
		}
	}

	/**
	 * 执行搜索。
	 */
	public final void searchByLabel() {
		this.searchSelfByLabel(this.label);
	}

	/**
	 * 得到某一个资源的前驱,返回该资源作为宾语的陈述迭代器
	 * 
	 * @param Resource
	 *            res
	 * @return 该资源作为宾语的陈述迭代器
	 */
	private final StmtIterator getPre(Resource res) {

		StmtIterator preStmtIter = model.listStatements(new SimpleSelector(null, null, res));
		return preStmtIter;
	}

	/**
	 * 得到某一个资源的后继，返回该资源作为主语的陈述迭代器
	 * 
	 * @param Resource
	 *            res
	 * @return 该资源作为主语的陈述迭代器
	 */
	private final StmtIterator getSub(Resource res) {
		StmtIterator subStmtIter = model.listStatements(new SimpleSelector(res, null, (RDFNode) null));
		return subStmtIter;
	}

	/**
	 * 
	 * @return 该对象处理的模型(标签库的模型)
	 */
	public Model getModel() {
		return model;
	}

}
