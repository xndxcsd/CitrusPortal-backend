package cn.edu.swu.lab1010.tagHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现匹配
 * @author csd
 *
 */
public class RdfMatch {

	/**
	 * 传入需匹配的标签和需匹配的文本，返回匹配成功与否
	 * @param label
	 * @param stringBuilder
	 * @return boolean
	 */
	private String label;
	private StringBuilder stringBuilder;
	private int start;
	private int end;
	private String stringOfMatch;
	public RdfMatch(String label,StringBuilder stringBuilder) {
		this.label =label;
		this.stringBuilder = stringBuilder;
		// TODO Auto-generated constructor stub
	}
	public final boolean match(){
		Pattern pattern = Pattern.compile(label);
		Matcher matcher = pattern.matcher(stringBuilder);
		if(matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			stringOfMatch = matcher.group();
			return true;
		}
		return false;
	}
	/**
	 * 返回成功匹配的内容，若未匹配调用此函数回抛出异常
	 * @throws Exception 
	 * @return stringOfMatch
	 */
	public final String getStringOfMatch() throws Exception{
		if(!this.match())
			throw new Exception("未匹配成功无法得到匹配字符串");
		return stringOfMatch;
	}
	/**
	 * 返回成功匹配起始
	 * @return start
	 */
	public final int getStartOfMatch() {
		
		return start;
	}
	
	/**
	 * 返回成功匹配末尾
	 * @return end
	 */
	public final int getEndOfMatch() {
		
		return end;
	}

}
