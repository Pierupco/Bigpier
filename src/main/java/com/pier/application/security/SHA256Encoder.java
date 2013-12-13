package com.pier.application.security;

import javax.annotation.Nullable;
import java.security.MessageDigest;

public class SHA256Encoder {
    private static final int ITERATION_COUNT = 5;

    private static final String DEFAULT_SALT_KEY = "{D&:uP7q<*X1]G.)Djp_^703Ad]4x$%QcIQTL*$7iLmQ-2|@f#e_/yKGwEhut1mU";

    public static String encode(final String rawString) throws Exception {
        return encode(rawString, DEFAULT_SALT_KEY);
    }

    public static String encode(@Nullable final String rawString, final String saltKey) throws Exception {
        if (rawString == null) {
            return null;
        }
        String encodedPassword = null;
        byte[] salt = Base64Encoder.base64ToByte(saltKey);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);

        byte[] btPass = digest.digest(rawString.getBytes("UTF-8"));
        for (int i = 0; i < ITERATION_COUNT; i++) {
            digest.reset();
            btPass = digest.digest(btPass);
        }
        encodedPassword = Base64Encoder.byteToBase64(btPass);
        return encodedPassword;
    }

}
