package com.gdsp.platform.grant.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.entity.IBaseEntity;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * GDSP权限系统工具类
 * @since 2016年12月26日 上午9:21:23
 */

public class GrantUtils {

    /**
     * 查询用户有管理权限的机构查询脚本，返回机构查询脚本
     * @param userID 用户
     * @param fieldName 字段名，可以带表别名
     * @return List
     */
    public static String getOrgPowerStrByUser(String userID) {
        if (StringUtils.isEmpty(userID))
            return "";
        // 返回用户有管理权限的机构查询SQL
        return " (select resource_id  from rms_power_org  where pk_role = '" + userID + "' or pk_role in (select pk_role from rms_user_role where pk_user = '" + userID + "'))";
    }

    /**
     * 查询用户有管理权限的机构条件，返回机构条件
    
     * @param userID 用户
     * @param fieldName 字段名，可以带表别名
     * @return List
     */
    public static String getOrgPowerCondByUser(String userID, String fieldName) {
        if (StringUtils.isEmpty(userID))
            return " 1=1 ";
        if (StringUtils.isEmpty(fieldName))
            fieldName = "pk_org";
        // 返回用户有管理权限的机构查询SQL
        String powerCond = fieldName + " in (select resource_id  from rms_power_org  where pk_role = '" + userID + "' or pk_role in (select pk_role from rms_user_role where pk_user = '"
                + userID + "'))";
        return powerCond;
    }
    
    /**
     * 转换列表为分页
     * @param list
     * @param pageable
     * @return
     * @author wqh
     * @since 2016年12月26日
     */
    public static <T extends IBaseEntity>Page<T> convertListToPage(List<T> list, Pageable pageable) {
    	if (list == null) {
			list = new ArrayList<T>();
		}
    	int pageNumber = pageable.getPageNumber();
    	int pageSize = pageable.getPageSize();
    	int startRowNum = pageNumber * pageSize;
    	int endRowNum = pageNumber * pageSize + pageSize;
    	if(list.size() < startRowNum){
            pageable = new PageSerRequest();
            pageNumber = pageable.getPageNumber();
            pageSize = pageable.getPageSize();
            startRowNum = pageNumber * pageSize;
            endRowNum = pageNumber * pageSize + pageSize;
        }
    	List<T> resultList = list.subList(startRowNum, endRowNum > list.size() ? list.size() : endRowNum);
    	Page<T> page = new PageImpl<T>(resultList, pageable, list.size());
		return page;
    }
}
