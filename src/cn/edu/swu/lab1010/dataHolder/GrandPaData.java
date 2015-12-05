package cn.edu.swu.lab1010.dataHolder;

/**
 * 持有grandpa数据
 * 
 * @author csd
 *
 */
public final class GrandPaData extends Data {
	/**
	 * 传入URI,label并新建对象
	 */

	public GrandPaData(String URI, String label) {
		super(URI, label);

		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRelation() {
		// TODO Auto-generated method stub
		return 2;
	}
}
