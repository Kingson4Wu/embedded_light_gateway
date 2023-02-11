package com.kxw.light.gateway.tools.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class Base64EncryptTest {

    @Test
    public void encrypt(){
        String base64 = Base64Encrypt.encrypt("dddddd");
        Assert.assertEquals("ZGRkZGRk", base64);

    }
}
