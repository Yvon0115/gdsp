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
                <#if _menuCount gt 4 && _pageCount lt 2>
                    <#assign _collapseMenu= 2-_pageCount+3/>
                <#else>
                    <#assign _collapseMenu=4/>
                </#if>
                <#if _collapseMenu gte _menuCount-1>
                    <#assign _collapsePage=-1/>
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