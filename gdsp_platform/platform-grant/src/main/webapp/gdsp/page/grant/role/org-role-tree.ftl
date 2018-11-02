<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxBody>
        <div class="autoscroll">
			<@c.SimpleTree id="orgRoleTree"  checkbox=true checkOption='1'>
				<@c.TreeListBuilder nodes=orgNodes nameField="orgname" nexp="<#if (node.isChecked)?? && (node.isChecked)==\"Y\">ckbox=\"true\"</#if>">
				</@c.TreeListBuilder>
			</@c.SimpleTree>	
		</div>
		<input type="hidden" id="checkedOrgIds" value="${checkedOrgIds}"/>
		<@c.Button type="primary" icon="fa fa-save" click="saveOrgRole()">保存</@c.Button>
		<@c.Button icon="glyphicon glyphicon-arrow-left"  action=[c.opentab("#mainPanel")]>返回</@c.Button>
    </@c.BoxBody>
</@c.Box>