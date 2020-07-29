package org.spring.springboot.utils.aes;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class ProtocolAESBase64Util {

    private static final String AES = "AES";

    // 加密算法
    private String    ALGO;

    // 16位的加密密钥
    private byte[]    keyValue;

    /**
     * 用来进行加密的操作
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(StringUtils.getBytesUtf8(data));
        String encryptedValue = Base64.encodeBase64String(encVal);
        return encryptedValue;
    }

    /**
     * 用来进行解密的操作
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decodeBase64(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = StringUtils.newStringUtf8(decValue);
        return decryptedValue;
    }

    /**
     * 用来进行加密的操作
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encryptURLSafe(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(StringUtils.getBytesUtf8(data));
        String encryptedValue = Base64.encodeBase64URLSafeString(encVal);
        return encryptedValue;
    }

    /**
     * 根据密钥和算法生成Key
     *
     * @return
     * @throws Exception
     */
    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public String getALGO() {
        return ALGO;
    }

    public void setALGO(String aLGO) {
        ALGO = aLGO;
    }

    public byte[] getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(byte[] keyValue) {
        this.keyValue = keyValue;
    }

    public static String decrypt(String data, String secretKey) throws Exception {
        // 创建加解密
        ProtocolAESBase64Util aes = new ProtocolAESBase64Util();
        // 设置加解密算法
        aes.setALGO(AES);
        // 设置加解密密钥
        aes.setKeyValue(secretKey.getBytes());
        // 进行解密后的字符串
        return aes.decrypt(data);
    }

    public static String encrypt(String data, String secretKey) throws Exception {
        // 创建加解密
        ProtocolAESBase64Util aes = new ProtocolAESBase64Util();
        // 设置加解密算法
        aes.setALGO(AES);
        // 设置加解密密钥
        aes.setKeyValue(secretKey.getBytes());
        // 进行解密后的字符串
        return aes.encrypt(data);
    }

    public static String encryptURLSafe(String data, String secretKey) throws Exception {
        // 创建加解密
        ProtocolAESBase64Util aes = new ProtocolAESBase64Util();
        // 设置加解密算法
        aes.setALGO(AES);
        // 设置加解密密钥
        aes.setKeyValue(secretKey.getBytes());
        // 进行解密后的字符串
        return aes.encryptURLSafe(data);
    }

    public static void main(String[] args) throws Exception {
        // 要进行加密的密码
        String data = "{\"dpsPP\":{\"2\":\"350\"}";
        //秘钥位数固定为 16 位
        String key = "eafc08bbbde32d8D";
        // 进行加密后的字符串
        String passwordEnc = ProtocolAESBase64Util.encrypt(data, key);
        String passwordEncURLSafe = ProtocolAESBase64Util.encryptURLSafe(data, key);
        String passwordDec = ProtocolAESBase64Util.decrypt(passwordEnc, key);
        String passwordDecURLSafe = ProtocolAESBase64Util.decrypt(passwordEnc, key);
        System.out.println("原来的data : " + data);
        System.out.println("加密后的data : " + passwordEnc);
        //System.out.println("加密后的密码URLSafe : " + passwordEncURLSafe);
        System.out.println("解密后的原data : " + passwordDec);
        //System.out.println("解密后的原密码URLSafe : " + passwordDecURLSafe);
    }
}