package com.gdsp.platform.systools.indexanddim.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.IndTreeVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;
import com.gdsp.platform.systools.indexanddim.service.IIndGroupService;

/**
 * 指标分组管理控制类 <br/>
 * 
 * @author hy
 */
@Controller
@RequestMapping("indexanddim/indexgroup")
public class IndGroupController {

    @Autowired
    IIndGroupService indGroupService;

    /**
     * 
     * 显示所有指标组，以树形结构展示 <br/>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model) {
        //指标分组树
        List<IndTreeVO> queryAllIndGroup = indGroupService.queryAllIndGroup();
        //"pk_ind"为点击节点后储存节点innercode值得变量
        model.addAttribute("pk_ind", "");
        model.addAttribute("nodes", queryAllIndGroup);
        return "indexanddim/indexgroup/list";
    }
    @RequestMapping("/dimGroupTree.d")
    @ResponseBody
    public String dimGroupTree(){
        List<IndTreeVO> dimTree=indGroupService.queryAllIndGroup();
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "groupName");
        if(dimTree.size()!=0)
            return   JsonUtils.formatList2TreeViewJson(dimTree, nodeAttr);
        else{
            return "";
            }
      }

    /**
     * 
     * 完成添加、修改任务后刷新树形页面 <br/>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/groupList.d")
    @ViewWrapper(wrapped = false)
    public String groupList(Model model) {
//        model.addAttribute("pk_ind", "");
//        model.addAttribute("nodes", indGroupService.queryAllIndGroup());
        return "indexanddim/indexgroup/ind-tree";
    }

    /**
     * 
     * 查询选中指标组下所有指标 <br/>
     * 
     * @param model
     * @param pageable
     * @param innercode 指标组innercode
     * @param condition
     * @param groupId  指标组id
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listDate(Model model, Pageable pageable, String groupId, Condition condition) {
        IndTreeVO node=indGroupService.queryIndGroupById(groupId);//通过id获得节点信息
        Page<IndGroupRltVO> queryChildInd =null;
        List<IndGroupRltVO> content=null;
        if(node==null){
            queryChildInd=indGroupService.queryChildInd(null, condition, pageable); 
             content = queryChildInd.getContent();
        }else{
            queryChildInd = indGroupService.queryChildInd(
                    node.getInnercode(), condition, pageable);
           content = queryChildInd.getContent();
        }
        for (IndGroupRltVO VO : content) {
            if ("Y".equalsIgnoreCase(VO.getIndexInfoVO()
                    .getOnlyflexiablequery())) {
                VO.getIndexInfoVO().setOnlyflexiablequery("是");
            } else {
                VO.getIndexInfoVO().setOnlyflexiablequery("否");
            }
        }
        model.addAttribute("inds", queryChildInd);
        model.addAttribute("groupId", groupId);
        return "indexanddim/indexgroup/list-data";
    }

    /**
     * 
     * 进入指标组的添加页面 <br/>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false)
    public String add(Model model) {
        model.addAttribute("editType", "add");
        return "indexanddim/indexgroup/indgroup-add";
    }

    /**
     * 
     * 检查指标组编码的唯一性 <br/>
     * 
     * @param indTreeVO
     * @return
     */
    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(IndTreeVO indTreeVO) {
        return indGroupService.uniqueCheck(indTreeVO);
    }

    /**
     * 
     * 添加指标组时选择上级指标组<br/>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/queryParentGroup.d")
    @ViewWrapper(wrapped = false)
    public String queryParentGroup() {
//        Sorter sort = new Sorter(Direction.ASC, "innercode");
//        model.addAttribute("nodes", indGroupService.queryAllIndGroup());
        return "indexanddim/indexgroup/parentgroup";
    }
    @RequestMapping("/parentGroup.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public String parentGroup(){
//        Sorter sort = new Sorter(Direction.ASC, "innercode");
        List<IndTreeVO> allIndGroup=indGroupService.queryAllIndGroup();
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "groupName");
        if(allIndGroup.size()!=0)
            return   JsonUtils.formatList2TreeViewJson(allIndGroup, nodeAttr);
        else{
            return "";
            } 
    }

    /**
     * 
     * 指标组信息的添加或修改 <br/>
     * 
     * @param indTreeVO
     * @return
     */
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(IndTreeVO indTreeVO) {
        if (indTreeVO.getId() == null || indTreeVO.getId() == "") {
            // 取得上级指标组id
            String parentId = indTreeVO.getInnercode();
            IndTreeVO treeVO = indGroupService.queryIndGroupByInn(parentId);
            // 设置父级id
            if (treeVO != null) {
                indTreeVO.setPk_fatherid(treeVO.getId());
                indTreeVO.setInnercode(treeVO.getInnercode());
                indTreeVO = (IndTreeVO) TreeCodeHelper.generateTreeCode(indTreeVO,
                        treeVO.getInnercode());
            }else{
                indTreeVO = (IndTreeVO) TreeCodeHelper.generateTreeCode(indTreeVO, null);
            }
            indGroupService.insert(indTreeVO);
            return AjaxResult.SUCCESS;
        }
        //取到id，执行修改功能
        else {
            indGroupService.changeVersion(indTreeVO);
            indGroupService.update(indTreeVO);
            return AjaxResult.SUCCESS;
        }
    }

