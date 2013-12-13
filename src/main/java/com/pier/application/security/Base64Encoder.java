package com.pier.application.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Encoder {

    /**
     * @param str
     * @return byte[]
     * @throws Exception
     */
    public static byte[] base64ToByte(String str) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(str);
    }

    /**
     * @param bt
     * @return String
     */
    public static String byteToBase64(byte[] bt) {
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(bt);
    }
}
