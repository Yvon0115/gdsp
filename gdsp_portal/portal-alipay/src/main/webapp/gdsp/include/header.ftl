<#import "/gdsp/tags/castle.ftl" as c>
<header class="main-header">
    <!-- Logo -->
    <a href="${ContextPath}/homepage.d" class="logo">
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">&nbsp;</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
        <div id="navbar-collapse" class="collapse navbar-collapse pull-left navbar-custom-menu">
            <ul class="nav navbar-nav">
            <#if Context.contextUserId??>
                <#if Context.requestInfo??>
                    <#assign curModule = Context.requestInfo.moduleId>
                <#else>
                    <#assign curModule = "">
                </#if>
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
                    <#assign _collapsePage= 4-_menuCount+1/>
                <#else>
                    <#assign _collapsePage=1/>
                </#if>
                <#if _collapsePage gte _pageCount-1>
                    <#assign _collapsePage=-1/>
                </#if>
                <#if _menuCount gt 5 && _pageCount lt 2>
                    <#assign _collapseMenu= 2-_pageCount+4/>
                <#else>
                    <#assign _collapseMenu=5/>
                </#if>
                <#if _collapseMenu gte _menuCount-1>
                    <#assign _collapsePage=-1/>
                </#if>
                <#if _pageCount gt 0>
                    <#list _pageHeaders  as h>
                        <#if curModule==h.id>
                            <#assign _activeSnippet=" class=\"active\"">
                        <#else>
                            <#assign _activeSnippet="">
                        </#if>
                         <#assign _paramSplit="?">
		                 <#if h.funcUrl?contains('?')><#assign _paramSplit="&"></#if>
                         <#if h_index==0>                 
		                            <li ${_activeSnippet} title="${h.funname?if_exists?html}" data-placement="top" style="max-width:300px;" class="dropdown" role="pagemenu" >
		                            	<#-- 在a标签上加上如下属性以实现点击弹出下拉框 class="dropdown-toggle" data-toggle="dropdown" ps:当前下拉方式为鼠标悬浮弹出-->
		                            	<a href="${ContextPath}/index.d"  >${h.funname?if_exists?html}</a>
		                            	<em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>
		                            	<#-- 页面页签下的页面列表,样式参考neris.css -->
			                            	<#assign page_size = h.childrenPage?size>
			                            	<#-- 只有一个页面时无下拉菜单 -->
			                            	<#if page_size != 1 >
				                            	<ul id="def" class="dropdown-menu" type="drop-pages">
				                            		<#list h.childrenPage as page>
				                            			<#if (page?size != 1)>
					                            			<li class="page_link">
					                            				<a role="page-name" href="${ContextPath}/${page.funcUrl}${_paramSplit}__force__jtabs__=yes&__jtabs__tabId__=${page.tabId}&__jtabs__tabentitytype__=${page.tabEntityType}">${page.pageName?if_exists?html}</a>
					                            				<input type="hidden" value=${page.id?if_exists} />
					                            				<input type="hidden" value=${page.pageid?if_exists}>
					                            				<!--<em style="position: absolute;bottom: 0px;width:100px;height:10px;"></em>-->
					                            			</li>
					                            			<#if page_index==0><#-- title="当前默认" -->
					                            				<a role="default-link" href="#"  style="display: inline-block;" type="default" size="sm" ><img  style="vertical-align: middle;" src="${__imagePath}/main/head/navibar/homepage_default.png"></a>
					                            			<#else>
					                            				<a role="default-link" href="#"  style="display: inline-block;" type="default" size="sm" click="setUserDefaultHomePage(this)" ><img style="vertical-align: middle;" src="${__imagePath}/main/head/navibar/homepage_setdefault.png" ></img></a>
					                            			</#if>
					                            		<#else>
				                            			</#if>
				                            		</#list>
				                            	</ul>
			                            	</#if>
		                        	</li>
		                        	
                        <#elseif _collapsePage == h_index>
                            <li ${_activeSnippet}><a href="${ContextPath}/${h.funcUrl}">${h.funname?if_exists?html}</a></li>
                        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                        <ul role="menu" class="dropdown-menu">
                        <#else>
                            <li ${_activeSnippet}><a href="${ContextPath}/${h.funcUrl}">${h.funname?if_exists?html}</a></li>
                        </#if>
                    </#list>
                    <#if _collapsePage gt 0>
                    </ul>
                    </li>
                    </#if>
                </#if>
                <#if _menuCount gt 0>
					<#assign _dpcCustomerKey = Context.getDpcCustomerKey()?default("") >
					<#assign _dpcLoginTokenKey = Context.getDpcLoginTokenKey()?default("") >
					<#assign _dpcCustomerId = Context.getDpcCustomerId()?default("") >
					<#assign _dpcLoginToken = Context.getDpcLoginToken()?default("") >
					<#assign _urlParam = "?"+_dpcCustomerKey+"="+_dpcCustomerId+"&"+_dpcLoginTokenKey+"="+_dpcLoginToken >
					<#list _menuHeaders  as h>
						<#if curModule==h.id>
							<#assign _activeSnippet=" class=\"active\"">
						<#else>
							<#assign _activeSnippet="">
						</#if>
						<#if _collapseMenu == h_index>
						   <#if h.url?starts_with("http")>
                       			<li ${_activeSnippet}><a href="${h.url+_urlParam}" target="_blank">${h.funname?if_exists?html}</a></li>
						   	<#else>
                          		<li ${_activeSnippet}><a href="${ContextPath}/module/func/${h.id}.d">${h.funname?if_exists?html}</a></li>
						   	</#if>
                          <#if _collapseMenu lt _menuCount - 1>
		                    <li class="dropdown" style="width:40px;"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
		                    <ul role="menu" class="dropdown-menu">
                          </#if>
                        <#else>
							<#if h.url?starts_with("http")>
	                       		<li ${_activeSnippet}><a href="${h.url+_urlParam}" target="_blank">${h.funname?if_exists?html}</a></li>
							<#else>
                      	  		<li ${_activeSnippet}><a href="${ContextPath}/module/func/${h.id}.d">${h.funname?if_exists?html}</a></li>
							</#if>
                        </#if>
                    </#list>
                    <#if _collapseMenu gt 0>
                    </ul>
                    </li>
                    </#if>
                </#if>
            </#if>
            </ul>
            <!-- 用户名太长 被挤 <form role="search" class="navbar-form navbar-left">
				 <div class="form-group">
					 <input type="text" placeholder="Search" id="navbar-search-input" class="form-control">
				 </div>
			 </form>-->
        </div>
        
        
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
	                            <@c.Link title="公告" action=[c.opendlg("#NoticeDlg","${ContextPath}/public/notice/noticeDlg.d","","900px",true)]><i class="fa fa-envelope-o"></i>公告[<label id="notice">${_noticeCount?if_exists?html}</label>]</@c.Link>
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
			<div id="favorites_div" style="height : 500px;width : 300px;text-align:left;padding-right:0px;padding-top: 0px;right: 0px;top: 40px; position:absolute;display : none;z-index:9999">
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
								<@c.Tab  id="favoritesPanel" active=true title="收藏夹" tabclass="liheight" style="background-color:#ffffff;color:#25abf2;">
									<div id="favorites" class="modal-body autoscroll" style="height : 300px; width : 276px;">
									</div>
								</@c.Tab>
								<@c.Tab  id="managePanel" title="收藏夹管理" tabclass="liheight" style="background-color:#ffffff;color:#25abf2;">
									<input type="hidden" id="manage_id" value="">
									<div id="favoritesManage" class="modal-body autoscroll" style="height : 260px; width : 276px;">
									</div>					
									<div class="text-right">
										<@c.Button style="margin-right:20px" icon="glyphicon glyphicon-trash" click="deleteFavorites()">删除</@c.Button>	
									</div>
									<div style="height:10px">
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
        </#if>
        
    </nav>
</header>