/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.newlife.charge.common.security;

import com.newlife.charge.common.Exceptions;
import org.apache.commons.lang3.Validate;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * <p>
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 *
 * @author calvin
 */
public class Digests {

    private static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    private static final String MD5 = "MD5";
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    private static SecureRandom random = new SecureRandom();

    /**
     * 对输入字符串进行md5散列.
     */
    public static byte[] md5(byte[] input) {
        return digest(input, MD5, null, 1);
    }

    public static byte[] md5(byte[] input, int iterations) {
        return digest(input, MD5, null, iterations);
    }

    /**
     * 对输入字符串进行sha1散列.
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     *
     * @param numBytes byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对文件进行md5散列.
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列.
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static String[] encryptPassword(String plainPassword) {
        return encryptPassword(plainPassword, true);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-256 hash
     */
    public static String[] encryptPassword(String plainPassword, boolean md5) {
        if (md5) {
            plainPassword = new Md5Hash(plainPassword).toString();
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash hash = new Sha256Hash(plainPassword, salt, HASH_INTERATIONS);
        String password = hash.toHex();
        return new String[]{salt, password};
    }

    /**
     * @param plainPassword
     * @param md5 是否需要MD5加密
     * @return
     */
    public static Password encryptPwd(String plainPassword, boolean md5) {
        String[] encryptPassword = encryptPassword(plainPassword, md5);
        return new Password(encryptPassword[0], encryptPassword[1]);
    }

    public static Password encryptPwd(String plainPassword) {
        return encryptPwd(plainPassword, true);
    }

    /**
     * 验证密码是否正确
     *
     * @param plainPassword 明文密码
     * @param plainSalt     明文salt
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String plainSalt, String password) {
        return validatePassword(plainPassword, plainSalt, password, true);
    }

    /**
     * 是否要加密
     * @param plainPassword
     * @param plainSalt
     * @param password
     * @param md5 是否需要MD5加密
     * @return
     */
    public static boolean validatePassword(String plainPassword, String plainSalt, String password, boolean md5) {
        if (md5) {
            plainPassword = new Md5Hash(plainPassword).toString();
        }
        SimpleHash hash = new Sha256Hash(plainPassword, plainSalt, HASH_INTERATIONS);
        String oldPassword = hash.toHex();
        return password.equals(oldPassword);
    }


    public static class Password {
        private String salt;
        private String encryptPwd;

        public Password() {
        }

        public Password(String salt, String encryptPwd) {
            this.salt = salt;
            this.encryptPwd = encryptPwd;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getEncryptPwd() {
            return encryptPwd;
        }

        public void setEncryptPwd(String encryptPwd) {
            this.encryptPwd = encryptPwd;
        }
    }


    public static void main(String[] args) {
//		System.out.println(new Md5Hash("123456").toString().equals("e10adc3949ba59abbe56e057f20f883e"));
//		System.out.println(Arrays.toString(encryptPassword("admin")));

        Password pwd = encryptPwd("123456");
        System.out.println(String.format("salt:%s\npwd:%s", pwd.getSalt(), pwd.getEncryptPwd()));

        System.out.println(validatePassword("123456", pwd.getSalt(), pwd.getEncryptPwd()));

        String md5 = "e10adc3949ba59abbe56e057f20f883e";

        System.out.println(validatePassword("e10adc3949ba59abbe56e057f20f883e", "ed6e5b080c9f33e0cd0172bb9a1c0ee1", "710f110006bf66a22479d1c1a5b3cdd186e8bc99448f553"));


        System.out.println(validatePassword("123456", "5fde681c6ab7643979c4d91b5a552c8d", "decdf8938f4abe022c2848331ab2ba820d1ac739d7082222ebc514595d28251c"));
    }


}
