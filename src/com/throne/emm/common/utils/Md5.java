package com.throne.emm.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

    /**
    * md5加密
    * @param message
    * @return
    */
    public static String md5(String message) {
        byte[] bytes = message.getBytes();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        messageDigest.update(bytes);
        return byteArr2HexStr(messageDigest.digest());
    }

    private static String byteArr2HexStr(byte[] arr) {
        int iLen = arr.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arr[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static String md5Hex(File file) {
        BufferedInputStream in = null;
        MessageDigest digest = null;
        try {
            try {
                long length = file.length();
                int size = (int) Math.min(65536, length);
                byte[] buf = new byte[size];
                digest = MessageDigest.getInstance("MD5");
                in = new BufferedInputStream(new FileInputStream(file), buf.length);
                int num_read;
                while ((num_read = in.read(buf)) != -1) {
                    digest.update(buf, 0, num_read);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
        }
        if (digest != null) {
            return new String(encode(digest.digest()));
        } else {
            return null;
        }
    }
    
    public static String md5Hex(InputStream in) {
        int num_read;
        byte[] buf = new byte[1024];
        MessageDigest digest = null;
        try {
            try {
                digest = MessageDigest.getInstance("MD5");
                while ((num_read = in.read(buf)) != -1) {
                    digest.update(buf, 0, num_read);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            
        }
        if (digest != null) {
            return new String(encode(digest.digest()));
        } else {
            return null;
        }
    }

    public static char[] encode(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexChar[(0xF0 & data[i]) >>> 4];
            out[j++] = hexChar[0x0F & data[i]];
        }

        return out;
    }

    public static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

}
