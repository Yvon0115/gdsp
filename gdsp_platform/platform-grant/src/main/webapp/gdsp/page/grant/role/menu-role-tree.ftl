<#--角色授权关联菜单树-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxBody>
		<#if menuNodes?? && menuNodes?size gt 0 >
	        <div class="autoscroll">
				<@c.SimpleTree id="menuRoleTree" checkbox=true>
					<#if isshowsafelevel?? && isshowsafelevel=='Y'>
					<@c.TreeMapBuilder map=menuNodes nameField="funname_safelevel" nexp="<#if (node.isChecked)?? && (node.isChecked)==\"Y\">ckbox=\"true\"</#if>">
					</@c.TreeMapBuilder>
					<#else>
					<@c.TreeMapBuilder map=menuNodes nameField="funname" nexp="<#if (node.isChecked)?? && (node.isChecked)==\"Y\">ckbox=\"true\"</#if>">
					</@c.TreeMapBuilder>
					</#if>
				</@c.SimpleTree>	
			</div>
			<input type="hidden" id="checkedMenuIds" value="${checkedMenuIds}"/>
			<@c.Button type="primary" icon="fa fa-save" click="saveMenuRole()">保存</@c.Button>
			<@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
		<#else>
			当前登录用户的角色没有关联菜单，请设置后授权。
		</#if>
    </@c.BoxBody>
</@c.Box>