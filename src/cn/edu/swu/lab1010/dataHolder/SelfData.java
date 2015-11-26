package cn.edu.swu.lab1010.dataHolder;

public final class SelfData extends Data {
	
    private String straightMappedString;//匹配上的文本
    private int start;//匹配的起始位置
    private int end;//匹配的末位置
    public SelfData(String URI, String label, String straightMappedString,
        int start, int end) {//初始化该条数据
        super(URI, label);
        // TODO Auto-generated constructor stub
        this.straightMappedString = straightMappedString;
        this.start = start;
        this.end = end;
    }

    public String getStraightMappedString() {
        if (straightMappedString == null) {
            return "null";
        }

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

}
