   <#--<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title">添加关联用户</h3>
         <@c.Search class="pull-right"  target="#pageUserContent" conditions="username" placeholder="姓名"/>
    </@c.BoxHeader>
<@c.BoxBody>
    <@c.TableLoader id="pageUserContent" url="${ContextPath}/grant/page/reloadUserList.d?pageId=${pageId?if_exists}">
		<#include "/gdsp/page/grant/pub/userlist-data.ftl">
    </@c.TableLoader>
   </@c.BoxBody>
  <@c.BoxFooter>
			  <@c.Pagination  class="pull-right" target="#pageUserContent" page=users?default("")/>
		  </@c.BoxFooter>
   
    <@c.BoxFooter class="text-center">
        <@c.Button icon="fa fa-save" type="primary" action=[c.rpc("${ContextPath}/grant/page/saveUsers.d?pageId=${pageId}",{"switchtab":"#mainPanel","dataloader":"#userListContent"},{"checker":["id","#pageUserContent"],"confirm":"确认添加关联用户？"}) ]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>-->
