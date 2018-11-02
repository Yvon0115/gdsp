package com.gdsp.dev.web.mvc.interceptor;

import org.springframework.web.servlet.ModelAndView;

/**
 * 包装模板处理类，当{@link DefaultMvcInterceptor}在进行包装模板处理时，只添加了模板页面，<br>
 * 但并不会添加模板页面可能会需要用到的数据。<br>
 * 此接口就是为了支持添加模板所需数据。<br>
 * 
 * 用法示例：
 * 系统启用了包装模板为decorate/castle-main-vertical(即在gdsp-view.properties中的配置为：view.defaultDecorate=decorate/castle-main-vertical)<br>
 * 如果包装模板需要一些特殊数据来显示，则需要在gdsp-view.properties中添加如下配置：<br>
 * decorate/castle-main-vertical=com.gdsp.xxx.xxx.VerticalDecorator<br><br>
 * 
 * com.gdsp.xxx.xxx.VerticalDecorator为实现{@link IDecorator}接口的实现类<br>
 * 
 * 在方法{@link IDecorator#handleModelAndView(ModelAndView)}中，往参数ModelAndView中，添加所需要数据。
 * 
 * 
 * @author zhaojianjun
 *
 */
public interface IDecorator {

    public void handleModelAndView(ModelAndView mv);
}
