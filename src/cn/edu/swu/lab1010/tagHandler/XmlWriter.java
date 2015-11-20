package cn.edu.swu.lab1010.tagHandler;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import javafx.scene.chart.PieChart.Data;

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
	private ResultData DataObject;
	
	public XmlWriter() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public XmlWriter(ResultData DataObject) {
		this.DataObject = DataObject;
	}
	
	
	/**
	 * <p>wirte to file by dom4j ,a XML tool for java .
	 * <p>
	 * @throws Exception 
	 */
	private final void write() throws Exception{
		if (null == DataObject)
			throw new Exception("no dataobject");
		else {
			
		}
		
	}
}
	








