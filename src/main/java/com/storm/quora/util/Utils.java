package com.storm.quora.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Utils {
    public static String md5(String str) {
        String result = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
