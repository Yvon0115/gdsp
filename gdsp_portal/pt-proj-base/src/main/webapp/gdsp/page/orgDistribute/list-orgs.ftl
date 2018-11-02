<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/user/distribute" id="" onload="" />
<@c.Box style="min-height:200px;">
	<@c.BoxBody style="min-height:150px;">
		<@c.EasyTreeBuilder id="orgTree" url="/user/distributeOrg/listOrg.d" levels="2" events="{onNodeSelected:selectNode}">
		</@c.EasyTreeBuilder>
	</@c.BoxBody>	
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" click="saveAllocate()">保存</@c.Button>
		<@c.Button icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
<@c.Hidden name="userIds" value="${ids}" />
<#-- 
-->