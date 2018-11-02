<#-- 角色管理 -->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/role_list.js"/>-->
<@c.Script src="script/grant/role/role_list" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
	<div class="col-md-3 no-padding">
		<@c.Box >
		   	 <@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
			    <#include  "org-tree.ftl">
			  </@c.BoxBody>
		</@c.Box>
	</div>
	<div class="col-md-9 no-padding">
        <@c.Box class="box-right">
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addRole()">添加角色</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/grant/role/delete.d",{"dataloader":"#rolesContent"},{"checker":["id","#rolesContent"],"confirm":"删除角色会一并删除角色与用户、机构、菜单和页面的关联关系，确定要删除该角色？"})]>删除</@c.Button>
          		 <@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
          		<@c.Condition id="queryCondition"  target="#rolesContent" class="pull-right" button=false style="width:50%;" cols=2 ctrlsize=6>
	                    <@c.QueryComponent name="rolename" placeholder="角色名" op="like" type="text" />
		                <@c.QueryComponent name="memo" op="like" placeholder="描述" type="text" />
	             </@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="rolesContent" url="${ContextPath}/grant/role/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                   <!-- 分页 -->
                   <@c.Pagination class="pull-right"  target="#rolesContent" page=rolePage?default(null)/>
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
