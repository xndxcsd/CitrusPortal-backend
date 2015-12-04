package cn.edu.swu.lab1010.xmlWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

import org.dom4j.Document;
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
 * <p>使用时必须传入相应对象。
 * <p>该类要实现将该对象携带的数据写到xml。
 * <p>That must be:</br>
 * {@code
 * 	XmlWriter xmlWriter=new XmlWriter(ArrayList<SonData> sonList,ArrayList<SelfData> selfList,ArrayList<FatherData> fatherList,ArrayList<GrandPaData> grandPaList);
 * }
 * 
 *
 *
 * @author csd
 */

public class XmlWriter {
	
	
	private String selfFilePath;
	private String sonFilePath;
	private String fatherFilePath;
	private String grandpaFilePath;
	private String dirPath = "C:\\Users\\csd\\Desktop\\testdir\\";
	//输出文件ID 
	static int ID = 0;
	
	private ArrayList<SonData> sonList;
	private ArrayList<SelfData> selfList;
	private ArrayList<FatherData> fatherList;
	private ArrayList<GrandPaData> grandPaList;
	
	public XmlWriter() {
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
		if (!Objects.equals(sonList, null)){
//			System.out.println("in son");
			this.writeSonList();
			
		}
		
		if (!Objects.equals(selfList, null))
			this.writeSelfList();
		
		if (!Objects.equals(fatherList, null))
			this.writeFatherList();
		
		if (!Objects.equals(grandPaList, null))
			this.writeGrandPaList();
		
		//文件名中的ID。每写完一次数据，就让该类域的值+1；
		XmlWriter.ID++;
	}
	
	
	public final void writeSelfList() throws IOException {
		selfFilePath = dirPath+"self"+ID+".xml";
		XMLWriter writer = null;
		File file = new File(selfFilePath);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(" ");
        
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Document doc = DocumentHelper.createDocument();
		
		Element root = doc.addElement("add");
		
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
			
		}
	
		writer = new XMLWriter(new FileWriter(file), format);
		writer.write(doc);
		writer.close();
		
	}
	
	public final void writeFatherList() throws IOException {
		fatherFilePath = dirPath + "father" + ID + ".xml";
		XMLWriter writer = null;
		File file = new File(fatherFilePath);
		
		OutputFormat format =OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(" ");
        
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("add");
		
		ListIterator<FatherData> fatherIter = fatherList.listIterator();
		
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
			

		}

		writer = new XMLWriter(new FileWriter(file), format);
//		writer = new XMLWriter(System.out,format);
		writer.write(doc);
		writer.close();
	
		
	}
	
	public final void writeSonList() throws IOException {
		sonFilePath = dirPath + "son" + ID + ".xml";
		XMLWriter writer = null;
		File file = new File(sonFilePath);
		
		OutputFormat format =OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(" ");
        
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("add");
		
		ListIterator<SonData> sonIter = sonList.listIterator();
		while (sonIter.hasNext()) {
			SonData sonData = sonIter.next();
	
			String label = sonData.getLabel();
			int relation = sonData.getRelation();
			String uri = sonData.getURI();
			String relativeMappedString = sonData.getRelativeMappedString();
			
			Element segment = root.addElement("doc");
			
			Element uriElement = segment.addElement("field")
										.addAttribute("name", "URI");
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
			
		}

		writer = new XMLWriter(new FileWriter(file), format);
//		writer = new XMLWriter(System.out,format);
		writer.write(doc);
		writer.close();
	}
	
	
	public final void writeGrandPaList() throws IOException {
		grandpaFilePath = dirPath + "grandPa" + ID + ".xml";
		XMLWriter writer = null;
		File file = new File(grandpaFilePath);
		
		OutputFormat format =OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(" ");
        
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("add");
		
		ListIterator<GrandPaData> grandPaIter = grandPaList.listIterator();
		while (grandPaIter.hasNext()) {
			GrandPaData grandPaData = grandPaIter.next();
	
			String label = grandPaData.getLabel();
			int relation = grandPaData.getRelation();
			String uri = grandPaData.getURI();
			String relativeMappedString = grandPaData.getRelativeMappedString();
			
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
			
		}

		writer = new XMLWriter(new FileWriter(file), format);
//		writer = new XMLWriter(System.out,format);
		writer.write(doc);
		writer.close();
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
	








