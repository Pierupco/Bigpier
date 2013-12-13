package com.pier.application.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESEncryptionUtil {
    private static final Log log = LogFactory.getLog(AESEncryptionUtil.class);
    private static final byte[] DEFAULT_AES_KEY = {12, 43, 32, 76, 72, 6, 24, 61, 1, 85, 19, 93, 82, 73, 23, 18};
    private static final String IV_KEY = "GsTSaXu72hZMWRmB";

    @Nullable
    public static String encrypt(@Nullable final String str) {
        return encrypt(str, DEFAULT_AES_KEY);
    }

    /**
     * Encrypt the key, only byte array whose length is 16 will be accepted;
     *
     * @param str
     * @param key
     * @return
     */
    @Nullable
    public static String encrypt(@Nullable final String str, @Nullable final byte[] key) {
        if (key == null || key.length != 16 || StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            final byte[] encryptedBytes = performCipher(key, str.getBytes("UTF-8"), Cipher.ENCRYPT_MODE);
            final String base64 = Base64Encoder.byteToBase64(encryptedBytes).replaceAll("\r", "").replaceAll("\n", "");
            return base64;
        } catch (Exception e) {
            log.error("AES Encryption encrypt error", e);
            return null;
        }
    }

    @Nullable
    public static String decrypt(@Nullable final String str) {
        return decrypt(str, DEFAULT_AES_KEY);
    }

    @Nullable
    public static String decrypt(@Nullable final String str, @Nullable final byte[] key) {
        if (key == null || key.length != 16 || StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] strBytes = Base64Encoder.base64ToByte(str);
            byte[] decryptedBytes = performCipher(key, strBytes, Cipher.DECRYPT_MODE);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            log.error("AES Encryption decrypt error", e);
            return null;
        }
    }

    private static byte[] performCipher(@Nonnull final byte[] key,
                                   @Nonnull final byte[] content,
                                   final int cipherMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key);
        keyGenerator.init(128, secureRandom);
        final SecretKey secretKey = keyGenerator.generateKey();
        byte[] encodeFormat = secretKey.getEncoded();
        final SecretKeySpec keySpec = new SecretKeySpec(encodeFormat, "AES");
        final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());
        cipher.init(cipherMode, keySpec, iv);
        return cipher.doFinal(content);
    }

}
