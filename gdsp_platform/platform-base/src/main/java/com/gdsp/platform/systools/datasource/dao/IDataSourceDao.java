package com.gdsp.platform.systools.datasource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.model.DsLibraryVO;
import com.gdsp.platform.systools.datasource.model.DsRegisterVO;

/**
 * 
 * 
* @ClassName: IDataSourceDao
* (数据源管理)
* @author songxiang
* @date 2015年10月28日 下午12:45:41
*
 */
@MBDao
public interface IDataSourceDao {

    /**
     * 
    * @Title: insert
    * (添加数据源)
    * @param dataSourceVO    参数说明
    * @return void    返回值说明
    *      */
    @Insert("insert into pt_datasource (id ,code,name ,username,password,type,comments,driver,ip,port,url,default_flag,span,permissionurl,isDefault,createtime,createby,version,px_url,path,product_version,authentication_model,keytabPath,krbConfPath,classify) "
            + "values (#{id},#{code},#{name},#{username},#{password},#{type},#{comments},#{driver},#{ip},#{port},#{url},#{default_flag},#{span},#{permissionurl},#{isDefault},#{createTime:BIGINT},#{createBy},#{version},#{px_url},#{path},#{product_version},#{authentication_model:BIGINT},#{keytabPath},#{krbConfPath},#{classify})")
    void insert(DataSourceVO dataSourceVO);

    /**
     * 
    * @Title: update
    * (修改数据源)
    * @param dataSourceVO    参数说明
    * @return void    返回值说明
    *      */
    @Update("update pt_datasource set code=#{code},name=#{name},username=#{username},password=#{password},type=#{type},driver=#{driver},comments=#{comments},ip=#{ip},port=#{port},url=#{url}, span=#{span},permissionurl=#{permissionurl},isDefault=#{isDefault},px_url=#{px_url},path=#{path},product_version=#{product_version},authentication_model=#{authentication_model},keytabPath=#{keytabPath},krbConfPath=#{krbConfPath},classify=#{classify} where id=#{id} ")
    void update(DataSourceVO dataSourceVO);

    /**
     * 
    * @Title: delete
    * (删除数据源)
    * @param ids    参数说明
    * @return void    返回值说明
    *      */
    @Delete("<script>delete from pt_datasource  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    /**
     * 
    * @Title: load
    * (加载操作对象)
    * @param id
    * @return    参数说明
    * @return DataSourceVO    返回值说明
    *      */
    @Select("select * from pt_datasource where id=#{id}")
    DataSourceVO load(String id);

    /**
     * 
    * @Title: queryDataSourceByCondition
    * (查询所以数据源)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<DataSourceVO>    返回值说明
    *      */
    Page<DataSourceVO> queryDataSourceByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort")Sorter sort);
    
    /**
     * 
    * @Title: queryDataSourceByCondition
    * (查询所以数据源)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<DataSourceVO>    返回值说明
    *      */
    List<DataSourceVO> queryDataSourceListByCondition(@Param("condition") Condition condition);

    /**
     * 
    * @Title: queryDataSourceByType
    * (查询某一个类型的数据源)
    * @param @param type
    * @param @return    设定文件
    * @return List<DataSourceVO>    返回类型
    *      */
    @Select("select * from pt_datasource where type=#{type}")
    public List<DataSourceVO> queryDataSourceByType(String type);

    /**
     * 
    * @Title: queryAllDataSourceByType
    * (查询所有数据源)
    * @param @return    设定文件
    * @return List<DataSourceVO>    返回类型
    *      */
    @Select("select * from pt_datasource ")
    public List<DataSourceVO> queryAllDataSourceByType();

    public int existSameCode(DataSourceVO dateSourceVO);

    /**
      * 
     * @Title: queryDataSourceByCode
     * (查询ByCode)
     * @param code
     * @return    参数说明
     * @return DataSourceVO
     *       */
    @Select("select * from pt_datasource where code=#{code}")
    public DataSourceVO queryDataSourceByCode(String code);

    /**
     * 
     * @Title: existDatasourceType 
     * @Description: 统计数据源类型的数量
     * @param dateSourceVO
     * @return
     * @return int    返回类型
     */
    @Select("<script>select count(1) from pt_datasource where type = #{type} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    int existDatasourceType(DataSourceVO dateSourceVO);
    /**
     * 根据启用报表类型查询启用的数据源
     * @param reportType
     * @return 以list集合形式返回数据源
     */
    public List<DataSourceVO> queryEnableDataSource(@Param("reportType") String[] reportType);
    /**
     * 查询类别下所有的目录
     * @param category 目录类别
     * @param handler 结果处理
     */
    void findDirTreeByCategory(String[] type, ResultHandler handler);

    /**
     * 
    * @Title: jdugeRegister
    * (检查是否已经存在该资源的引用注册信息)
    * @param pk_datasource
    * @param res_id
    * @return    参数说明
    * @return int    返回值说明：返回含有pk_datasource和res_id的注册信息个数
    *      */
    int jdugeRegister(@Param("pk_datasource")String pk_datasource, @Param("res_id")String res_id);

    /**
     * 插入注册服务信息
    * @param dsRegisterVO 
    * @Title: insertPtRegMsgVO
    * (这里用一句话描述这个方法的作用)    参数说明
    * @return void    返回值说明
     */
    void insertPtRegMsgVO(DsRegisterVO dsRegisterVO);

    /**
     * 更新注册服务信息
    * @Title: updatePtRegMsgVO
    * (这里用一句话描述这个方法的作用)
    * @param dsRegisterVO    参数说明
    * @return void    返回值说明
     */
    void updatePtRegMsgVO(DsRegisterVO dsRegisterVO);

    /**
     * 
    * @Title: deletePtRegMsgVO
    * (注销注册服务信息)
    * @param ds_id
    * @param res_id
    * @return    参数说明
    * @return Boolean    返回值说明
    */
    void deletePtRegMsgVO(@Param("ds_id")String ds_id, @Param("res_id")String res_id);

    /**
     * 
    * @Title: findAllDsRef
    * (查找所有注册信息)
    * @return    参数说明
    * @return List<DsRegisterVO>    返回值说明
     */
    List<DsRegisterVO> findAllDsRef();

    /**
     * 
     * (查询数据源模型列表)
     * @param condition
     * @return
     */
    List<DsLibraryVO> findDSProListByTypeAndVersion(@Param("ds_type")String ds_type, @Param("ds_version")String ds_version);
}
