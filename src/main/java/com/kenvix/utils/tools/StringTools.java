//--------------------------------------------------
// Class StringTools
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.tools;

import java.util.Locale;

public final class StringTools {
    private StringTools() {}

    public static String getShortPackageName(String sourceClassName) {
        StringBuilder builder = new StringBuilder(sourceClassName);

        if (sourceClassName.contains(".")) {
            int beginPosition = 0;
            int nextPosition = 0;

            do {
                nextPosition = builder.indexOf(".", beginPosition + 1);
                int deletedLength = nextPosition - beginPosition - 1;
                builder.delete(beginPosition + 1, nextPosition);
                beginPosition = nextPosition - deletedLength + 1;
            } while ((nextPosition = builder.indexOf(".", beginPosition + 1)) >= 0);
        }

        return builder.toString();
    }

    /**
     * 通配符算法。 可以匹配"*"和"?"
     * 如a*b?d可以匹配aAAAbcd
     *
     * @param pattern 匹配表达式
     * @param str     匹配的字符串
     * @return result
     */
    public static boolean wildcardMatch(String pattern, String str) {
        if (pattern == null || str == null)
            return false;

        boolean result     = false;
        char    c; // 当前要匹配的字符串
        boolean beforeStar = false; // 是否遇到通配符*
        int     back_i     = 0;// 回溯,当遇到通配符时,匹配不成功则回溯
        int     back_j     = 0;
        int     i, j;
        for (i = 0, j = 0; i < str.length(); ) {
            if (pattern.length() <= j) {
                if (back_i != 0) {// 有通配符,但是匹配未成功,回溯
                    beforeStar = true;
                    i = back_i;
                    j = back_j;
                    back_i = 0;
                    back_j = 0;
                    continue;
                }
                break;
            }

            if ((c = pattern.charAt(j)) == '*') {
                if (j == pattern.length() - 1) {// 通配符已经在末尾,返回true
                    result = true;
                    break;
                }
                beforeStar = true;
                j++;
                continue;
            }

            if (beforeStar) {
                if (str.charAt(i) == c) {
                    beforeStar = false;
                    back_i = i + 1;
                    back_j = j;
                    j++;
                }
            } else {
                if (c != '?' && c != str.charAt(i)) {
                    result = false;
                    if (back_i != 0) {// 有通配符,但是匹配未成功,回溯
                        beforeStar = true;
                        i = back_i;
                        j = back_j;
                        back_i = 0;
                        back_j = 0;
                        continue;
                    }
                    break;
                }
                j++;
            }
            i++;
        }

        if (i == str.length() && j == pattern.length())// 全部遍历完毕
            result = true;
        return result;
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public static String makeFirstLetterUppercase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String makeFirstLetterLowercase(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * Convert Uppercase Letter To Underlined Lowercase Letter
     * @param name string to convert
     * @return result
     */
    public static String convertUppercaseLetterToUnderlinedLowercaseLetter(String name) {
        if(name.isEmpty())
            return name;

        char[] chars = name.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        if(chars[0] >= 'A' && chars[0] <= 'Z')
            stringBuilder.append((char) (chars[0] + (char) 32));
        else
            stringBuilder.append(chars[0]);

        for (int i = 1; i < chars.length; i++){
            if(chars[i] >= 'A' && chars[i] <='Z') {
                stringBuilder.append("_").append((char) (chars[i] + (char) 32));
            } else {
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }

    public static String convertPackageNameToUppercaseLetter(String name) {
        char[] chars = name.toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = 0; i < chars.length; i++){
            if(chars[i] == '.') {
                if(i < chars.length-1) {
                    if(chars[i+1] <= 'z' && chars[i+1] >= 'a') {
                        stringBuilder.append((char) (chars[i+1] - (char) 32));
                    } else {
                        stringBuilder.append((char) (chars[i+1]));
                    }
                    i++;
                    continue;
                }
            }

            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }
}
