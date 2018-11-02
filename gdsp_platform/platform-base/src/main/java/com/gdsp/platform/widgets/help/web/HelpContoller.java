package com.gdsp.platform.widgets.help.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.widgets.help.model.HelpVO;
import com.gdsp.platform.widgets.help.service.IHelpService;
import com.gdsp.platform.widgets.help.utils.FileUploadUtils;

/**
 * Created on 2015/6/29
 */
@Controller
@RequestMapping("help")
public class HelpContoller {
	private static final Logger logger = LoggerFactory.getLogger(HelpContoller.class);
    @Autowired
    private IHelpService         helpService;
    @Autowired
    private IUserQueryPubService userPubService;

    /*****
     * 用户管理主界面
     * 
     * @param model
     * @param pk_org
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model, String pk_org, Condition condition, Pageable pageable) {
        UserVO vo = userPubService.load(AppContext.getContext().getContextUserId());
        model.addAttribute("usertype", vo.getUsertype());
        Sorter sort = new Sorter(Direction.ASC, "attach_name");
        Page<HelpVO> page = helpService.queryHelpVosPageByCondition(null, pageable, sort);
        model.addAttribute("noticeVoPages", page);
        return "help/list";
    }

    /****
     * 刷新界面
     * 
     * @param model
     * @param pk_org
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, String pk_org, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "attach_name");
        Page<HelpVO> page = helpService.queryHelpVosPageByCondition(null, pageable, sort);
        model.addAttribute("noticeVoPages", page);
        UserVO vo = userPubService.load(AppContext.getContext().getContextUserId());
        model.addAttribute("usertype", vo.getUsertype());
        return "help/list-data";
    }

    /*****
     * 添加用户界面
     * 
     * @param model
     * @param pk_org
     * @return
     */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String pk_org) {
        return "help/form";
    }

    /***
     * 编辑用户界面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("user", helpService.load(id));
        return "help/form";
    }

    /*****
     * 保存 功能
     * 
     * @param user
     * @return
     * @throws IOException 
     */
    @RequestMapping("/save.d")
    public String save(HelpVO helpVO, HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("fileName");
        String path = FileUploadUtils.uploadFileReturnPath(file.getInputStream(), file.getOriginalFilename());
        helpVO.setAttach_path(path);
        helpVO.setAttach_name(file.getOriginalFilename());
        helpService.saveHelpVO(helpVO);
        return "redirect:list.d";
    }

    /****
     * 删除用户，可多个
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id == null)
            return new AjaxResult(AjaxResult.STATUS_ERROR, "页面出现问题，请刷新页面后再试。");
        helpService.deleteHelp(id);
        return new AjaxResult(AjaxResult.STATUS_SUCCESS, "操作成功。");
    }

    @RequestMapping("/downLoad.d")
    @ViewWrapper(wrapped = false)
    public String downLoad(Model model, HttpServletResponse response, HttpServletRequest request, String id){
        String path = AppConfig.getInstance().getString("portal.help.upload.addr");
        HelpVO helpVO = helpService.load(id);
        String relativePath = helpVO.getAttach_path();
        File file = new File(path + relativePath);
        OutputStream os = null;
        FileInputStream fs = null;
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        try {
			request.setCharacterEncoding("UTF-8");
			String name = helpVO.getAttach_name();
	        name = new String(name.getBytes("gbk"), "iso-8859-1");
	        response.setHeader("Content-disposition", "attachment;filename=" + name);
	        os = response.getOutputStream();
	        fs = new FileInputStream(file);
	        byte bytes[] = new byte[1024];
	        int len = 0;
	        while ((len = fs.read(bytes)) != -1) {
	            os.write(bytes, 0, len);
	        }
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}finally{
			FileUtils.close(os);
			FileUtils.close(fs);
		}
        return null;
    }
}
