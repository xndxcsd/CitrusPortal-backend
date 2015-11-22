package cn.edu.swu.lab1010.dataHolder;

public final class SelfData extends Data {

	private String straightMappedString;
	private int start;
	private int end;

	public SelfData(String URI, String label, String straightMappedString, int start, int end) {
		super(URI, label);
		// TODO Auto-generated constructor stub
		this.straightMappedString = straightMappedString;
		this.start = start;
		this.end = end;
	}

	public String getStraightMappedString() {
		if (straightMappedString == null)
			return "null";
		return straightMappedString;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public int getRelation() {
		// TODO Auto-generated method stub
		return 0;
	}
	// 设置两个boolean变量限制如果这条数据是匹配本身时，确保其不能被重复的赋值
	// private boolean isGivenRow = false;
	// private boolean isGivenPosition = false;
	// 如果这条数据是直接的匹配，还需要将匹配到的内容和匹配的位置保存在对象中
	// private int row;
	// private int position;
	// 匹配到的内容和匹配串的起始位置和结束位置

	// public void setStart(int start) {
	// this.start = start;
	// }

	// public void setStraightMappedString(String straightMappedString) {
	// this.straightMappedString = straightMappedString;
	// }

	// public void setEnd(int end) {
	// this.end = end;
	// }
	//
	// public int getRow() {

	// if (0 == row)
	// return -1;
	// return row;
	// }
	//
	// public void setRow(int row) {
	// if (isGivenRow)
	// System.out.println("row只能被赋值一次");
	// else {
	// this.row = row;
	// isGivenRow =!isGivenRow;
	// }
	// }
	//
	// public boolean isGivenRow() {
	// return isGivenRow;
	// }
	//
	// /*
	// * isGivenRow用来标志row是否被赋值，以此来保证不会重复赋值
	// public void setGivenRow(boolean isGivenRow) {
	// this.isGivenRow = isGivenRow;
	// }
	// */
	//
	// public int getPosition() {
	// if (0 == position)
	// return -1;
	// return position;
	// }
	//
	// public void setPosition(int position) {
	// if(isGivenPosition)
	// System.out.println("position given!");
	// else {
	// this.position = position;
	//
	// }
	//
	//
	// this.position = position;
	// }
	//
	// public boolean isGivenPosition() {
	// return isGivenPosition;
	// }
	/*
	 * isGivenPosition用来标志position是否被赋值，以此来保证不会重复赋值 public void
	 * setGivenPosition(boolean isGivenPosition) { this.isGivenPosition =
	 * isGivenPosition; }
	 */
	// public void setGivenPosition(boolean isGivenPosition) {
	// this.isGivenPosition = isGivenPosition;
	// }

}
