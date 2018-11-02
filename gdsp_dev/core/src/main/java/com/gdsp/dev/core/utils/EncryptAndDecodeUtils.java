package com.gdsp.dev.core.utils;


import java.io.UnsupportedEncodingException;


import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES对称加密解密类
 * @author Wang
 *
 */
public class EncryptAndDecodeUtils {  
    
    /**
     * Don't let anyone instantiate this class.
     */
    private EncryptAndDecodeUtils(){
        throw new IllegalAccessError("Utility class");
    }
    
	private static final Logger logger = LoggerFactory.getLogger(EncryptAndDecodeUtils.class);
      
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static byte[] encrypt(String content, String password) {  
        try {             
            SecretKeySpec key = (SecretKeySpec) initKeyForAES(password);  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            return cipher.doFinal(byteContent); // 加密  
        } catch (NoSuchAlgorithmException ex) {  
        	logger.error(ex.getMessage(),ex);  
        } catch (NoSuchPaddingException em) {  
        	logger.error(em.getMessage(),em);  
        } catch (InvalidKeyException en) {  
        	logger.error(en.getMessage(),en);    
        } catch (UnsupportedEncodingException eq) {  
        	logger.error(eq.getMessage(),eq);  
        } catch (IllegalBlockSizeException el) {  
        	logger.error(el.getMessage(),el);  
        } catch (BadPaddingException eg) {  
        	logger.error(eg.getMessage(),eg);   
        }  
        return new byte[0];  
	}  
  
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static byte[] decrypt(byte[] content, String password) {  
        try {  
            
             SecretKeySpec key = (SecretKeySpec) initKeyForAES(password);              
             Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
             cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
             return cipher.doFinal(content); // 加密  
        } catch (NoSuchAlgorithmException ex) {  
        	logger.error(ex.getMessage(),ex);   
        } catch (NoSuchPaddingException em) {  
        	logger.error(em.getMessage(),em);  
        } catch (InvalidKeyException en) {  
        	logger.error(en.getMessage(),en);  
        } catch (IllegalBlockSizeException el) {  
        	logger.error(el.getMessage(),el);  
        } catch (BadPaddingException eg) {  
        	logger.error(eg.getMessage(),eg);  
        }  
        return new byte[0];  
	}  
	private static Key initKeyForAES(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException(ex);
        }
        return key2;

    }
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
    public static String parseByte2HexStr(byte[] buf) {  
    	StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                    hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return new byte[0];  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
    /**
     * 加密处理
     * @param str
     * @return
     */
    public static String getEncryptString(String str,String password){
    	if(StringUtils.isBlank(str)) {
            return "";
        }
    	byte[] encryptResult = encrypt(str, password); 
    	return parseByte2HexStr(encryptResult);
    }
    /**
     * 解密处理
     * @param str
     * @return
     */
    public static String getDecryptString(String str,String password){
    	if(StringUtils.isBlank(str)) {
            return "";
        }
    	byte[] decryptFrom = parseHexStr2Byte(str);  
    	byte[] decryptResult = decrypt(decryptFrom,password);
    	try {
            return new String(decryptResult,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
    	return new String(decryptResult);
    }
  
}  
