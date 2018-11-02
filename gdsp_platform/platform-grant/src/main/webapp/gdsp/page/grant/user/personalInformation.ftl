<!-- 个人信息 -->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>个人信息</modal-title>
<@c.Script src="script/grant/user_main"/>
<div class="modal-body autoscroll">
  <@c.Form id="personalInfForm" class="validate" action="${ContextPath}/grant/user/execResetPersonalInf.d" method="post" after={"$.closeDialog()":""}>
	   <@c.Hidden name="id" value="${user.id?if_exists}"/>
	   <@c.FormInput name="account" label="账号"  readonly=true value="${user.account?if_exists}"/>
	   <@c.FormInput name="username" label="姓名"  validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60} value="${user.username?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}"/>
       <@c.FormInput name="user_password" type="password" label="密码" helper="${help}" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"remote":"${ContextPath}/security/pwd/checkPassword.d"}/>  
       <@c.FormInput name="re_password" type="password" label="确认密码" value="${user?if_exists.user_password?if_exists}" validation={"required":true,"equalTo":"#user_password"}/>  
       <@c.FormInput name="mobile" label="手机" value="${user.mobile?if_exists}" helper="11位数字" maxlength="11" validation={"mobile":true}/>
       <@c.FormInput name="tel" label="固定电话" value="${user?if_exists.tel?if_exists}" helper="7-20位字符，仅支持数字，可用‘+、-、()’分隔" validation={"phone":true}/>
       <@c.FormInput name="email" label="邮箱" value="${user.email?if_exists}" validation={"email":true}/>
  </@c.Form>
</div>
<div class="modal-footer">
   <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#personalInfForm")]>确定</@c.Button>
   <@c.Button type="canel"  icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>