<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title" class="border header-bg">添加关联角色</h3>
         <@c.Search class="pull-right"  target="#roleContent" conditions="rolename" placeholder="角色名" />
    </@c.BoxHeader>
      <@c.BoxBody>
    <@c.TableLoader id="roleContent" url="${ContextPath}/grant/page/reloadRoleList.d?pageId=${pageId?if_exists}">
			<#include "/gdsp/page/grant/pub/rolelist-data.ftl">
			 </@c.TableLoader>
	    </@c.BoxBody>
	     <@c.BoxFooter>
			  <@c.Pagination  class="pull-right" target="#roleContent" page=roles?default("")/>
		  </@c.BoxFooter>
   
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.rpc("${ContextPath}/grant/page/saveRoles.d?pageId=${pageId}",{"switchtab":"#mainPanel","dataloader":"#roleListContent"},{"checker":["id","#roleContent"],"confirm":"确认添加关联角色？"}) ]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
