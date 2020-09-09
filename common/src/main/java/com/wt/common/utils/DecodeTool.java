package com.wt.common.utils;

/**
 * @author wangtao
 * @date 2019/12/26 15:16
 */
public class DecodeTool {

    // Base64转码
    public static String base64Encode(String str) {
        byte[] encodedBytes = java.util.Base64.getEncoder().encode(str.getBytes());
        return new String(encodedBytes,java.nio.charset.Charset.forName("UTF-8"));
    }

    // Base64解码
    public static String base64Decode(String ecode) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(ecode.getBytes());
        return new String(decodedBytes, java.nio.charset.Charset.forName("UTF-8"));
    }

    /**
     * 生成验证码
     * @author wangtao
     * @date 2020/1/1 14:26
     * @param  * @param numberflag
     * @param length
     * @return java.lang.String
     */
    public static String createVerCode(boolean numberflag, int length) {
        String retstr = "";
        String strtable = numberflag ? "1234567890" : "1234567890aAbBcCdDeEfFgGhHiIjJkKmMnNpPqQrRsStTuUvVwWxXyYzZ";
        int len = strtable.length();
        boolean bdone = true;
        do {
            retstr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblr = Math.random() * len;
                int intr = (int) Math.floor(dblr);
                char c = strtable.charAt(intr);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retstr += strtable.charAt(intr);
            }
            if (count >= 2) {
                bdone = false;
            }
        } while (bdone);
        return retstr;
    }
}
