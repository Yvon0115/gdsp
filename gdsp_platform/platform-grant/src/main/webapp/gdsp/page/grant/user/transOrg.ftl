<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/user_main.js"/>-->
<@c.Script src="script/grant/user_main" />
<modal-title> &nbsp;&nbsp; &nbsp;机构变更
	<hr style="margin-top:10px; border-top:1px solid #e5e5e5;">
	<div id="userInf" style="line-height:9px;">	   		
					<span> &nbsp;&nbsp;&nbsp;&nbsp;姓名：</span>
					<span>${user?if_exists.username?if_exists}</span>
					<span> &nbsp;&nbsp; &nbsp;&nbsp;原机构：</span>
					<span>${user?if_exists.orgname?if_exists}</span></br>
	</div>
</modal-title>	
			
<div class="modal-body autoscroll" style="height:220px">
	<@c.Form id="transForm" class="validate" action="${ContextPath}/grant/user/execTransOrg.d"  method="post" before={"confirm":"<font color='red'>该用户一旦变更机构将失去当前所有权限，需要在新机构中重新分配！！</font>"} after={"$.closeDialog()":""}>
		   	<@c.FormIdVersion id="${user?if_exists.id?if_exists}" version="${user?if_exists.version?default(0)}"/>
			<@c.Hidden name="pk_org" value="${user?if_exists.pk_org?if_exists}"/>
			<@c.Hidden name="transOrg" value=transOrg/>
			<#--<@c.SimpleTree id="orgTree"  events="{clickNode: transNode}">
				<@c.TreeListBuilder nodes=nodes nameField="orgname">
				</@c.TreeListBuilder>
			</@c.SimpleTree>-->
			<@c.EasyTreeBuilder id="transOrgTree" url="/grant/user/transOrgTree.d" levels="2" checkOption="1" events="{onNodeSelected:transNode}">
			</@c.EasyTreeBuilder>
	</@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save"  action=[c.saveform("#transForm")]>保存</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>