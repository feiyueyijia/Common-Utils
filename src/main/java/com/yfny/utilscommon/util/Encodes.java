/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.yfny.utilscommon.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 封装各种格式的编码解码工具类.
 * 1.Commons-Codec的 hex/base64 编码
 * 2.自制的base62 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 * <p>
 * Author jisongZhou
 * Date  2019/11/19
 */
public class Encodes {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            return null;
        }
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

//	/**
//	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
//	 */
//	public static String encodeUrlSafeBase64(byte[] input) {
//		return Base64.encodeBase64URLSafe(input);
//	}

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * Html 转码.
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String part) {

        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        String input = "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llc3NZaMyO37NT2eeSEHA+DkYekOwAyW7gDMOHLp7gyPaa9Y1VsXp/TF/RXdvP2R4oEf30P9raX76tID7dYaOqg3lRl1tLpzqL/74Lo2l79TzxdTInig9d2aJof9qvdTOp3x695aUcmoT6VyXclEUKFYRhfOnHohBwPg5Dly6V5v9OSEA1pf39Xls8PaKLWOvOjfWroHg0FFIj6NZE+p1TxUIlRXodJSNOoon88/+fuQT+sbO6pWK8+W7gomVatUFXYc5fPcdAdeZ0c+090ZSmtzY1Pnzp3Wytqu3n3vuo74gKW2W9Xi8qpSqZQqbksjyZjUbyuczKqzX1RmcloryyuKO1ElEhGV632FBi0NwklNZIZUK+2q4YmpsbejbDatPbenodBA9b5fU9kRvhICrylPoVAwd9M9lUrJ7z9iDAbglcPSHYAZx3/eMQC8ZAQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBkEC4AZBAuAGQQLgBn/A6IcgODLVvjXAAAAAElFTkSuQmCC";
        //System.out.println(decodeBase64(input));
        System.out.println(decodeBase64String(input));
    }
}
