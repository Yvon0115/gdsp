<#--系统配置主页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Form id="passwordConfView"  class="validate"  action="">
	<@c.FormInput  name="time"  type="text" label="密码时效(天):" readonly=true value="${policy?if_exists.timeLimit?if_exists}"
			helper="时效设置为0表示不开启"	style="width : 480px"  events="{change :function(){numberCheck(this)}}"/>
	<@c.FormInput  name="length" type="text" label="密码位数:" readonly=true style="width : 480px" 
		helper="位数设置为0表示不开启"	value="${policy?if_exists.pwdLength?if_exists}"
		events="{keyup :function(){numberCheck(this)}}" />
	<@c.FormItem id="" label="密码复杂度:">
    	<div style="margin-top:5px">
			<input type="checkbox" id="numberState" disabled="disabled" value="Y" name="pwdNumberState" <#if policy?if_exists.pwdNumberState?if_exists=='Y'>checked</#if>/>&nbsp;数字&nbsp;&nbsp;&nbsp;
			<input type="checkbox" id="characterState" disabled="disabled" value="Y" name="pwdCharacterState" <#if policy?if_exists.pwdCharacterState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;@,#,$特殊字符&nbsp;&nbsp;
			<input type="checkbox" id="englishState" disabled="disabled" value="Y" name="pwdEnglishState" <#if policy?if_exists.pwdEnglishState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;英文字母&nbsp;&nbsp;
			<input type="checkbox" id="caseState" disabled="disabled" value="Y" name="pwdCaseState" <#if policy?if_exists.pwdCaseState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;大写英文字母&nbsp;&nbsp;
		</div>
	</@c.FormItem>
</@c.Form>
