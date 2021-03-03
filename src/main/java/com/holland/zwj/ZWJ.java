package com.holland.zwj;

import com.holland.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 零宽字符加密
 */
public class ZWJ {
    private static final char _0 = '\u200b';
    private static final char _1 = '\u200c';
    //    private static final char _10 = '\u200d';
    private static final char split = '\uFEFF';

//    private static final String secret = "秘密";

    public static String encode(String str, String secret) {
        final List<String> list = new ArrayList<>();
        for (char c : secret.toCharArray()) {
            final String s = Integer.toBinaryString(c);
            list.add(s);
        }

        final List<String> collect = list.stream().map(s -> {
            StringBuilder newS = new StringBuilder();
            for (char c : s.toCharArray()) {
                newS.append(c == 48 ? _0 : _1);
            }
            return newS.toString();
        }).collect(Collectors.toList());

        return str + collect.stream().collect(Collectors.joining(String.valueOf(split), String.valueOf(split), String.valueOf(split)));
    }

    public static String decode(String str) {
        final List<String> list = new ArrayList<>();

        String s = null;
        for (char c : str.toCharArray()) {
            if (c == split) {
                if (s == null) {
                    s = "";
                } else {
                    list.add(s);
                    s = "";
                }
            }
            if (c == _0) {
                s += "0";
            }
            if (c == _1) {
                s += "1";
            }
        }

        return list.stream().map(ZWJ::BinstrToChar).map(String::valueOf).collect(Collectors.joining());
    }

    private static char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    private static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    public static void main(String[] args) {
        final String encode = ZWJ.encode("一段文字", "私密a1!@.?？——-");
        System.out.println(encode);
        FileUtil.INSTANCE.newFile(encode, "D:", "weather.txt");

        final String[] strings = FileUtil.INSTANCE.readFile("D:", "weather.txt");
        final String decode = decode(strings[0]);
        System.out.println(decode);
    }
}
