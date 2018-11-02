package com.gdsp.platform.systools.indexanddim.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.systools.indexanddim.dao.IIndexManageDao;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;
import com.gdsp.platform.systools.indexanddim.service.IIndexManageService;

@Service
public class IndexManageImpl implements IIndexManageService {

    @Autowired
    private IIndexManageDao indexManagerDao;
    @Autowired
    private IOrgQueryPubService orgQueryPubService;
    @Resource
    private IDefDocService  defDocService;

    @Override
    public Page<IndexInfoVO> queryIndexInfoByCondition(Condition condition, Pageable page, Sorter sort) {
        Page<IndexInfoVO> indexList = indexManagerDao.queryIndexInfoByCondition(condition, page, sort);
        //设置指标统计频度
//        String frequency = "frequency";
//        for (IndexInfoVO indexInfoVO : indexList) {
//            String tempStr = indexInfoVO.getStatfreq();
//            String[] tempStrs = tempStr.split(",");
//            List<DefDocVO> defDocList = defDocService.findFreqByStateFreq(
//                    tempStrs, frequency);
//            String temp = "";
//            for (int j = 0; j < defDocList.size(); j++) {
//                temp += defDocList.get(j).getDoc_name() + ",";
//            }
//            if(temp.length() != 0){
//                indexInfoVO.setStatfreq(temp.substring(0, temp.length() - 1));
//            }else{
//                indexInfoVO.setStatfreq("");
//            }
//        }
        return indexList;
    }

    @Transactional
    @Override
    public boolean saveIdxInfo(IndexInfoVO indexInfoVO) {
        if (StringUtils.isNotEmpty(indexInfoVO.getId())) {
            indexManagerDao.updateIndexInfo(indexInfoVO);
        } else {
            OrgVO orgs = null;
            if (indexInfoVO.getComedepart() != null && !"".equals(indexInfoVO.getComedepart())) {
                orgs = orgQueryPubService.load(indexInfoVO.getComedepart());
                indexInfoVO.setComedepart(orgs.getOrgname());
            }
            indexInfoVO.setPeicision(indexInfoVO.getPeicision());
            indexManagerDao.insertIndexInfo(indexInfoVO);
        }
        return true;
    }

    @Override
    public IndexInfoVO findIdxInfoById(String id) {
        IndexInfoVO findIdxInfo = indexManagerDao.findIdxInfoById(id);
        return findIdxInfo;
    }

    @Override
    public boolean deleteIdxInfo(String[] ids) {
        indexManagerDao.deleteIdxInfo(ids);
        return true;
    }

    @Override
    public boolean uniqueCheck(IndexInfoVO indexInfoVO) {
        int temp = indexManagerDao.existSameIdxInfo(indexInfoVO);
        if (temp > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Page<IndexInfoVO> queryIdxDepartment(Condition condition,
            Pageable page, Sorter sort) {
        Page<IndexInfoVO> temp = indexManagerDao.queryIdxDepartment(condition, page, sort);
        return temp;
    }

    @Override
    public void saveExcelIdxInfo(ArrayList<String[]> excelList, List<String> errorList) {
        List<IndexInfoVO> list = new ArrayList<IndexInfoVO>();
        if (excelList != null && excelList.size() > 0) {
            for (int i = 0; i < excelList.size(); i++) {
            	//arrays代表每一行的3列的数据 数组，
                String[] arrays = excelList.get(i);
                IndexInfoVO idxInfo = new IndexInfoVO();

                if (arrays[0] != null && !arrays[0].isEmpty()) {
                    int idxRes = indexManagerDao
                            .findExportIdxInfoById(arrays[0]);
                    if (idxRes == 0) {
                        idxInfo.setIndexCode(arrays[0]);
                    } else {
                        errorList.add("第" + (i + 1) + "行，第1列，指标编码重复");
                        continue;
                    }
                    if(arrays[0].replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*","").length()==0){
                        idxInfo.setIndexCode(arrays[0]);
                    }else{
                        errorList.add("第" + (i + 1) + "行，第1列，指标编码含有特殊字符");
                        continue;
                    }
                    if (arrays[0].length()<=10) {
                        idxInfo.setIndexCode(arrays[0]);
                    }else{
                    	errorList.add("第" + (i + 1) + "行，第1列，指标编码长度超过10");
                        continue;
                    }
                    
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                
                if (arrays[1] != null && !arrays[1].isEmpty()) {
                    idxInfo.setIndexName(arrays[1]);
                    
                    if (arrays[1].length()<=60) {
                    idxInfo.setIndexName(arrays[1]);
                    }else{
                    	errorList.add("第" + (i + 1) + "行，第2列，指标名称长度超过60");
                        continue;
                    }
                    
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                
                
                idxInfo.setBusinessbore(arrays[2]);
               /* if (arrays[2] != null && !arrays[2].isEmpty()) {
                    idxInfo.setBusinessbore(arrays[2]);
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }*/
                /*if (arrays[3] != null && !arrays[3].isEmpty()) {
                    idxInfo.setIndexColumnName(arrays[3]);
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                if (arrays[4] != null && !arrays[4].isEmpty()) {
                    idxInfo.setIndexTableName(arrays[4]);
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                if (arrays[5] != null && !arrays[5].isEmpty()) {
                    if (arrays[5].equals("是")) {
                        idxInfo.setOnlyflexiablequery("Y");
                    } else if (arrays[5].equals("否")) {
                        idxInfo.setOnlyflexiablequery("N");
                    } else {
                        errorList.add("第" + (i + 1) + "行，第6列填写有误");
                        continue;
                    }
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                if (arrays[6] != null && !arrays[6].isEmpty()) {
                    String replace = arrays[6].replaceAll("，", ",");
                    String[] tempStrs = replace.split(",");
                    List<DefDocVO> defDocList = defDocService
                            .findFreqByCodeName(tempStrs);
                    String temp = "";
                    if (defDocList != null && defDocList.size() > 0) {
                        for (int j = 0; j < defDocList.size(); j++) {
                            temp += defDocList.get(j).getDoc_code() + ",";
                        }
                        idxInfo.setStatfreq(temp.substring(0, temp.length() - 1));
                    } else {
                        errorList.add("第" + (i + 1) + "行，第7列，统计频度填写有误请确认");
                        continue;
                    }
                } else {
                    errorList.add("第" + (i + 1) + "行，信息不完整");
                    continue;
                }
                idxInfo.setDatasource(arrays[7]);
                idxInfo.setComedepart(arrays[8]);
                idxInfo.setBusinessbore(arrays[9]);
                idxInfo.setTechbore(arrays[10]);
                if (!"".equals(arrays[11]) && arrays[11].length() > 0) {
                    idxInfo.setPeicision(Integer.parseInt(arrays[11]));
                } else {
                    idxInfo.setPeicision(2);
                }
                idxInfo.setMeterunit(arrays[12]);
                idxInfo.setRemark(arrays[13]);*/
                list.add(idxInfo);
            }
            if (list != null && list.size() > 0) {
                indexManagerDao.insertIndex(list);
            }
        } else {
            errorList.add("无可导入的指标");
        }
    }

    @Override
    public OrgVO queryOrgByID(String comedepart) {
        return orgQueryPubService.load(comedepart);
    }

    @Override
    public List<IndexInfoVO> queryIndexByIds(List<String> ids) {
        return indexManagerDao.queryIndexByIds(ids);
    }
}
