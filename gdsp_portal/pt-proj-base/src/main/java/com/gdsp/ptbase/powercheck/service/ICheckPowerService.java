/**
 * 
 */
package com.gdsp.ptbase.powercheck.service;


/**
 * @author wangliyuan
 *
 */
public interface ICheckPowerService {
    /**
     * check user power
     * @param user
     * @param password
     * @return
     */
    String checkPower(String user,String password);
    /**
     * Verify st is correct  
     * @param st
     * @return
     */
    String checkST(String st);
}
