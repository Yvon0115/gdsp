package com.gdsp.platform.config.global.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.impl.QueryService;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.global.dao.IDefDocDao;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;

/**
 * 
* @ClassName: DefDocServiceImpl
* (接口实现类)
* @author songxiang
* @date 2015年10月28日 下午2:02:43
*
 */
@Service
@Transactional(readOnly = true)
public class DefDocServiceImpl extends QueryService<DefDocVO> implements IDefDocService {

    @Resource
    private IDefDocDao         defDocDao;
    @Resource
    private IDefDocListService defDocListService;

    @Override
    protected QueryDao<DefDocVO> getDao() {
        return defDocDao;
    }

    //根据主键查询
    @Override
    public DefDocVO findOne(String id) {
        return defDocDao.load(id);
    }

    //查询分类下的档案
    @Override
    public List<DefDocVO> findListByType(String type_id) {
        return defDocDao.findListByType(type_id);
    }

    //查询分类下的档案
    @Override
    public Page<DefDocVO> findPageByType(String type_id, Pageable pageable) {
        Page<DefDocVO> defDocVO=defDocDao.findPageByType(type_id, pageable);//查询所有对象信息
        if(defDocVO.getContent().size()>0){
            List<String> pk_fatherId=new ArrayList<String>();
            for(int i=0;i<defDocVO.getContent().size();i++){
                if(defDocVO.getContent().get(i).getPk_fatherId()==null){
                    defDocVO.getContent().get(i).setPk_fatherId("");
                }
                pk_fatherId.add(defDocVO.getContent().get(i).getPk_fatherId());//取出对象信息的pk_fatherId  
            }
            List<DefDocVO> parentVO=defDocDao.queryParent(pk_fatherId);
            for(int i=0;i<defDocVO.getContent().size();i++){
                for(int j=0;j<parentVO.size();j++){
                    if(defDocVO.getContent().get(i).getPk_fatherId().equals(parentVO.get(j).getId())){//匹配对象信息的上级
                        defDocVO.getContent().get(i).setParentCode(parentVO.get(j).getDoc_code());
                        defDocVO.getContent().get(i).setParentName(parentVO.get(j).getDoc_name());
                        break;
                    }
                }
            }
            return defDocVO;
        }else{
            return defDocVO;
        }
    }

    //保存更新
    @Transactional
    @Override
    @CacheEvict(value = "defDocListCache", allEntries = true)
    public void saveDefDoc(DefDocVO defDocVO) {
        
        if (StringUtils.isNotEmpty(defDocVO.getId())) {
            defDocDao.update(defDocVO);
        } else {
            defDocVO.setSortnum(defDocDao.findSortnum() + 1);
            defDocDao.insert(defDocVO);
        }
    }

    //单条ID查询
    @Override
    public DefDocVO load(String id) {
        DefDocVO defDocVO=defDocDao.load(id);
        setParent(defDocVO);
        return defDocVO;
    }

	private void setParent(DefDocVO defDocVO) {
		if(defDocVO != null && StringUtils.isNotEmpty(defDocVO.getPk_fatherId())){
            List<String> pk_fatherId=new ArrayList<String>();
            pk_fatherId.add(defDocVO.getPk_fatherId());
            List<DefDocVO> parent=defDocDao.queryParent(pk_fatherId);
            for(DefDocVO i:parent){
                defDocVO.setParentCode(i.getDoc_code());
                defDocVO.setParentName(i.getDoc_name());
            } 
        }
	}
/*
    //删除
    @Transactional
    @Override
    @CacheEvict(value = "defDocListCache", allEntries = true)
    public void delete(String[] ids) {
        defDocDao.delete(ids);
    }
*/
    //查询到sortnum的最大值
    @Override
    public int findSortnum() {
        
        return defDocDao.findSortnum();
    }

