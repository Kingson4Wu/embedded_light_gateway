package com.kxw.light.gateway.tools.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Encrypt {

    public static String encrypt(String data) {
        return DigestUtils.md5Hex(data);
    }

}
