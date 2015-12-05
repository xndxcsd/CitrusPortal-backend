package cn.edu.swu.lab1010.dataHolder;

/**
 * 保存数据的抽象类： 所有的数据对象都持有URI,Label,relativeMappedString
 * 
 * @author csd
 *
 */
public abstract class Data {
	/*
	 * 对于一次匹配中的father、son、self、grandpa而言，与他们相关的直接匹配中匹配成功的标签字符串应该是相同的
	 * 用static修饰的staticRelativeMappedString存放的好处就在于每成功匹配一次，我们只需要对其调用一次setter方法
	 * 即可实现所有数据对象该域的赋值，并能保持高度的一致性。
	 */
	public static String staticRelativeMappedString;

	// 所有的数据都具有URI， label
	public String URI;
	public String label;

	/*
	 * 用来表示与他们相关的直接匹配中匹配成功的标签字符串
	 * 这个对象的实例域用来转存staticRelativeMappedString的值，避免类域值得改变造成该值改变，显然 一个对象能携带的值是唯一的。
	 * 对象一旦被建立，这个实例域的值就被初始化了。
	 *
	 */
	public String relativeMappedString;

	/**
	 * 传入URI,label并新建对象
	 */
	public Data(String URI, String label) {
		// TODO Auto-generated constructor stub
		this.URI = URI;
		this.label = label;
		setRelativeMappedString();
	}

	// relation用来表示与直接的匹配间的关系 规定有n层关系relation的值就为n
	public abstract int getRelation();

	public static void setStaticMappedString(String mappedString) {
		Data.staticRelativeMappedString = mappedString;
	}

	public void setRelativeMappedString() {
		if (null != staticRelativeMappedString) {
			this.relativeMappedString = Data.staticRelativeMappedString;
		} else {
			this.relativeMappedString = "与其相关的直接匹配的标签字符串不明";
		}
	}

	public String getURI() {
		if (URI == null) {
			return "null";
		}

		return URI;
	}

	public String getLabel() {
		return label;
	}

	public String getRelativeMappedString() {
		return relativeMappedString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((URI == null) ? 0 : URI.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		if (URI == null) {
			if (other.URI != null) {
				return false;
			}
		} else if (!URI.equals(other.URI)) {
			return false;
		}

		return true;
	}
}
