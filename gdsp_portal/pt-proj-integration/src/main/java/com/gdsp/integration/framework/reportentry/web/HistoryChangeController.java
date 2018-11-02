/**
 * 
 */
package com.gdsp.integration.framework.reportentry.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.integration.framework.reportentry.model.HistoryChangeVO;
import com.gdsp.integration.framework.reportentry.service.IHistoryChangeService;
import com.gdsp.platform.grant.utils.GrantUtils;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;


/**
 * @author wangliyuan
 *
 */
@Controller
@RequestMapping("framework/history")
public class HistoryChangeController {
    @Autowired
    private IHistoryChangeService historyChangeService;
    @Autowired
    private IPageWidgetService pageWidgetService;
    private static final Logger logger=LoggerFactory.getLogger(HistoryChangeController.class);
    @RequestMapping("/list.d")
    @ViewWrapper(wrapped=false)
    public String list(Model model,String link_id,Pageable pageable,String type,String report_name,String flag){    
        String loginUserID = AppContext.getContext().getContextUserId();
        List<HistoryChangeVO> historyChangeVO=null;
        //将页面发布成功能节点查看历史变更，通过ac_page_widget的id 获取报表id
        if(flag!=null &&flag.equals("1")){
          String reportID = pageWidgetService.findWidgetIdByPageId(link_id);
          if(reportID!=null){
            historyChangeVO=historyChangeService.findHistoryChangeVOBylinkId(reportID);  
          }else{
              logger.error("查询无此记录");
          }
        }else{
            historyChangeVO=historyChangeService.findHistoryChangeVOBylinkId(link_id);  
        }
        if(historyChangeVO!=null){
            if(historyChangeVO.size()!=0){
                historyChangeVO.get(0).setState("Y");
            }
        }
//      GrantUtils.convertListToPage(historyChangeVO, pageable);
        model.addAttribute("historyChangeVO",GrantUtils.convertListToPage(historyChangeVO, pageable));
        if(historyChangeVO!=null){
            model.addAttribute("size", historyChangeVO.size());
        }else{
            model.addAttribute("size", 0);
        }
        model.addAttribute("link_id", link_id);
        model.addAttribute("report_name", report_name);
        model.addAttribute("loginUserID", loginUserID);
        model.addAttribute("type", type);
        return "integration/framework/history/list";  
    }
    
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped=false)
   public String listData(Model model, Condition condition, String link_id,Pageable pageable,String type) {
       String loginUserID = AppContext.getContext().getContextUserId();
       Sorter sort = new Sorter(Direction.DESC, "createTime");
       //通过ac_page_widget的id 获取报表id
       String reportID = pageWidgetService.findWidgetIdByPageId(link_id);
           if(StringUtils.isNotEmpty(reportID)){
               condition.addExpression("link_id",reportID);
           }else{//若查询的报表id为空说明参数传来的link_id为报表id
               if(StringUtils.isNotEmpty(link_id)){
                   condition.addExpression("link_id",link_id);
               }
           }
      List<HistoryChangeVO> historyChangeVO = historyChangeService.queryByCondition(condition, sort);
      if(historyChangeVO.size()!=0&&!StringUtils.isNotBlank(condition.getFreeValue())){
          historyChangeVO.get(0).setState("Y");
      }
//      GrantUtils.convertListToPage(historyChangeVO, pageable);
      model.addAttribute("historyChangeVO", GrantUtils.convertListToPage(historyChangeVO, pageable));
      model.addAttribute("size", historyChangeVO.size());
      model.addAttribute("link_id", link_id);
      model.addAttribute("loginUserID", loginUserID);
      model.addAttribute("type", type);
      return "integration/framework/history/list-data";
   }
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped=false,onlyAjax=true)
   public String add(Model model, String link_id,String report_name,Pageable pageable) {
       List<HistoryChangeVO> historyChangeVO=historyChangeService.findHistoryChangeVOBylinkId(link_id);
       if(historyChangeVO.size()==0){//判断是否存在数据
           model.addAttribute("link_id", link_id);
           model.addAttribute("report_name", report_name);  
       }else{
           String opType="1";//当不是第一个报表说明是，变更类型只能选择变更
           model.addAttribute("opType", opType);
           model.addAttribute("link_id", link_id);
           model.addAttribute("report_name", report_name);
       }
       return "integration/framework/history/form";
   }
    
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(HistoryChangeVO historyChangeVO) {
        Condition condition=new Condition();
        int findHistoryChangeVOByCon = historyChangeService.findHistoryChangeVOByCon(condition,historyChangeVO);
        if(findHistoryChangeVOByCon>0)
            {
                return new AjaxResult(AjaxResult.STATUS_ERROR,"创建类型已存在请选择变更类型！");
            }
        if(historyChangeService.saveHistoryChange(historyChangeVO)){
            return AjaxResult.SUCCESS;
        }else{
            return new AjaxResult(AjaxResult.STATUS_ERROR,"请检查变更日期");
        }
        
        
    }
    
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped=false,onlyAjax=true)
    public String edit(Model model,String id,String link_id ){ 
        model.addAttribute("historyChangeVO", historyChangeService.load(id));
        model.addAttribute("link_id", link_id);
        return "integration/framework/history/form";
    }
    
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String id) {
        historyChangeService.delete(id);
        return AjaxResult.SUCCESS;
    }
}
