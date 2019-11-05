package com.yfny.utilscommon.util;
/**
 * Created by jisongZhou on 2019/11/5.
 **/

/**
 * ASCII码转换工具
 * Author jisongZhou
 * Date  2019/11/5
 */
public class AsciiUtils {

    /*0-9对应Ascii 48-57
     *A-Z 65-90
     *a-z 97-122
     *第33～126号(共94个)是字符，其中第48～57号为0～9十个阿拉伯数字
     */
    public static void main(String[] args) {
        System.out.println(charToByteAscii('9'));
        System.out.println(byteAsciiToChar(57));
        System.out.println(SumStrAscii("="));
        System.out.println(SumStrAscii(">"));
        System.out.println(SumStrAscii("6e233902-fec1-11e9-a9b8-a0a4c561a610"));
        System.out.println(SumStrAscii("GB3102.1-1993"));
        System.out.println(SumStrAscii("GB3102.2-1993"));
        System.out.println(SumStrAscii("GB3102.3-1993"));
        System.out.println(SumStrAscii("GB3102.4-1993"));
        System.out.println(SumStrAscii("GB3102.5-1993"));
    }

    /**
     * 方法一：将char 强制转换为byte
     *
     * @param ch
     * @return
     */
    public static byte charToByteAscii(char ch) {
        byte byteAscii = (byte) ch;

        return byteAscii;
    }

    /**
     * 方法二：将char直接转化为int，其值就是字符的ascii
     *
     * @param ch
     * @return
     */
    public static int charToIntAscii(char ch) {
        int byteAscii = (int) ch;

        return byteAscii;
    }

    /**
     * 同理，ascii转换为char 直接int强制转换为char
     *
     * @param ascii
     * @return
     */
    public static char byteAsciiToChar(int ascii) {
        char ch = (char) ascii;
        return ch;
    }

    /**
     * 求出字符串的ASCII值和
     * 注意，如果有中文的话，会把一个汉字用两个byte来表示，其值是负数
     */
    public static int SumStrAscii(String str) {
        byte[] byteStr = str.getBytes();
        int sum = 0;
        for (int i = 0; i < byteStr.length; i++) {
            sum += byteStr[i];
        }
        return sum;
    }
}
