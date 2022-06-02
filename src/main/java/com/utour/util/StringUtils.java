package com.utour.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

	public static String voidString(String value) {
		return hasText(value) ? "" : value;
	}

	public static String defaultString(String value, String defaultString) {
		return isEmpty(value) ? defaultString : value;
	}

	/**
	 * markdown 본문에 삽입된 image - url 을 추출합니다.
	 * @param markdown
	 * @return
	 */
	@Deprecated
	public static java.util.List<String> findMarkdownImages (String markdown) {
		Matcher m = Pattern.compile("\\[.*\\]\\((.*)\\)").matcher(markdown);

		List<String> allMatches = new ArrayList<>();

		while (m.find()) {
			allMatches.add(m.group(1).split(" ")[0]);
		}
		return allMatches;
	}

	public static java.util.List<String> findMarkdownImageFileName (String markdown) {
		List<String> allMatches = new ArrayList<>();
		AtomicInteger atomicInteger = new AtomicInteger(1);
		try {
			while (true) {
				int startAt = markdown.indexOf("(/api/v1/image", atomicInteger.get());
				atomicInteger.set(startAt);
				if(atomicInteger.get() == -1) break;
				int endAt = markdown.indexOf(")", atomicInteger.get());
				atomicInteger.set(endAt);
				String extra = markdown.substring(startAt + 1, endAt);
				allMatches.add(extra.substring(extra.lastIndexOf("/") + 1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return allMatches;
	}
}
