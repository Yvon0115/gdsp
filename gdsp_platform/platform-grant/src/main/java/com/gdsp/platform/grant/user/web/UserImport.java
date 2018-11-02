package com.gdsp.platform.grant.user.web;

import java.util.List;

import com.gdsp.dev.core.utils.excel.AbstracDataImport;
import com.gdsp.dev.core.utils.excel.AbstractDataIOVO;

/**
 * @ClassName: UserImport
 * @Description: 用户数据导入
 * 
 */
public class UserImport extends AbstracDataImport {

    /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param clazz
    */
    public UserImport(Class clazz) {
        super(clazz);
    }

    @Override
    public List<AbstractDataIOVO> convertImportVO2InserVO(List list) {
        return list;
    }
}
