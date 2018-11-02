<#--预警类型部署页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/schedule/jobdef_deploy.js"/>-->
<@c.Script src="script/schedule/jobdef_deploy" onload="onloadCss();" />
<@c.Script id="" src="" onload="init()"/>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title">部署预警类型</h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    
    <@c.Form  id="deployForm" class="validate" action="${ContextPath}/schedule/alertdef/deployJob.d" method="post" before={"return checkDeployInf()":""} after={"switchtab":"#mainPanel","dataloader":"#alertdefsContent"} mode="simple">
    
    <@c.Tabs ulclass="header-bg">
	    <@c.Tab  id="commonPanel" active=true title="常规设置">
	    <#include "deploy-common.ftl">
	    </@c.Tab>
	    
	    <@c.Tab  id="timePanel" title="时间设置" >
	    <#include "deploy-method.ftl">
	    </@c.Tab>  
	    
	    <@c.Tab  id="paraPanel" title="其他设置" >
	    <#include "deploy-para.ftl">
	    </@c.Tab>  
	    
	</@c.Tabs>

  
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#deployForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>