    /* (非 Javadoc)
    * <p>Title: isPreset</p>
    * <p>Description: </p>
    * @param id
    * @return
    * @see com.gdsp.platform.systools.datadic.service.IDefDocService#isPreset(java.lang.String[])
    */
    @Override
    public List<String> isPreset(String[] id) {
        return defDocDao.isPreset(id);
    }
/*
    @Override
    public DefDocVO findByName(String doc_name, String type_id) {
        return defDocDao.findByName(doc_name, type_id);
    }
    */
/*
    @Override
    public DefDocVO findByDocDesc(String doc_desc, String type_id) {
        return defDocDao.findByDocDesc(doc_desc, type_id);
    }
*/
    @Override
    public boolean synchroCheck(DefDocVO defDocVO) {
    	 if(defDocDao.existSameDocCode(defDocVO)>0){
             return false;
         }else{
             return true;
         }
    }
    /*
    @Override
    public List<DefDocVO> getDefDocsByTypeIDFromDataBase(String dataSource) {
        return defDocDao.getDefDocsByTypeIDFromDataBase(dataSource);
    }
    */
   

    @Override
    public List<DefDocVO> findDateFreq(String dateFreq) {
        return defDocDao.findDateFreq(dateFreq);
    }

    @Override
    public List<DefDocVO> findFreqByStateFreq(String[] tempStrs, String frequency) {
        return defDocDao.findFreqByStateFreq(tempStrs, frequency);
    }

    @Override
    public List<DefDocVO> findFreqByCodeName(String[] tempStrs) {
        return defDocDao.findFreqByCodeName(tempStrs);
    }

    @Override
    @Cacheable(value = "defDocListCache", key = "#typeCode")
    public List<DefDocVO> queryDefDocByTypeCode(String typeCode) {
        List<DefDocListVO> listVos = defDocListService.queryByTypeCode(typeCode);
        List<String> typeids = new ArrayList<String>();
        if (listVos != null) {
            for (DefDocListVO listVo : listVos) {
                typeids.add(listVo.getId());
            }
        }
        if (typeids.size() == 0) {
            return new ArrayList<DefDocVO>();
        }
        Condition con = new Condition();
        con.addExpression("type_id", typeids, "in");
        return this.findListByCondition(con);
    }

    

    @Override
    public Map selectParent(String type_id) {
        MapListResultHandler handler = new MapListResultHandler("pk_fatherId");
        defDocDao.selectParent(handler,type_id);
        return handler.getResult();
    }
    
    @Override
    public Map<String, List<DefDocVO>> findAllDocListWithDocs() {
        List<DefDocListVO> list = defDocDao.findAllDocListWithDocs();
        Iterator<DefDocListVO> it = list.iterator();
        Map<String, List<DefDocVO>> defdoclistMap = new HashMap<String, List<DefDocVO>>();
        while (it.hasNext()) {
            DefDocListVO vo = it.next();
            if (vo.getDefdocs() != null) {
                for (int i = 0; i < vo.getDefdocs().size(); i++) {
                    DefDocVO defdocvo = vo.getDefdocs().get(i);
                    defdocvo.setDoc_code("##NULL##".equals(defdocvo.getDoc_code()) ? "" : defdocvo.getDoc_code());
                }
            }
            defdoclistMap.put(vo.getType_code(), vo.getDefdocs());
        }
        return defdoclistMap;
    }

	@Override
	public List<DefDocVO> findSubLevelDocsByCode(String typeCode, String docCode) {
		String typeId = defDocListService.findIdByTypeCode(typeCode);
//		String docId = this.findDocIdByDocCode(docCode);
//		String pk_fatherId = this.load(docCode).getPk_fatherId();
		DefDocVO docVO = this.findDocByTypeAndCode(typeCode, docCode);
        Condition cond = new Condition();
        cond.addExpression("type_id", typeId, "=");
        cond.addExpression("PK_FATHERID", docVO.getId(), "=");
        return this.findListByCondition(cond);
	}
	
	@Override
	public List<DefDocVO> findSubLevelDocsByCode(String typeCode, List<String> docCode) {
		return defDocDao.querySubLevelDocsByCode(typeCode, docCode);
	}
	
	@Override
	public DefDocVO findDocByTypeAndCode(String typeCode, String docCode) {
		DefDocVO docVO = defDocDao.queryDocByTypeAndCode(typeCode,docCode);
		setParent(docVO);
		return docVO;
	}

	@Override
	public Map findDefDocMapByTypeCode(String typeCode) {
		String typeId = defDocListService.findIdByTypeCode(typeCode);
		MapListResultHandler handler = new MapListResultHandler("pk_fatherId");
		defDocDao.queryDefDocMapByTypeId(typeId, handler);
		return handler.getResult();
	}
	
	
	
	
     
}
