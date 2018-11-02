package com.gdsp.dev.web.security.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 加密服务
 * @author paul.yang
 * @version 1.0 2014年10月29日
 * @since 1.6
 */
@Service("passwordService")
public class EncodePasswordService {

    /**
     * 加密算法，必须与HashedCredentialsMatcher配置一至
     */
    @Value("${credential.hashAlgorithmName:md5}")
    private String  hashAlgorithmName           = "md5";
    /**
     * 哈希迭代次数，必须与HashedCredentialsMatcher配置一至
     */
    @Value("${credential.hashIterations:2}")
    private int     hashIterations              = 2;
    /**
     * 密码存储方式，必须与HashedCredentialsMatcher配置一至
     */
    @Value("${credential.storedCredentialsHexEncoded:true}")
    private boolean storedCredentialsHexEncoded = true;
    /**
     * 附加码
     */
    @Value("${credential.saltAttachCode:67891}")
    private String  attachCode                  = "67891";

    /**
     * 对密码进行加密
     * @param userName 用户名
     * @param password 密码
     * @return 加密后值
     */
    public String encodePassword(String userName, String password) {
        SimpleHash hash = new SimpleHash(getHashAlgorithmName(), password, getSalt(userName), hashIterations);
        if (isStoredCredentialsHexEncoded()) {
            return hash.toHex();
        } else {
            return hash.toBase64();
        }
    }

    /**
     * 根据用户名取得加密盐
     * @param userName 用户名
     * @return 加密盐
     */
    public String getSalt(String userName) {
        return userName + attachCode;
    }

    /**
     * 取得哈希迭代次数，必须与HashedCredentialsMatcher配置一至
     * @return 哈希迭代次数
     */
    public int getHashIterations() {
        return hashIterations;
    }

    /**
     * 设置哈希迭代次数，必须与HashedCredentialsMatcher配置一至
     * @param hashIterations 哈希迭代次数
     */
    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * 取得哈希加密算法
     * @return 哈希加密算法
     */
    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    /**
     * 设置哈希加密算法
     * @param hashAlgorithmName 哈希加密算法
     */
    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    /**
     * 判断密码存储方式
     * @return 布尔值
     */
    public boolean isStoredCredentialsHexEncoded() {
        return storedCredentialsHexEncoded;
    }

    /**
     * 设置密码存储方式
     * @param storedCredentialsHexEncoded 布尔值
     */
    public void setStoredCredentialsHexEncoded(boolean storedCredentialsHexEncoded) {
        this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
    }

    /**
     * 取得附加嘛
     * @return 附加码
     */
    public String getAttachCode() {
        return attachCode;
    }

    /**
     * 设置附加码
     * @param attachCode 附加码
     */
    public void setAttachCode(String attachCode) {
        this.attachCode = attachCode;
    }
}
