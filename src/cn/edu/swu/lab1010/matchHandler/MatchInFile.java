package cn.edu.swu.lab1010.matchHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现标签值与文本的匹配
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

	/**
	 * 匹配
	 * 
	 * @return 匹配的结果
	 */
	public final boolean match() {
		String patternString = label;

		Pattern pattern = Pattern.compile(patternString);
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
	 * 返回匹配的内容
	 * 
	 * @return String 成功匹配的内容（如果未成功匹配返回"null"）
	 */
	public final String getStringOfMatch() {
		if (!this.match()) {
			return "null";
		}

		return stringOfMatch;
	}

	/**
	 * 返回匹配起始位置
	 * 
	 * @return int 成功匹配起始位置（如果未成功匹配返回0）
	 */
	public final int getStartOfMatch() {
		if (!this.match()) {
			return 0;
		}

		return start;
	}

	/**
	 * 返回成功匹配末尾
	 * 
	 * @return int 成功匹配末位置（如果未成功匹配返回-1）
	 *
	 */
	public final int getEndOfMatch() {
		if (!this.match()) {
			return -1;
		}

		return end;
	}
}
