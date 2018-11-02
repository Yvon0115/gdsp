<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>密码重置</modal-title>
<div class="modal-body autoscroll">
	<@c.Form id="passwordForm" class="validate" action="${ContextPath}/grant/user/execResetPassword.d" method="post" after={"$.closeDialog()":""}>
	   <@c.FormIdVersion id="${user?if_exists.id?if_exists}" version="${user?if_exists.version?default(0)}"/>
	   <@c.FormInput name="account" label="账号" value="${user?if_exists.account?if_exists}" readonly=true />
	   <@c.FormInput name="username" label="姓名" value="${user?if_exists.username?if_exists}" readonly=true/>	   
       <@c.FormInput name="user_password" type="password" label="密码" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"remote":"${ContextPath}/grant/user/pwdBlankCheck.d"}/>  
       <@c.FormInput name="re_password" type="password" label="确认密码" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"equalTo":"#passwordForm #user_password"}/>  
  	</@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#passwordForm")]>保存</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.dismiss()]>取消</@c.Button>
</div>