    /**
     * 
     * 进入指标组的修改页面 <br/>
     * 
     * @param model
     * @param id  指标组id
     * @return
     */
    @RequestMapping("/editGroup.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editGroup(Model model, String id) {
        model.addAttribute("indTreeVO", indGroupService.queryIndGroupById(id));
        model.addAttribute("editType", "editGroup");
        return "indexanddim/indexgroup/indgroup-add";
    }

    /**
     * 
     * 指标组的删除 <br/>
     * 
     * @param id 指标组id
     * @return
     */
    @RequestMapping("/deleteGroup.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object deleteGroup(String id) {
        Condition condition = new Condition();
        List<IndTreeVO> indGroupList = indGroupService.queryChildIndGroupById(id, condition, null, true);
        //判断该指标组下是否存在子指标组，若存在则不能直接删除
        if (indGroupList != null && indGroupList.size() > 1) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "该指标组存在下级，请先清空下级指标组", null, false);
        }
        //condition = new Condition();
        List<IndGroupRltVO> indGroupRltVO = indGroupService.queryRltByGroupId(id);
        //判断该指标组下是否存在指标，若存在则不能直接删除
        if (indGroupRltVO != null && indGroupRltVO.size() > 0) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "该指标组存在指标，请先清空该组指标", null, false);
        }
        //不存在子指标组且没有关联指标，进行删除
        indGroupService.deleteIndGroup(id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     * 查询不在该指标组下的指标 <br/>
     * 只显示未添加的指标
     * @param model
     * @param groupId  指标组id
     * @param pageable
     * @return
     */
    @RequestMapping("/addIndex.d")
    @ViewWrapper(wrapped = false)
    public String addIndex(Model model, String groupId, Pageable pageable, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "indexCode");
        model.addAttribute("groupId", groupId);
        Page<IndexInfoVO> queryRemainInd = indGroupService.queryRemainInd(groupId, pageable, sort, condition);
        List<IndexInfoVO> content = queryRemainInd.getContent();
        for (IndexInfoVO vo : content) {
            if ("Y".equalsIgnoreCase(vo.getOnlyflexiablequery())) {
                vo.setOnlyflexiablequery("是");
            } else {
                vo.setOnlyflexiablequery("否");
            }
        }
        model.addAttribute("inds", queryRemainInd);
        return "indexanddim/indexgroup/ind-add";
    }

    /**
     * 
     * 给指标组添加指标（不在该指标组下的指标）<br/>
     * 
     * @param model
     * @param id
     * @param pageable
     * @return
     */
    @RequestMapping("/addIndList.d")
    @ViewWrapper(wrapped = false)
    public String addIndList(Model model, String groupId, Pageable pageable, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "indexCode");
        model.addAttribute("groupId", groupId);
        Page<IndexInfoVO> queryRemainInd = indGroupService.queryRemainInd(groupId, pageable, sort, condition);
        List<IndexInfoVO> content = queryRemainInd.getContent();
        for (IndexInfoVO vo : content) {
            if ("Y".equalsIgnoreCase(vo.getOnlyflexiablequery())) {
                vo.setOnlyflexiablequery("是");
            } else {
                vo.setOnlyflexiablequery("否");
            }
        }
        model.addAttribute("inds", queryRemainInd);
        return "indexanddim/indexgroup/indlist-data";
    }

    /**
     * 
     * 给指标组下添加指标 <br/>
     * 
     * @param groupId  指标组id
     * @param id 指标id
     * @return
     */
    @RequestMapping("/saveIndOnGroup.d")
    @ResponseBody
    public Object saveIndOnGroup(String groupId, String... id) {
        indGroupService.addRelation(groupId, id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     * 移除指标组下的指标，即删除指标组与指标的关联关系 <br/>
     * 
     * @param id （row.id）
     * @return
     */
    @RequestMapping("/deleteRelation.d")
    @ResponseBody
    public Object deleteRelation(String... id) {
        if (id != null && id.length > 0) {
            indGroupService.deleteRelation(id);
        }
        return AjaxResult.SUCCESS;
    }
}
