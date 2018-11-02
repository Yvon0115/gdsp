package com.gdsp.platform.systools.indexanddim.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.model.IndexDicVO;
import com.gdsp.platform.systools.indexanddim.service.IIndexDicService;

/**
 * @ClassName: MessageInfDataLoader
 * (消息数据加载)
 * @author zhangkaituo
 * @date 2016年6月6日 下午06:06:06
 *
 */
@Controller("loaderIndexDic")
public class IndexDicDataLoader implements IViewDataLoader {

    @Autowired
    private IIndexDicService indexDicService;

    @Override
    public Object getValue() {
        MapListResultHandler<IndexDicVO> mapHander = new MapListResultHandler<IndexDicVO>("dom_forn_indc");
        Condition cond = new Condition();
        Sorter sort = new Sorter(Direction.DESC, "dom_forn_indc");
        indexDicService.queryAllByHandler(mapHander, cond, sort);
        Map<String, List<IndexDicVO>> map = mapHander.getResult();
        List<IndexDicVO> rootList = new ArrayList<IndexDicVO>();
        Set<String> keySet = map.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {//对map进行循环构建根路径
            String key = it.next();//类型名称
            IndexDicVO dicVO = new IndexDicVO();
            dicVO.setIdx_cde(key);//valueField,idField
            dicVO.setIdx_name(key);//nameField
            rootList.add(dicVO);
        }
        map.put("__null_key__", rootList);
        return map;
    }

    @Override
    public Object getValue(String... parameter) {
        
        return null;
    }
}
