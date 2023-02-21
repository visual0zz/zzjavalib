package com.zz.lib.common.json;

public class JsonStrParser {

    public int index;
    public CharSequence charSequence;
    boolean ended = false;

    public char next() {
        if (ended) {
            throw new RuntimeException("Substring bounds error");
        }
        if (index >= charSequence.length() - 1) {
            ended = true;
        }
        return charSequence.charAt(index++);
    }

    public String next(int n) {
        if (n == 0) {
            return "";
        }
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            result[i] = next();
        }
        return new String(result);
    }

    public String nextString(char quote) {
        char c;
        StringBuilder sb = new StringBuilder();
        while (true) {
            c = next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    throw new RuntimeException("Unterminated string");
                case '\\':// 转义符
                    c = next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':// Unicode符
                            sb.append((char) Integer.parseInt(next(4), 16));
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                        case '/':
                            sb.append(c);
                            break;
                        default:
                            throw new RuntimeException("Illegal escape.");
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }
}
