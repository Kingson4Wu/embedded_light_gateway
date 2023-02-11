package com.kxw.light.gateway.tools.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Encrypt {

    public static String encrypt(String data) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data.getBytes(StandardCharsets.UTF_8));

    }

}
