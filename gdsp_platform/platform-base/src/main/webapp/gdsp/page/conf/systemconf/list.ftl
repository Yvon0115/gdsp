<#--系统配置主页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="sysConfExtJS" src="script/func/systemExtConf" onload="loadClickEvents()"/> 
<@c.Tabs class="clearfix" >
	<@c.Tab  id="systemConfMainPanel" active=true>
		<@c.Tabs ulclass="header-bg">
		<@c.Tab id="sys_conf" active=true title="系统设置">
		<@c.Box>
			<@c.BoxHeader class="border">
				<@c.Button type="primary" icon="glyphicon glyphicon-pencil" action=[c.opentab("#systemConfDetailPanel","${ContextPath}/func/systemconf/edit.d?id=${systemConf?if_exists.id?if_exists}")]>编辑</@c.Button>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.Form id="systemConfForm" class="validate" action="">
					<@c.FormIdVersion id="${systemConf?if_exists.id?if_exists}" version="${systemConf?if_exists.version?default(0)}" />
        			<@c.FormItem id="sysHomeRadio" label="系统首页:">
        			<div style="margin-top:5px;">
						<input type="radio"  value="Y"  id="enable"  name="isEnable" disabled="disabled" <#if systemConf?if_exists.sysHomeState?if_exists=='Y'>checked</#if> />&nbsp;启用&nbsp;&nbsp;&nbsp;
						<input type="radio"  value="N"  id="disable"  name="isEnable" disabled="disabled" <#if systemConf?if_exists.sysHomeState?if_exists!='Y'>checked</#if> />&nbsp;不启用&nbsp;
					</div>
					</@c.FormItem>
					<@c.FormInput  name="sysHomeUrl" label="URL地址:" value="${systemConf?if_exists.sysHomeUrl?if_exists}" readonly=true helper="URL地址"/>
					<@c.FormItem id="reportIntCheckbox" label="报表集成:">
        			<div style="margin-top:5px;">
        				<#list reportTypeList as reportType>
        					<input type="checkbox"  value="${reportType?if_exists.doc_code?if_exists}"  id="cognos"  name="reportInt" disabled="disabled"<#if reportType?if_exists.checked?if_exists=='checked'>checked</#if> />&nbsp;${reportType?if_exists.doc_name?if_exists}&nbsp;&nbsp;&nbsp;
        				</#list>
					</div>
					</@c.FormItem>
				</@c.Form>
			</@c.BoxBody>
		</@c.Box>
		</@c.Tab>
		<@c.Tab id="password_conf" active=false title="密码安全策略设置">
			<@c.Box>
				<@c.BoxHeader class="border">
					<@c.Button type="primary" icon="glyphicon glyphicon-pencil" action=[c.opentab("#pwdSecurityPolicyPanel","${ContextPath}/func/systemconf/editPasswordSecurityPolicy.d")]>编辑</@c.Button>
				</@c.BoxHeader>
				<@c.BoxBody  id="policy">
					<#include "policyForm.ftl"/>
				</@c.BoxBody>
			</@c.Box>
		</@c.Tab>
		<#-- 权限时效页签 -->
		<#-- <@c.Tab id="grant_aging_conf" active=false title="权限时效设置">-->
		 <@c.Tab id="grant_aging_conf" active=false title="权限时效设置" reload=true >
			<@c.Box>
				<@c.BoxHeader class="border">
					<@c.Button type="primary" icon="glyphicon glyphicon-pencil" action=[c.opentab("#systemConfDetailPanel","${ContextPath}/func/systemconf/editGrantAgingConfigs.d?")]>编辑</@c.Button>
				</@c.BoxHeader>
				<@c.BoxBody  id="grantAging">
					<#include "grant-aging-form.ftl"/>
				</@c.BoxBody>
				<#--<@c.BoxFooter class="text-center">-->
					<#--<@c.Button  type="primary" icon="fa fa-save" click="saveGrantAgingConfigs()">保存</@c.Button>
				</@c.BoxFooter>-->
			</@c.Box>
		</@c.Tab>
		<#-- 邮件服务页签 -->
		<#-- <@c.Tab id="grant_aging_conf" active=false title="邮件服务设置">-->
			<@c.Tab id="mail_service_conf" url="${ContextPath}/func/systemconf/loadMailServiceConfs.d" title="邮件服务设置" reload=true >
					<#include "mailService-list.ftl"/>
			</@c.Tab>
		</@c.Tabs>	
	  </@c.Tab>
	  <@c.Tab  id="systemConfDetailPanel"></@c.Tab>
	  <@c.Tab  id="pwdSecurityPolicyPanel"></@c.Tab>
</@c.Tabs>
	