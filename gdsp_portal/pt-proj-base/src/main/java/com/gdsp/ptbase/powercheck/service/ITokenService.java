/**
 * 
 */
package com.gdsp.ptbase.powercheck.service;


/**
 * @author wangliyuan
 *
 */
public interface ITokenService {
    
    /**
     * Issue token 
     * @param UUID
     * @return
     */
    String issueToken(String UUID);
    /**
     * save token to tokenCache
     * @param ticket
     * @return
     */
    String saveToken (String ticket);
     /**
      * remove tokenCache by ticket
      * @param ticket
      */
    void  removeToken(String ticket);
}
