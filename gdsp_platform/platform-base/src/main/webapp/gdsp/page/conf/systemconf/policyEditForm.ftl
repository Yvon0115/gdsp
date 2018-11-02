<#--系统配置主页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/func/policyEdit" />
<@c.Box>
	<@c.BoxHeader class="border header-bg" >
        <h3 class="box-title">编辑密码安全策略设置</h3>
    </@c.BoxHeader>
	<@c.BoxBody>
		<@c.Form id="passwordConfForm"  class="validate" method="post" action="${ContextPath}/func/systemconf/savePasswordSecurityPolicy.d" after={"switchtab":"#systemConfMainPanel","pageload":{}}>
			<@c.FormInput  name="timeLimit"  type="text" label="密码时效(天):" value="${policy?if_exists.timeLimit?if_exists}"
					helper="时效设置为0表示不开启"	style="width : 480px"  events="{change :function(){numberCheck(this)}}"/>
			<@c.FormInput  name="pwdLength" type="text" label="密码位数:" style="width : 480px" 
				helper="位数设置为0表示不开启"	value="${policy?if_exists.pwdLength?if_exists}"
				events="{keyup :function(){numberCheck(this)}}" />
			<@c.FormItem id="" label="密码复杂度:">
		    	<div style="margin-top:5px">
					<input type="checkbox" id="pwdNumberState" value="Y" name="pwdNumberState" <#if policy?if_exists.pwdNumberState?if_exists=='Y'>checked</#if>/>&nbsp;数字&nbsp;&nbsp;&nbsp;
					<input type="checkbox" id="pwdCharacterState" value="Y" name="pwdCharacterState" <#if policy?if_exists.pwdCharacterState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;@,#,$特殊字符&nbsp;&nbsp;
					<input type="checkbox" id="pwdEnglishState" value="Y" name="pwdEnglishState" <#if policy?if_exists.pwdEnglishState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;英文字母&nbsp;&nbsp;
					<input type="checkbox" id="pwdCaseState" value="Y" name="pwdCaseState" <#if policy?if_exists.pwdCaseState?if_exists=='Y'>checked</#if>/>&nbsp;&nbsp;大写英文字母&nbsp;&nbsp;
				</div>
			</@c.FormItem>
		</@c.Form>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button   type="primary" icon="fa fa-save" action=[c.saveform("#passwordConfForm")]>保存</@c.Button>
		<@c.Button type="cancel" icon="glyphicon glyphicon-off" action=[c.opentab("#systemConfMainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
