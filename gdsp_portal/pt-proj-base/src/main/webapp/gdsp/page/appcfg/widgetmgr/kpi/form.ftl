<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box id="mainBox">
    <@c.BoxHeader>
        <h3 class="box-title"><#if kpi??&& kpi.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody >
	    <@c.Form id="kpiForm" class="validate" action="${ContextPath}/widgetmgr/kpi/save.d" method="post">
	        <@c.FormIdVersion id="${kpi?if_exists.id?if_exists}" version="${kpi?if_exists.version?default(0)}"/>
	        <@c.FormInput name="name" label="名称" value="${kpi?if_exists.name?if_exists}" validation={"required":true,"minlength":1,"maxlength":150}/>
	        <@c.FormInput name="alias" label="别名" value="${kpi?if_exists.alias?if_exists}"/>
	        <@c.FormText id="comments" name="comments" label="描述" rows=2>${kpi?if_exists.comments?if_exists}</@c.FormText>
	        <@c.Hidden name="widget_id" value="${widget_id?if_exists}" />
	  	</@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save" action=[c.saveform("#kpiForm")]>保存</@c.Button>
    	<@c.Button  icon="glyphicon glyphicon-arrow-left"  action=[c.opentab("#mainPanel")]   >返回</@c.Button>
    </@c.BoxFooter>
    <#include "list.ftl">
</@c.Box>
