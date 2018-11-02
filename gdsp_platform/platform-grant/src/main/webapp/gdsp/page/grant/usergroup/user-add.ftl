<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
             <@c.Search class="pull-right"  target="#userContent" conditions="account,username" placeholder="账号/姓名"/>
    </@c.BoxHeader>
	<@c.BoxBody>
		  <@c.TableLoader id="userContent" url="${ContextPath}/grant/usergroup/addGroupUserlist.d?groupId=${groupId}">
		  	<#include "/gdsp/page/grant/pub/userlist-data.ftl">
		  </@c.TableLoader>
		  <@c.Pagination class="pull-right" target="#userContent" page=users/><!-- 分页 -->
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.rpc("${ContextPath}/grant/usergroup/saveUserOnGroup.d?groupId=${groupId}",{"switchtab":"#userListPanel","dataloader":"#userListContent"},{"checker":["id","#userContent"],"confirm":"确认添加关联用户？"}) ]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#userListPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
