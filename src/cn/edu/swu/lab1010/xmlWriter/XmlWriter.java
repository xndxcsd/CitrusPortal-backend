package cn.edu.swu.lab1010.xmlWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.edu.swu.lab1010.dataHolder.FatherData;
import cn.edu.swu.lab1010.dataHolder.GrandPaData;
import cn.edu.swu.lab1010.dataHolder.SelfData;
import cn.edu.swu.lab1010.dataHolder.SonData;

/**
 * <p>无参的构造方法在此类中并没有用。
 * 我们并未在该类中添加setDataObject()方法
 *	增加发生未知错误的可能性。
 * <p>使用时必须传入一个ResultData对象。
 * <p>该类要实现将该对象携带的数据写到xml。
 * <p>That must be:</br>
 * {@code
 * 	XmlWriter xmlWriter=new XmlWriter(ResultData dataObject);
 * }
 * @author csd
 */

public class XmlWriter {
	
	
	private String selfFilePath = "C:\\Users\\csd\\Desktop\\testdir\\selfxmltest.xml";
	private String sonFilePath = "C:\\Users\\csd\\Desktop\\testdir\\sonxmltest.xml";
	private String fatherFilePath = "C:\\Users\\csd\\Desktop\\testdir\\fatherxmltest.xml";
	private String grandpaFilePath = "C:\\Users\\csd\\Desktop\\testdir\\grandpaxmltest.xml";

	private ArrayList<SonData> sonList;
	private ArrayList<SelfData> selfList;
	private ArrayList<FatherData> fatherList;
	private ArrayList<GrandPaData> grandPaList;
	
	DocumentFactory docFactory = DocumentFactory.getInstance();
	public XmlWriter() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public XmlWriter(ArrayList<SonData> sonList,ArrayList<SelfData> selfList,ArrayList<FatherData> fatherList,ArrayList<GrandPaData> grandPaList)
	{
		this.sonList = sonList;
		this.selfList = selfList;
		this.fatherList = fatherList;
		this.grandPaList = grandPaList;
	}
	
	/**
	 * 
	 * <p>wirte to file by dom4j (a XML tool for java) .
	 * <p>
	 * @throws IOException 
	 */
	public final void write() throws IOException{
		XMLWriter writer = null;
		File file = new File(selfFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("add");
		
		Element segment = root.addElement("doc");
		Element URI = segment.addElement("field");
		URI.addAttribute("name", "URI");
		URI.setText("text");
		
		writer = new XMLWriter(new FileWriter(file), format);
		writer.write(doc);
		writer.close();
		
	}
	
	
	public final void writeSelfList() throws IOException {
		XMLWriter writer = null;
		File file = new File(selfFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Document doc = docFactory.createDocument();
		Element root = doc.addElement("add");
		int count=0;
		
		ListIterator<SelfData> selfIter = selfList.listIterator();
		while (selfIter.hasNext()) {
			SelfData selfData2 = selfIter.next();
			String label = selfData2.getLabel();
			int end = selfData2.getEnd();
			int start = selfData2.getStart();
			String straightMappedString = selfData2.getStraightMappedString();
			int relation = selfData2.getRelation();
			String uri = selfData2.getURI();
			
			Element segment = root.addElement("doc");
			Element uriElement = segment.addElement("field");
			uriElement.addAttribute("name", "URI");
			uriElement.setText(uri);
			
			Element labelElement = segment.addElement("field");
			labelElement.addAttribute("name", "label");
			labelElement.setText(label);
			
			Element straightMappedStringElement = segment.addElement("field");
			straightMappedStringElement.addAttribute("name", "straightMappedString");
			straightMappedStringElement.setText(straightMappedString);
			
			
			Element startElement = segment.addElement("field");
			startElement.addAttribute("name", "start");
			startElement.setText(new Integer(start).toString());
			
			Element endElement = segment.addElement("field");
			endElement.addAttribute("name", "end");
			endElement.setText(new Integer(end).toString());
			
			Element relationElement = segment.addElement("field");
			relationElement.addAttribute("name", "relation");
			relationElement.setText(new Integer(relation).toString());
			
//			System.out.println("添加第"+ count + "项到xml中");
//			count++;
//			
		}
		
		
	
		writer = new XMLWriter(new FileWriter(file), format);
		writer.write(doc);
		writer.close();
		
	}
	
	public final void writeFatherList() throws IOException {
		XMLWriter writer = null;
		File file = new File(fatherFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Document doc = docFactory.createDocument();
		Element root = doc.addElement("add");
		
		ListIterator<FatherData> fatherIter = fatherList.listIterator();
		int count=0;
		while (fatherIter.hasNext()) {
			FatherData fatherData = fatherIter.next();
	
			String label = fatherData.getLabel();
			int relation = fatherData.getRelation();
			String uri = fatherData.getURI();
			String relativeMappedString = fatherData.getRelativeMappedString();
			
			Element segment = root.addElement("doc");
			
			Element uriElement = segment.addElement("field");
			uriElement.addAttribute("name", "URI");
			uriElement.setText(uri);
			
			Element relativeMappedStringElement = segment.addElement("field");
			relativeMappedStringElement.addAttribute("name", "relativeMappedString");
			relativeMappedStringElement.setText(relativeMappedString);
			
			
			Element labelElement = segment.addElement("field");
			labelElement.addAttribute("name", "label");
			labelElement.setText(label);
			
			Element relationElement = segment.addElement("field");
			relationElement.addAttribute("name", "relation");
			relationElement.setText(new Integer(relation).toString());
			
			System.out.println("添加第"+ count + "项到xml中");
			count++;
			
		}

//		writer = new XMLWriter(new FileWriter(file), format);
//		writer = new XMLWriter(System.out);
//		writer = new XMLWriter(new FileWriter(file));
//		writer = new XMLWriter(new BufferedWriter(new FileWriter(file)));
		writer = new XMLWriter(new FileOutputStream(file));
		writer.write(doc);
		writer.close();
		
	}
	
	public final void writeSonList(ArrayList<SonData> sonData) {
		XMLWriter writer = null;
		File file = new File(sonFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public final void writeGrandPaList(ArrayList<GrandPaData> GrandPaData) {
		
		XMLWriter writer = null;
		File file = new File(grandpaFilePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public final void printToConsole() {
		for (SelfData SelfData : selfList) {//测试该列表中是否有数据
			
			System.out.println("relativelabel : "+SelfData.getRelativeMappedString());
			System.out.println("label : "+SelfData.getLabel());
			System.out.println("URI : "+SelfData.getURI());
			System.out.println("relation : "+SelfData.getRelation());
			System.out.println("start :"+SelfData.getStart());
			System.out.println("end :"+SelfData.getEnd());
			System.out.println("straight :"+SelfData.getStraightMappedString());
			System.out.println();
		}
		for (FatherData fatherData : fatherList) {
			System.out.println("relativelabel : "+fatherData.getRelativeMappedString());
			System.out.println("label : "+fatherData.getLabel());
			System.out.println("URI : "+fatherData.getURI());
			System.out.println("relation : "+fatherData.getRelation());
			System.out.println();
			
		
		}
		
	}
}
	








