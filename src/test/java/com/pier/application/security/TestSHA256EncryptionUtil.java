package com.pier.application.security;

import org.junit.Assert;
import org.junit.Test;

public class TestSHA256EncryptionUtil {
    @Test
    public void testEnDecryptWithDefaultKey() throws Exception {
        final String test = "This is a test string";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test));
        Assert.assertEquals(test, recovered);
    }

    @Test
    public void testEnDecryptWithCustomizedKey() throws Exception {
        final byte[] key = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        final String test = "This is a test string";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test, key), key);
        Assert.assertEquals(test, recovered);
    }
}
