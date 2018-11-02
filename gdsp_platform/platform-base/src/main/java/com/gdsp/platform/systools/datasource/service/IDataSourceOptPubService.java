/**  
* @Title: IDataSourceOptPubService.java
* @Package com.gdsp.platform.systools.datasource.service
* (用一句话描述该文件做什么)
* @author yuchenglong
* @date 2017年7月7日 下午4:27:40
* @version V1.0  
*/ 
package com.gdsp.platform.systools.datasource.service;

import com.gdsp.platform.systools.datasource.model.DsRegisterVO;
import com.gdsp.platform.systools.datasource.model.PtRegMsgVO;

/**
* 数据源相关操作公共接口
* @author yuchenglong
* @see DsRegisterVO 接收注册服务信息 : {@code DsRegisterVO}
* @see PtRegMsgVO 返回注册状态信息 : {@code PtRegMsgVO}
* @date 2017年7月7日 下午4:27:40
*/
@Deprecated
public interface IDataSourceOptPubService {
    
	/**
	 * 数据源注册
	 * @param dsRegisterVO 接收注册服务对象
	 * @return PtRegMsgVO 返回注册状态对象
	 */ 
    public PtRegMsgVO register2Datasource(DsRegisterVO dsRegisterVO);
    
    /**
     * 解除对数据源的注册占用
     * @param ds_id 注册的数据源id
     * @param res_id 注册的资源id
     * @return Boolean 是否解除成功
     */
    public Boolean removeDatasourceRef(String ds_id,String res_id);
    
}
