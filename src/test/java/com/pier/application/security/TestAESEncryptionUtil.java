package com.pier.application.security;

import org.junit.Assert;
import org.junit.Test;

public class TestAESEncryptionUtil {
    @Test
    public void testEnDecryptWithDefaultKey() throws Exception {
        final String test = "This is a test string";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test));
        Assert.assertEquals(test, recovered);
    }

    @Test
    public void testEnDecryptWithDefaultKey2() throws Exception {
        final String test = "API:FE66489F304DC75B8D6E8200DFF8A456E8DAEACEC428B427E9518741C92C6660:1234:t@i.com:1385615856355:1385615856355";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test, "1385615941179tss".getBytes()), "1385615941179tss".getBytes());
        Assert.assertEquals(test, recovered);
    }

    @Test
    public void testLongStringEnDecryptWithDefaultKey() throws Exception {
        final String test = "This is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test stringThis is a test string";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test));
        Assert.assertEquals(test, recovered);
    }

    @Test
    public void testEnDecryptWithCustomizedKey() throws Exception {
        final byte[] key = "ithinkthisisagreatkey".substring(0, 16).getBytes();
        final String test = "This is a test string";
        final String recovered = AESEncryptionUtil.decrypt(AESEncryptionUtil.encrypt(test, key), key);
        Assert.assertEquals(test, recovered);
    }
}
