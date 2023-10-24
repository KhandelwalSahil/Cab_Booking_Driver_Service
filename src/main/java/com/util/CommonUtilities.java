package com.util;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtilities {

    public static String encryptPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
