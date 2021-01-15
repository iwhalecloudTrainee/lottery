package com.iwhalecloud.lottery.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 加密工具类
 *
 * @author by W4i
 * @date 2020/9/22 20:51
 */
public class EncryptionUtils {
    //统一密钥
    private final static String seedCode = "lanwuPark";

    /**
     * 普通md5加密
     *
     * @param str
     * @return
     */
    public static String getMd5String(String str) {
        try {
            //先改变字符串防止暴力破解
            str = str + "lanwuPark";
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5Date() {
        return getMd5String(DateUtils.getNowDate());
    }

    /**
     * 获取X位验证码
     *
     * @return
     */
    public static String getCode(int num) {
        String code = "";
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            int ran1 = r.nextInt(10);
            if (ran1 > num / 2) {
                ran1 = r.nextInt(10);
                code = ran1 + code;
            } else {
                char c = (char) (int) (Math.random() * 26 + 97);
                String b = String.valueOf(c).toUpperCase();
                code = b + code;
            }
        }
        return code;
    }

    /**
     * 6位验证码
     *
     * @return
     */
    public static String getCode() {
        return getCode(6);
    }


    /**
     * 对称加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encode(String str) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seedCode.getBytes());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = str.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);

    }

    /**
     * 对称解密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String decode(String str) throws Exception {
        byte[] content = Base64.decodeBase64(str);
        //防止linux下 随机生成key
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seedCode.getBytes());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return new String(result);
    }

    /**
     * 对UUID进行二次加密
     *
     * @param uuid
     * @return
     */
    public static String UUIDEncode(String uuid) throws Exception {
        String[] uuids = uuid.split("");
        String[] code = String.valueOf(UUID.randomUUID()).split("");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < code.length; i++) {
            String a = uuids[i];
            stringBuffer.append(a);
            String b = code[i];
            stringBuffer.append(b);
        }
        return encode(String.valueOf(stringBuffer));
    }

    /**
     * 对二次加密的UUID进行解密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String UUIDDecode(String str) throws Exception {
        str = EncryptionUtils.decode(str);
        String[] strings = str.split("");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if (i % 2 == 0) {
                stringBuffer.append(string);
            }
        }
        return String.valueOf(stringBuffer);
    }
}