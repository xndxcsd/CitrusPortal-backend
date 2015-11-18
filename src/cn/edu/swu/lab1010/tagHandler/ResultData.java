package cn.edu.swu.lab1010.tagHandler;

/**
 * <p>
 * 这个类的对象储存着检索到信息<br>
 * <p>
 * 我们重写了hashcode()和equals()方法，当
 * <Strong>
 * 数据的URI
 * </Strong>
 * 			相同时我们就认为这两条数据是相同的
 * 
 * @author csd
 *
 */
public class ResultData {
	/*
	 * 本来是考虑一个ResultData对象中存放一条链式的数据，但考虑到数据的 抽离和写入过程过于复杂，所以放弃了这种方案
	 *
	 * 
	 * private String self; private String pre; private String prepre; private
	 * String sub;
	 * 
	 * public ResultData(String self,String pre,String prepre,String sub) { //
	 * TODO Auto-generated constructor stub this.self = self; this.pre = pre;
	 * this.prepre =prepre; this.sub = sub; }
	 * 
	 * public ResultData() { // TODO Auto-generated constructor stub } public
	 * void setSelf(String self) { this.self = self; } public void setPre(String
	 * pre) { this.pre = pre; } public void setPrepre(String prepre) {
	 * this.prepre = prepre; } public void setSub(String sub) { this.sub = sub;
	 * } public String getSelf() { return self; }
	 * 
	 * public String getPre() { return pre; }
	 * 
	 * public String getPrepre() { return prepre; }
	 * 
	 * public String getSub() { return sub; }
	 * 
	 * 
	 */

	/*
	 * 现在我们的一种新方案是一个ResultData类的对象中只存放一条数据 
	 * 它包含 
	 * 这条数据与匹配之间的关系（前驱和后继都是一层，前驱的前驱是第二层）
	 * 这条数据的URI，标签值 
	 * 如果这条数据就是直接的匹配本身，那么还包括直接匹配的内容和匹配的位置
	 * 
	 */
	//所有的数据都具有URI，	label，relation
	
	public final String URI;
	public final String label;
	
	//relation用来表示与直接的匹配间的关系 规定有n层关系relation的值就为n	
	public final int relation;
	
	//设置两个boolean变量限制如果这条数据是匹配本身时，确保其不能被重复的赋值	
	private boolean isGivenRow = false;
	private boolean isGivenPosition = false;
	//如果这条数据是直接的匹配，还需要将匹配到的内容和匹配的位置保存在对象中
	private int row;
	private int position;
	//	在构造方法中为final域赋值，并且在该类中将不会给出setter防止值被篡改
	public ResultData() {
		// TODO Auto-generated constructor stub
		this.URI = " ";
		this.label =" ";
		this.relation = 0;
	}
	
	public ResultData(int relation,String URI,String label) {
		// TODO Auto-generated constructor stub
		this.relation = relation;
		this.URI = URI;
		this.label = label;
	}
//URI,Label,relation没有setter方法。
	public String getURI() {
		return URI;
	}

	public String getLabel() {
		return label;
	}

	public int getRelation() {
		return relation;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		if (isGivenRow)
			System.out.println("row只能被赋值一次");
		else {
			this.row = row;
			isGivenRow =!isGivenRow;
		}
	}

	public boolean isGivenRow() {
		return isGivenRow;
	}
	
	/*
	 * isGivenRow用来标志row是否被赋值，以此来保证不会重复赋值
	public void setGivenRow(boolean isGivenRow) {
		this.isGivenRow = isGivenRow;
	}
	 */
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		
		
		
		this.position = position;
	}

	public boolean isGivenPosition() {
		return isGivenPosition;
	}
	/*
	 * isGivenPosition用来标志position是否被赋值，以此来保证不会重复赋值
	public void setGivenPosition(boolean isGivenPosition) {
		this.isGivenPosition = isGivenPosition;
	}
	 */
	public void setGivenPosition(boolean isGivenPosition) {
		this.isGivenPosition = isGivenPosition;
	}


	
}
