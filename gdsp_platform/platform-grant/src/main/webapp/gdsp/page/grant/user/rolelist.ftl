<#import "/gdsp/tags/castle.ftl" as c>
<#-- 用户已关联角色列表 -->
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/user_main.js"/>-->
<@c.Script src="script/grant/grant_aging" />
<@c.Script src="script/grant/user_main" />
<@c.Hidden name="userId" id="userId" value="${userId}" />
<@c.Tabs ulclass="header-bg">
	<@c.Tab id="roleMainPanel" active=true>
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.opentab("#roledetailPanel","${ContextPath}/grant/user/openRoleListTab.d?userId=${userId}")]>添加关联角色</@c.Button>
				<@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/grant/user/deleteRolesOnUser.d?userId=${userId}",{"dataloader":"#roleListContent"},{"checker":["id","#roleListContent"],"confirm":"确认删除关联角色？"})]>删除</@c.Button>
				<#if agingStatus??&&agingStatus=='Y'>
					<@c.Button icon="glyphicon glyphicon-pencil" click="editAgingEndDate()">设置权限时效</@c.Button>
				</#if>	
				<@c.Button icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
				<@c.Search class="pull-right" target="#roleListContent" conditions="rolename" placeholder="名称"/>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.TableLoader id="roleListContent" url="${ContextPath}/grant/user/loadRolesData.d?userId=${userId}">
					<#include "rolelist-data.ftl">
				</@c.TableLoader>
				<@c.Hidden id="agingroleId" name="agingroleId" value="${agingroleId?if_exists}" />
			</@c.BoxBody>
			<@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#roleListContent" page=roleVO?if_exists />
			</@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="roledetailPanel">
	</@c.Tab>
</@c.Tabs>	