package com.gdsp.ptbase.appcfg.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.config.global.dao.IDefDocDao;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.ptbase.appcfg.dao.ILayoutColumnDao;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.service.ILayoutColumnService;
import com.gdsp.ptbase.appcfg.service.IPageService;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;

/**
 * 列布局实现类
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
@Service
@Transactional(readOnly = true)
public class LayoutColumnService implements ILayoutColumnService {

    /**
     * 数据访问对象
     */
    @Resource
    private ILayoutColumnDao layoutColumnDao;

    @Resource
    private IDefDocDao          defDocDao;
	@Resource
	private IDefDocService      ddService;
    @Resource
    private IPageWidgetService  pwService;
    @Resource
    private IPageService        pService;
    @Resource
    private IDefDocListService  defDocListService;
	 
	@Override
	public List<LayoutColumnVO> findLayoutColumns(String layoutId) {
		return layoutColumnDao.findLayoutColumns(layoutId);
	}

	
	@Override
	@Transactional
	public void save(List<String> columnIdArray,DefDocVO defDocVO) {

		int sum=0;
		// 至少输入2个，判断前两个输入是否为整数
		for (int i = 0; i < columnIdArray.size(); i++) {
			if (isNumeric(columnIdArray.get(i))) {
				throw new BusinessException("您输入的第"+(i+1)+"列宽度不为整数，请输入100以内的整数！");
			}
			int columnint = Integer.parseInt(columnIdArray.get(i)) ;
			sum=sum+columnint;
		}
		if (sum != 100) {
			throw new BusinessException("各列宽度总和相加不等于100，请检查");
		}else {
			ddService.saveDefDoc(defDocVO);
			//保存DefDocVO成功后，获得系统码表保存成功后的id
			String layoutId = defDocVO.getId();
			//保存layoutColumnVO
			String before = "ct_column";
			LayoutColumnVO layoutColumnVO = null;
			for (String columnId : columnIdArray) {
				int colspan = Integer.parseInt(columnId);
				//系数15，自定义布局的columnID和colspan都分别乘以系数15，用以区别boostrap的12栅栏属性
				//随机三位数是为了区分不同的列columnID，否则在页面加载的时候，相同的columnID会刷新出两遍组件
					colspan=(colspan*15);
					layoutColumnVO = new LayoutColumnVO();
					layoutColumnVO.setColumn_id(before + RandomStringUtils.randomNumeric(3));
					layoutColumnVO.setLayout_id(layoutId);
					layoutColumnVO.setColspan(colspan);
					layoutColumnVO.setSortnum(layoutColumnDao.findSortnum() + 1);
					layoutColumnDao.insert(layoutColumnVO);
			}
		}
	}
		//计算是否为整数
	protected  boolean isNumeric(String str){  
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return true;  
		   	}  
		  }  
		  return false;  
		}

	@Override
	@Transactional
	public void delete(String layoutId, List<String> pageIds) {
		//删除码表维护中的布局信息
		String [] layoutIds={layoutId};
		defDocDao.delete(layoutIds);
		//更新已保存自定义布局的页面为默认单列布局，
		for (String pageid : pageIds) {
			pwService.updateDefColumn(pageid);
			pService.updateDefLayout(pageid);
		}
		layoutColumnDao.delete(layoutId);
	}
	
	@Override
	public boolean docNameCheck(String typeCode, String docName) {
		//根据typeCode查询当前类型的所有数据
		List<DefDocVO> defVO = defDocListService.getDefDocsByTypeID(typeCode);
		//验证是否重名
		boolean name = true;
		if(defVO.size()>0){
			for(int i =0;i<defVO.size();i++){
				if(docName.equals(defVO.get(i).getDoc_name())){
					name = false;
				}
			}
		}
		return name;
	}
}
