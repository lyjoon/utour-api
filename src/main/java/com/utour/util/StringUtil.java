package com.utour.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

	public static String voidString(String value) {
		return isEmpty(value) ? "" : value;
	}

	public static String defaultString(String value, String defaultString) {
		return isEmpty(value) ? defaultString : value;
	}

	/**
	 * markdown 본문에 삽입된 image - url 을 추출합니다.
	 * @param markdown
	 * @return
	 */
	public static java.util.List<String> findMarkdownImages (String markdown) {
		Matcher m = Pattern.compile("\\[.*\\]\\((.*)\\)").matcher(markdown);

		List<String> allMatches = new ArrayList<>();

		while (m.find()) {
			allMatches.add(m.group(1).split(" ")[0]);
		}

		return allMatches;
	}
}
