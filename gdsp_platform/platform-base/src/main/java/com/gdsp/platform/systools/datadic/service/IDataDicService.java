package com.gdsp.platform.systools.datadic.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;

/**
 * 数据字典接口
 * @author qishuo
 * @date 2016年12月09日
 */
@Deprecated
public interface IDataDicService {
   /**
    * 加载操作对象
    * @param dicId 数据字典ID
    * @return 以VO的形式返回
    */
   public DataDicVO loadDataDic(String dicId);
   /**
    * 加载数据字典中所有数据
    * @param con 查询条件
    * @param page 分页对象
    * @param sort 排序
    * @return 以分页的形式返回数据字典VO
    */
   public Page<DataDicVO> queryDataDicPageByCon(Condition con,Pageable page,Sorter sorter);
   
   /**
    * 加载数据字典中所有数据
    * @return 以集合的形式返回数据字典VO
    */
   public List<DataDicVO> queryAllDataDicList ();
   /**
    * 查询所有不在当前id集合中的数据字典数据
    * @param ids 数据字典id集合
    * @return  以分页的形式返回数据字典数据
    */
   public Page<DataDicVO> queryDataDicExtDicIds(List<DataDicPowerVO> voList, Condition condition, Pageable page);
   /**
    * 保存数据字典
    * @param dataDicVO 数据字典对象
    */
   public void saveDataDic(DataDicVO dataDicVO );
   /**
    * 删除数据字典中的记录
    * @param ids 要删除数据记录的id集合
    */
   public List<?> deleteDataDic(String[] ids);
   /**
    * 校验数据字典的编码是否重复
    * @param dataDicVO 数据字典对象
    * @return 以boolean的形式返回是否重复
    */
   public boolean checkDataDicCode(DataDicVO dataDicVO);
   /**
    * 通过数据字典id查询数据字典值
    * @param dicId 数据字典id
    * @return 以集合的形式返回数据字典值VO
    * TODO 实现这个方法
    */
//   public List<DataDicValueVO> findDaDicValListByDicId(String dicId);
   /**
    * 通过数据字典id查询数据字典值
    * @param dicId 数据字典id
    * @return 以分页的形式返回数据字典值VO
    */
   public Page<DataDicValueVO> findDaDicValPageByDicId(Pageable page,String pk_dicId);
   /**
    * 保存数据字典值
    * @param dataDicValueVO 数据字典值对象
    */
   public void saveDataDicValue(DataDicValueVO dataDicValueVO);
   /**
    * 加载数据字典值对象
    * @param dicValId 数据字典值ID
    * @return
    */
   public DataDicValueVO loadDataDicValue(String dicValId);
   /**
    * 删除数组
    * @param ids 数据字典值id集合
    */
   public List<?> deleteDataDicVal(String[] ids);
   /**
    * 校验数据字典值编码是否重复
    * @param dataDicValCode 数据字典值对象
    * @return 以boolean的形式返回是否重复
    */
   public boolean checkDataDicValCode(DataDicValueVO dataDicValCode);
   /**
    * 查询数据字典值树
    * @return 以map的形式返回数据字典值
    */
   public Map<?, ?> queryDataDicValTree(String pk_dicId);
   /**
    * 查询维值树
    * @param list
    * @return
    */
   public List<Map<String,Map<?,?>>> queryDataDicVal(List<String> pk_dicId);
   
   public List<Map<String,String>> queryDataDicDetail(List<String> dicId);
   /**
    * 通过维值的id集合查询维度及维值信息
    * @param dicValIds
    * @return
    */
   public List<RoleAuthorityVO> queryDataDicInfo(List<String> dicValIds);
   /**
    * 通过数据字典值的编码查询数据字典值详细信息
    * @param typeCode
    * @return
    */
   public List<DataDicValueVO> getDatadicValueByTypeCode(String typeCode);
  /**
   * 通过维度编码查询维度详细信息
   * @param typeCode
   * TODO 实现这个方法
   */
//   public DataDicVO queryDataDicInfoByTypeCode(String typeCode);
}
