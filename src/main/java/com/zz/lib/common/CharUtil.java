package com.zz.lib.common;

/**
 * 字符工具类<br>
 * 部分工具来自于Apache Commons系列
 *
 * @author looly
 * @since 4.0.1
 */
public class CharUtil {

	public static char SPACE = ' ';
	public static char TAB = '	';
	public static char DOT = '.';
	public static char SLASH = '/';
	public static char BACKSLASH = '\\';
	public static char CR = '\r';
	public static char LF = '\n';
	public static char DASHED = '-';
	public static char UNDERLINE = '_';
	public static char COMMA = ',';
	public static char DELIM_START = '{';
	public static char DELIM_END = '}';
	public static char BRACKET_START = '[';
	public static char BRACKET_END = ']';
	public static char DOUBLE_QUOTES = '"';
	public static char SINGLE_QUOTE = '\'';
	public static char AMP = '&';
	public static char COLON = ':';
	public static char AT = '@';
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

	public static boolean isChar(Object value) {
		//noinspection ConstantConditions
		return value instanceof Character || value.getClass() == char.class;
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
		return SLASH == c || BACKSLASH == c;
	}

	/**
	 * 不考虑大小写比较两个字符是否相同
	 */
	public static boolean equalsWithoutCase(char c1, char c2) {
			return Character.toLowerCase(c1) == Character.toLowerCase(c2);
	}

	/**
	 * 获取字符类型
	 *
	 * @param c 字符
	 * @return 字符类型
	 * @since 5.2.3
	 */
	public static int getType(int c) {
		return Character.getType(c);
	}

	/**
	 * 获取给定字符的16进制数值
	 *
	 * @param b 字符
	 * @return 16进制字符
	 * @since 5.3.1
	 */
	public static int digit16(int b) {
		return Character.digit(b, 16);
	}

	/**
	 * 将字母、数字转换为带圈的字符：
	 *     '1' -》 '①'
	 *     'A' -》 'Ⓐ'
	 *     'a' -》 'ⓐ'
	 * 获取带圈数字 /封闭式字母数字 ，从1-20,超过1-20报错
	 *
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
	 * 将[1-20]数字转换为带圈的字符：
	 *     1 -》 '①'
	 *     12 -》 '⑫'
	 *     20 -》 '⑳'
	 * 也称作：封闭式字符，英文：Enclosed Alphanumerics
	 *
	 * @param number 被转换的数字
	 * @return 转换后的字符
	 */
	public static char toCloseByNumber(int number) {
		if (number > 20) {
			throw new IllegalArgumentException("Number must be [1-20]");
		}
		return (char) ('①' + number - 1);
	}
}
