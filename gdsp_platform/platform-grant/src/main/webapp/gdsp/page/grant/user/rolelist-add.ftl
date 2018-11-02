<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/user_main.js"/>-->
<@c.Script src="script/grant/user_main" />
<#-- 未关联角色列表 -->
<@c.Hidden name="userId" id="userId" value="${userId}" />
<@c.Box>
	<@c.BoxHeader class="border header-bg">
		<h3 class="box-title">关联角色添加界面</h3>
		<@c.Search class="pull-right" target="#roledataAddContent" conditions="rolename" placeholder="角色名称"/>
	</@c.BoxHeader>
	<@c.BoxBody>
		<@c.TableLoader id="roledataAddContent" url="${ContextPath}/grant/user/loadRoleListData.d?userId=${userId}">
				<#include "rolelist-add-data.ftl">
		</@c.TableLoader>
		<@c.Hidden id="agingRoleId" name="agingRoleId" value="${agingRoleId?if_exists}" />
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Pagination class="pull-right" target="#roledataAddContent" page=roleVO?if_exists />
		<@c.Button type="primary" icon="fa fa-save" click="saveRoles()" action=[c.opentab("#roleMainPanel")]>保存</@c.Button>
		<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#roleMainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>