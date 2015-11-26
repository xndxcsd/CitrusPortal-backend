package cn.edu.swu.lab1010.xmlWriter;
import java.util.ArrayList;

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
	private ArrayList<SonData> sonList;
	private ArrayList<SelfData> selfList;
	private ArrayList<FatherData> fatherList;
	private ArrayList<GrandPaData> grandPaList;
	
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
	 * <p>wirte to file by dom4j ,a XML tool for java .
	 * <p>
	 * @throws Exception 
	 */
	public final void write() throws Exception{
		//todo
		if (null == null)
			throw new Exception("no dataobject");
		else {
			
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
	








