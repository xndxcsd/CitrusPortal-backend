package cn.edu.swu.lab1010.matchHandler;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.swu.lab1010.mainTes.Data;

/**
 * 实现匹配
 * 
 * @author csd
 *
 */
public class MatchInFile {

	/**
	 * 传入需匹配的标签和需匹配的文本，返回匹配成功与否
	 * 
	 * @param label
	 * @param stringBuilder
	 * @return boolean
	 */
	private String label;
	private StringBuilder stringBuilder;
	private int start;
	private int end;
	private String stringOfMatch;

	public MatchInFile(String label, StringBuilder stringBuilder) {
		this.label = label;
		this.stringBuilder = stringBuilder;
		// TODO Auto-generated constructor stub
	}

	public final boolean match() {
		String patternString = label;
		// System.out.println(patternString);
		// if(patternString!="5-14°C" && patternString.length()<=20) {
		//	System.out.println("进入到if中");
		Pattern pattern = Pattern.compile(patternString);
		// System.out.println(pattern.pattern());
		Matcher matcher = pattern.matcher(stringBuilder);
		if (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			stringOfMatch = matcher.group();
			return true;
		}
		// }
		return false;
	}

	/**
	 * 返回成功匹配的内容，若未匹配调用此函数回抛出异常
	 * 
	 * @throws Exception
	 * @return stringOfMatch
	 */
	public final String getStringOfMatch() throws Exception {
		if (!this.match())
			return "null";
		return stringOfMatch;
	}

	/**
	 * 返回成功匹配起始
	 * 
	 * @return start
	 */
	public final int getStartOfMatch() {
		if (!this.match())
			return 0;
		return start;
	}

	/**
	 * 返回成功匹配末尾
	 * 
	 * @return end
	 */
	public final int getEndOfMatch() {
		if (!this.match()) 
			return -1;
		return end;
	}

//	/**
//	 * 为直接匹配的字符串添加匹配的位置。
//	 * @param resultSet
//	 * @return
//	 * @throws Exception 
//	 */
//	public final HashSet<Data> addLocation(HashSet<Data> resultSet) throws Exception {
//		for (Data resultData : resultSet) {
//			if (label.equals(resultData.getRelativeMappedString())&&resultData.getRelation()==0) {
//				resultData.setStart(start);
//				resultData.setEnd(end);
//				resultData.setStraightMappedString(getStringOfMatch());
//			}//end if 
//		}//end for
//			
//		return resultSet;
//	}
//	
	}
