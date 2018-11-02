<#import "/gdsp/tags/castle.ftl" as c>

<@c.Script id="sysConfExtJS" src="script/conf/pwdsecurity/pwdsecurity"/> 
<@c.Box>
  <@c.BoxHeader>
  	<h4 class="text-center" style="margin-top : 25px;font-size : 24px;color : #2372a0">密码过期或为初始状态请修改</h4>
  </@c.BoxHeader>
  <hr align=center  width="80%" color=#aeaeae size=1>
  <@c.BoxBody style="height : 350px">
  	<div class="row">
  	<div class="col-md-2"></div>
  	<div class="col-md-5">
	<@c.Form id="passwordForm" class="validate" action="${ContextPath}/security/pwd/editPassword.d" method="post">
	    <input type="hidden" id="userId" name="id" value="${user?if_exists.id?if_exists}"/>
	   <@c.FormInput name="account" style="width : 290px;height:38px" label="账号" value="${user?if_exists.account?if_exists}" readonly=true />
	   <@c.FormInput name="username" style="width : 290px;height:38px" label="姓名" value="${user?if_exists.username?if_exists}" readonly=true/>	   
       <@c.FormInput name="user_password" style="width : 290px;height:38px" type="password" label="密码" value="" validation={"required":true,"remote":"${ContextPath}/security/pwd/checkPassword.d"}/>
       <@c.FormInput name="re_password" style="width : 290px;height:38px" type="password" label="确认密码" value="" validation={"required":true,"equalTo":"#passwordForm #user_password"}/>  
  	</@c.Form>
  	</div>
  	<div class="col-md-3" style="background-color : #f7f7fe;height : 237px">
  		<div style="height : 200px">
  		<div style="height : 150px;margin-top : 20px">
	  		<p id="tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="no_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="length_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="number_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="character_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="english_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  		<p id="case_tip" class="text-left" style="font-size : 16px;color : #767272"></p>
	  	</div>
	  	</div>
  	</div>
  	<div class="col-md-2"></div>
  	</div>
  	<input type="hidden" id="pwd_length" value="${pwdSecurity.pwdLength}"/>  
  	<input type="hidden" id="pwd_number_state" value="${pwdSecurity.pwdNumberState}"/>  
  	<input type="hidden" id="pwd_character_state" value="${pwdSecurity.pwdCharacterState}"/>  
  	<input type="hidden" id="pwd_english_state" value="${pwdSecurity.pwdEnglishState}"/> 
  	<input type="hidden" id="pwd_case_state" value="${pwdSecurity.pwdCaseState}"/>  
  </@c.BoxBody>
  <@c.BoxFooter class="text-center" style="height : 105px">
  	<div style="margin-top : 25px">
	<@c.Button type="primary" icon="fa fa-save" click="savePassword()">保存</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" style="margin-left:40px" click="reset()">重置</@c.Button>
  	</div>
  </@c.BoxFooter>
</@c.Box>
