package cn.edu.swu.lab1010.dataHolder;

public abstract class Data {

	//所有的数据都具有URI，	label，relation
	
		public String URI;
		public String label;
		//mappedString 用来表示这一条数据是跟哪个直接匹配的标签有关系。
		public static String staticRelativeMappedString;
		public String relativeMappedString;
		//relation用来表示与直接的匹配间的关系 规定有n层关系relation的值就为n	

		public abstract int getRelation();
		
		public static void setStaticMappedString(String mappedString) {
			Data.staticRelativeMappedString = mappedString;
		}
		public void setRelativeMappedString() {
			if (null != staticRelativeMappedString)
				this.relativeMappedString = Data.staticRelativeMappedString;
		}
		
		public Data(String URI,String label) {
			// TODO Auto-generated constructor stub
			this.URI = URI;
			this.label = label;
			setRelativeMappedString();
		}
		public String getURI() {
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
			result = prime * result + ((URI == null) ? 0 : URI.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Data other = (Data) obj;
			if (URI == null) {
				if (other.URI != null)
					return false;
			} else if (!URI.equals(other.URI))
				return false;
			return true;
		}
		
		

}
