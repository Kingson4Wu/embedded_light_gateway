package com.kxw.light.gateway.tools.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class MD5EncryptTest {

    @Test
    public void encrypt(){
        String md5 = MD5Encrypt.encrypt("dddddd");
        Assert.assertEquals("980ac217c6b51e7dc41040bec1edfec8", md5);

    }

}
