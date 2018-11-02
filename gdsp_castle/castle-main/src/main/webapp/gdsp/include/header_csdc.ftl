<#import "/gdsp/tags/castle.ftl" as c>
<script></script>
<header class="main-header">
	<!--框架头部开始-->
	<div class="header_csdc">
		<!--头部左侧-->
		<#if hideHeaderLogo?? && hideHeaderLogo=='Y'>
		<div class="header_nl"><span class="sys_name"></span></div>
		<#else>
		<div class="header_l"><span class="sys_name"></span></div>
		
		</#if>
		<!--头部右侧-->
		<#if Context.contextUserId??>
                <#assign _messageCount = Context.getLoaderValue("messageInfData")?default(0)>
                <#assign _noticeCount = Context.getLoaderValue("noticeInfData")?default(0)>
			<div class="home_csdc">
				<ul>
					<#if sysHomePageState?if_exists=='Y'><li><div class="home-image-csdc" id="homePage" title="返回首页" data-placement="left"><a href="${ContextPath}/homepage.d"></a></div></li></#if>
					<li><i class="user-image-csdc"></i></li>
					<li>
						<a href="#" style="float: right;" data-toggle="dropdown" data-toggle="dropdown" title="${Context.contextUser.username?html}">
							<span class="user-name-csdc" >欢迎登录：<span>${Context.contextUser.username?if_exists?html}</span></span>
						</a>
					</li>
					<li class="dropdown"><a href="#" id="favorites_csdc" class="user-more-csdc" data-toggle="dropdown" events="{click:select}" ></a>
						<ul class="dropdown-menu">
	                        <li>
	                            <@c.Link title="消息" action=[c.opendlg("#messageDlg","${ContextPath}/tools/message/messageDlg.d","","900px",true)]><i class="fa fa-comment-o"></i>消息[<label id="message">${_messageCount?if_exists?html}</label>]</@c.Link>
	                        </li>
	                        <li>
	                            <@c.Link title="公告" action=[c.opendlg("#NoticeDlg","${ContextPath}/sysnotice/noticeDlg.d","","900px",true)]><i class="fa fa-envelope-o"></i>公告[<label id="notice">${_noticeCount?if_exists?html}</label>]</@c.Link>
	                        </li>
	                        <li>
	                            <@c.Link title="个人信息" action=[c.opendlg("#NoticeDlg","${ContextPath}/grant/user/personalInformation.d","","",true)]>
	                                <i class="fa fa-gear"></i>个人信息
	                            </@c.Link>
	                        </li>
                    	</ul>
                   	</li>
                   	<li><a id="favorites_drop" href="#" class="favorites-image" data-toggle="dropdown"></a>

                   	</li>
					<li><a href="${ContextPath}/logout.d" class="user-close-csdc"></a></li>
				</ul>
			</div>
			<#-- 该树为加载simpletree.js用,不能删除 -->
			<#-- <@c.SimpleTree id="testTree"></@c.SimpleTree> -->
			<div id="favorites_div" style="height : 500px;width : 300px;text-align:left;padding-right:0px;padding-top: 0px;right: 0px;top: 30px; position:absolute;display : none;z-index:9999">
				<@c.Box >
				<@c.BoxHeader style="height : 50px">
			        <button id="addFavorites" type="button" class="btn btn-primary" click="addToFavorites()"><i class="fa fa-save"></i>添加到收藏夹</button>
			        <input type="hidden" id="hideUrl" value="${url?if_exists}">
			        <input type="hidden" id="hideMenuName" value="${name?if_exists}">
			        <input type="hidden" id="favoritesUrl">
			        <input type="hidden" id="hideMenuId">
			        <input type="hidden" id="contextPath" value="${ContextPath}">
				</@c.BoxHeader>
					<@c.BoxBody>
						<@c.Box>
							<@c.Tabs>
								<@c.Tab  id="favoritesPanel" active=true title="收藏夹" tabclass="liheight">
									<div id="favorites" class="modal-body autoscroll" style="height : 300px; width : 276px;">
									</div>
								</@c.Tab>
								<@c.Tab  id="managePanel" title="收藏夹管理" tabclass="liheight">
									<input type="hidden" id="manage_id" value="">
									<div id="favoritesManage" class="modal-body autoscroll" style="height : 260px; width : 276px;">
									</div>					
									<div class="text-right">
										<@c.Button style="margin-right : 20px" icon="glyphicon glyphicon-trash" click="deleteFavorites()">删除</@c.Button>	
									</div>
									<div style="height : 10px">
									</div>
								</@c.Tab>
							</@c.Tabs>
						</@c.Box>
					</@c.BoxBody>
				</@c.Box>
			</div>
			</#if>
			<#if hideHeaderMenu?? && hideHeaderMenu=='Y'>
			<#else>
			<#-- 导航栏菜单 -->
			<div class="menu_csdc">
				<ul id="one_level">
					<!--li class="active_csdc"><a href="#">首页<div style="position: absolute;bottom: 0px;width:100px;height:10px;" class="menu_bg"></div></a></li-->
					<#if Context.contextUserId??>
		    			<#if Context.requestInfo??>
		                    <#assign curModule = Context.requestInfo.moduleId>
		                <#else>
		                    <#assign curModule = "">
		                </#if>
		                <#-- 导航栏菜单数据 -->
		                <#assign _pageHeaders = Context.getLoaderValue("pageNavigator")?default([])>
		                <#assign _menuHeaders = Context.getLoaderValue("menuNavigator")?default([])>
		                <#if _pageHeaders??>
		                    <#assign _pageCount = _pageHeaders?size>
		                <#else>
		                    <#assign _pageCount = 0>
		                </#if>
		                <#if _menuHeaders??>
		                    <#assign _menuCount = _menuHeaders?size>
		                <#else>
		                    <#assign _menuCount = 0>
		                </#if>
		
		                <#if _menuCount lt 4 && _pageCount gt 2>
		                    <#assign _collapsePage = 4 - _menuCount + 1/>
		                <#else>
		                    <#assign _collapsePage = 1/>
		                </#if>
		                <#if _collapsePage gte _pageCount-1>
		                    <#assign _collapsePage=-1/>
		                </#if>
		                <#if _menuCount gt 4 && _pageCount lt 2>
		                    <#assign _collapseMenu= 2 - _pageCount + 3/>
		                <#else>
		                    <#assign _collapseMenu = 4/>
		                </#if>
		                <#if _collapseMenu gte _menuCount-1>
		                    <#assign _collapsePage=-1/>
		                </#if>
		                <#-- 页面页签 -->
		                <#if _pageCount gt 0>
		                    <#list _pageHeaders  as h>
		                        <#if curModule==h.id>
		                            <#assign _activeSnippet=" class=\"drop active\"">
		                        <#else>
		                            <#assign _activeSnippet="">
		                        </#if>
		                        <#assign _paramSplit="?">
		                        <#if h.funcUrl?contains('?')><#assign _paramSplit="&"></#if>
		                        <#-- 第一个页面页签 -->
		                        <#if h_index==0>
		                            <li ${_activeSnippet} title="${h.funname?if_exists?html}" data-placement="top" style="max-width:300px;" class="dropdown" role="pagemenu" >
		                            	<#-- 在a标签上加上如下属性以实现点击弹出下拉框 class="dropdown-toggle" data-toggle="dropdown" ps:当前下拉方式为鼠标悬浮弹出-->
		                            	<a href="${ContextPath}/index.d"  >${h.funname?if_exists?html}</a>
		                            	<em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>
		                            	<#-- 页面页签下的页面列表,样式参考neris.css -->
		                            	<ul id="def" class="dropdown-menu" type="drop-pages">
			                            	<#assign page_size = h.childrenPage?size>
			                            	<#-- 只有一个页面时无下拉菜单 -->
			                            	<#if page_size != 1 >
			                            		<#list h.childrenPage as page>
			                            			<#if (page?size != 1)>
				                            			<li>
				                            				<a role="page-name" href="${ContextPath}/${page.funcUrl}${_paramSplit}__force__jtabs__=yes&__jtabs__tabId__=${page.tabId}&__jtabs__tabentitytype__=${page.tabEntityType}" >${page.pageName?if_exists?html}</a>
				                            				<input type="hidden" value=${page.id?if_exists} />
				                            				<input type="hidden" value=${page.pageid?if_exists}>
				                            				<!--<em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>-->
				                            			</li>
				                            			<#if page_index==0><#-- title="当前默认" -->
				                            				<a role="default-link" href="#" type="default" size="sm" ><img src="${__imagePath}/main/head/navibar/homepage_default.png"></a>
				                            			<#else>
				                            				<a role="default-link" href="#" type="default" size="sm" click="setUserDefaultHomePage(this)" ><img src="${__imagePath}/main/head/navibar/homepage_setdefault.png" ></img></a>
				                            			</#if>
				                            		<#else>
				                            				
			                            			</#if>
			                            		</#list>
			                            	</#if>
		                            	</ul>
		                        	</li>
		                        <#elseif _collapsePage == h_index>
		                            <li title="${h.funname?if_exists?html}" data-placement="top" style="max-width:300px;" ${_activeSnippet}><a href="${ContextPath}/${h.funcUrl}${_paramSplit}__force__jtabs__=yes&__jtabs__tabId__=${h.tabId}&__jtabs__tabentitytype__=${h.tabEntityType}" class="">${h.funname?if_exists?html}</a><em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em></li>
		                        <li class="dropdown" style="width:96px;"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="text-align:center;"><span class="caret"></span></a>
		                        <ul role="menu" class="dropdown-menu">
		                        <#-- 其他的页面页签 -->
		                        <#else>
		                        	<li ${_activeSnippet} title="${h.funname?if_exists?html}" data-placement="top" class="dropdown" role="pagemenu" style="max-width:300px;">
		                        		<a href="${ContextPath}/${h.funcUrl}${_paramSplit}__force__jtabs__=yes&__jtabs__tabId__=${h.tabId}&__jtabs__tabentitytype__=${h.tabEntityType}" class="" style="background-color: transparent;">${h.funname?if_exists?html}</a><em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>
		                            	<ul id="def" class="dropdown-menu" type="drop-pages">
		                            		<#assign page_size = h.childrenPage?size>
		                            		<#-- 只有一个页面时无下拉菜单 -->
		                            		<#if page_size != 1 >
		                            			<#list h.childrenPage as page>
			                            			<li>
			                            				<a role="page-name" href="${ContextPath}/${page.funcUrl}${_paramSplit}__force__jtabs__=yes&__jtabs__tabId__=${page.tabId}&__jtabs__tabentitytype__=${page.tabEntityType}" >${page.pageName?if_exists?html}</a>
			                            				<input type="hidden" value=${page.id?if_exists} />
			                            				<input type="hidden" value=${page.pageid?if_exists}>
			                            				<!--<em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>-->
			                            			</li>
			                            			<#if page_index==0>
			                            				<a role="default-link" href="#" type="default" size="sm" ><img src="${__imagePath}/main/head/navibar/homepage_default.png" ></a>
			                            			<#else>	
			                            				<a role="default-link" href="#" type="default" size="sm" click="setUserDefaultHomePage(this)" ><img src="${__imagePath}/main/head/navibar/homepage_setdefault.png" ></a>
			                            			</#if>
		                            			</#list>
		                            		</#if>	
		                            	</ul>
		                            </li>
		                        </#if>
		                    </#list>
		                    <#if _collapsePage gt 0>
		                    </ul>
		                    </li>
		                    </#if>
		                </#if>
		                <#-- 功能页签 -->
		                <#if _menuCount gt 0>
		                    <#list _menuHeaders  as h>
		                        <#if curModule==h.id>
		                            <#assign _activeSnippet=" class=\"active\"">
		                        <#else>
		                            <#assign _activeSnippet="">
		                        </#if>
		                        <#if _collapseMenu == h_index>
		                            <li title="${h.funname?if_exists?html}" data-placement="top" style="max-width:300px;" ${_activeSnippet}><a href="${ContextPath}/module/func/${h.id}.d" class="">${h.funname?if_exists?html}</a><em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em></li>
		                            <#if _collapseMenu < _menuCount - 1>
		                          <li class="dropdown" style="width:96px;"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="text-align:center;"><span class="caret"></span></a>
		                          <ul role="menu" class="dropdown-menu">
		                          </#if>
		                        <#else>
		                            <li title="${h.funname?if_exists?html}" data-placement="top" style="max-width:300px;" ${_activeSnippet}><a href="${ContextPath}/module/func/${h.id}.d" class="1" style="background-color: transparent;">${h.funname?if_exists?html}</a><em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em></li>
		                        </#if>
		                    </#list>
		                    <#if _collapseMenu gt 0>
		                    <#if _collapseMenu < _menuCount - 1>
		                    </ul>
		                    </#if>
		                    </li>
		                    </#if>
		                </#if>
		            </#if>
				</ul>
			</div>
			</#if>
	</div>
</header>
