<#import "/gdsp/tags/castle.ftl" as c>
<script>
$(function(){

$(".menu_csdc li").click(function(){//.end 节点元素返回上一步操作
//$(this).addClass('open').children('.sub_menu').slideDown().end().siblings().removeClass('open').children('.sub_menu').slideUp();
//$(this).children('.arrow').addClass('on')
	console.log($(this).children('a'));
	$(this).children('a').addClass('bgimg');
	$(this).children('div').addClass('menu_bg');
	console.log($(this).children('div'));
});

})
</script>
<header class="main-header">
	<!--框架头部开始-->
	<div class="header_csdc">
		<!--头部左侧-->
		<div class="header_l"></div>
		<!--头部右侧-->
		<#if Context.contextUserId??>
                <#assign _messageCount = Context.getLoaderValue("messageInfData")?default(0)>
                <#assign _noticeCount = Context.getLoaderValue("noticeInfData")?default(0)>
				<div class="home_csdc_rms">
					<#if sysHomePageState?if_exists=='Y'><div id="homePage" class="home" title="返回首页" data-placement="left"><a href="${ContextPath}/homepage.d"></a></div></#if>
					<div class="back" title="后退"  data-placement="left"><a href="javascript:history.go(-1);" ></a></div>
					<div class="refresh" title="刷新" data-placement="left"><a href="javascript:location.reload()"></a></div>
					<div class="user"><span class="u_icon" ></span><span class="u_name"  data-placement="left" data-toggle="dropdown" title="${Context.contextUser.username?if_exists?html}">${Context.contextUser.username?if_exists?html}</span>
						<ul class="dropdown-menu">
	                        <li>
	                            <@c.Link title="消息" action=[c.opendlg("#messageDlg","${ContextPath}/tools/message/messageDlg.d","","900px",true)]><i class="fa fa-comment-o"></i>消息<#if _messageCount gt 0>[<label>${_messageCount?if_exists?html}</label>]</#if></@c.Link>
	                        </li>
	                        <li>
	                            <@c.Link title="公告" action=[c.opendlg("#NoticeDlg","${ContextPath}/sysnotice/noticeDlg.d","","900px",true)]><i class="fa fa-envelope-o"></i>公告<#if _noticeCount gt 0>[<label>${_noticeCount?if_exists?html}</label>]</#if></@c.Link>
	                        </li>
	                        <li>
	                            <@c.Link title="个人信息" action=[c.opendlg("#NoticeDlg","${ContextPath}/grant/user/personalInformation.d","","",true)]>
	                                <i class="fa fa-gear"></i>个人信息
	                            </@c.Link>
	                        </li>
                    	</ul>
					</div>
					<!--<div class="mail"><a href=""></a><span class="badge_red">22</span></div>-->
					<!-- -->
					<div class="set"  title="设置个人信息" data-placement="left">
						<@c.Link action=[c.opendlg("#NoticeDlg","${ContextPath}/grant/user/personalInformation.d","","",true)]>
		                </@c.Link>
	                </div>
	               
					<div class="signout"  title="退出" data-placement="left"><a href="${ContextPath}/logout.d"></a></div>
				</div>
			</#if>
	</div>
</header>