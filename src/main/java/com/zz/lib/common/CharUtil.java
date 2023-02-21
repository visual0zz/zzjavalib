package com.zz.lib.common;

import com.zz.lib.common.exception.DataContentException;

/**
 * 字符工具类<br>
 * 部分工具来自于Apache Commons系列
 *
 * @author looly
 * @since 4.0.1
 */
public class CharUtil {
	public static boolean isAsciiChar(char ch) {
		return ch < 128;
	}

	public static boolean isAsciiPrintableChar(char ch) {
		return ch >= 32 && ch < 127;
	}

	public static boolean isAsciiControlChar(final char ch) {
		return ch < 32 || ch == 127;
	}
	public static boolean isLetter(char ch) {
		return isLetterUpper(ch) || isLetterLower(ch);
	}

	public static boolean isLetterUpper(final char ch) {
		return ch >= 'A' && ch <= 'Z';
	}

	public static boolean isLetterLower(final char ch) {
		return ch >= 'a' && ch <= 'z';
	}

	public static boolean isNumberChar(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static boolean isHexChar(char c) {
		return isNumberChar(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
	}

	public static boolean isLetterOrNumberChar(final char ch) {
		return isLetter(ch) || isNumberChar(ch);
	}

	public static boolean isBlankChar(char c) {
		return isBlankChar((int) c);
	}

	public static boolean isBlankChar(int c) {
		return Character.isWhitespace(c)
				|| Character.isSpaceChar(c)
				|| c == '\ufeff'
				|| c == '\u202a'
				|| c == '\u0000'
				|| c == '\u3164'
				// Braille Pattern Blank
				|| c == '\u2800'
				// MONGOLIAN VOWEL SEPARATOR
				|| c == '\u180e';
	}

	public static boolean isEmoji(char c) {
		return !((c == 0x0) || //
				(c == 0x9) || //
				(c == 0xA) || //
				(c == 0xD) || //
				((c >= 0x20) && (c <= 0xD7FF)) || //
				((c >= 0xE000) && (c <= 0xFFFD)) || //
				((c >= 0x100000) && (c <= 0x10FFFF)));
	}
	public static boolean isFileSeparator(char c) {
		return '/' == c || '\\' == c;
	}

	/**
	 * 不考虑大小写比较两个字符是否相同
	 */
	public static boolean equalsWithoutCase(char c1, char c2) {
			return Character.toLowerCase(c1) == Character.toLowerCase(c2);
	}

	/**
	 * 将字母、数字转换为带圈的字符：
	 *     '1' -》 '①'
	 *     'A' -》 'Ⓐ'
	 *     'a' -》 'ⓐ'
	 * @param c 被转换的字符，如果字符不支持转换，返回原字符
	 * @return 转换后的字符
	 */
	public static char toCloseChar(char c) {
		int result = c;
		if (c >= '1' && c <= '9') {
			result = '①' + c - '1';
		} else if (c >= 'A' && c <= 'Z') {
			result = 'Ⓐ' + c - 'A';
		} else if (c >= 'a' && c <= 'z') {
			result = 'ⓐ' + c - 'a';
		}
		return (char) result;
	}

	/**
	 * 将1-20数字转换为带圈的字符：
	 *     1 -》 '①'
	 *     12 -》 '⑫'
	 *     20 -》 '⑳'
	 *
	 * @param number 被转换的数字
	 * @return 转换后的字符
	 */
	public static char toCloseByNumber(int number) {
		if (number > 20) {
			throw new DataContentException("input number must between 1 and 20.");
		}
		return (char) ('①' + number - 1);
	}
}
