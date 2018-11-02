package com.gdsp.platform.systools.indexanddim.service;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.model.IndexDicVO;

/**
 * 
* @ClassName: IIndexDicService
* (IIndexDoc接口)
* @author zhangkaituo
*/
public interface IIndexDicService {

    public void queryAllByHandler(MapListResultHandler<IndexDicVO> map, Condition cond, Sorter sort);
}
