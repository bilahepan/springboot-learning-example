package org.springboot.pulsar.pulsar.mq;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AESBase64Utils {

    private static final String AES = "AES";

    // Encryption Algorithm
    private String ALGO;

    // Encryption key
    private byte[] keyValue;

    /**
     * Encryption method
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
     * Decryption method
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
     * @return
     * @throws Exception
     */
    private Key generateKey() {
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
        AESBase64Utils aes = new AESBase64Utils();
        aes.setALGO(AES);
        aes.setKeyValue(secretKey.getBytes());
        return aes.decrypt(data);
    }

    public static String encrypt(String data, String secretKey) throws Exception {
        AESBase64Utils aes = new AESBase64Utils();
        aes.setALGO(AES);
        aes.setKeyValue(secretKey.getBytes());
        return aes.encrypt(data);
    }

}
