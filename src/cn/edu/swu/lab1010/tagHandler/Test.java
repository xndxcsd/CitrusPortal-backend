package cn.edu.swu.lab1010.tagHandler;

import org.apache.jena.rdf.model.Model;

public class Test {
	
	
	private static final String TAGFILEPATH = "C:\\Users\\Alexi\\Desktop" + "\\陈思定用工具\\CitrusTest1.3_modified.rdf";

	public static void main(String[] args) {
		RdfDao rdfdao = new RdfDao();
		Model model = null;
		try {
			
			model = rdfdao.readTagRdf(TAGFILEPATH);
			rdfdao.listObjectWithlabel(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
