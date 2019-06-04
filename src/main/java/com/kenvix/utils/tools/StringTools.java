//--------------------------------------------------
// Class StringTools
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.tools;

public class StringTools {
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
}
