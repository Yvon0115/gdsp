<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if action??&& action.id??>修改动作<#else>添加动作</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="actionForm" class="validate" action="${ContextPath}/widgetmgr/action/save.d" method="post" after={"switchtab":"#mainActionPanel","dataloader":"#actionName"}>
        <@c.FormIdVersion id="${action?if_exists.id?if_exists}" version="${action?if_exists.version?default(0)}"/>
        <@c.FormInput name="code" label="动作编码" value="${action?if_exists.code?if_exists}" validation={"required":true,"minlength":1,"maxlength":50,"remote":"${ContextPath}/widgetmgr/action/synchroCheck.d?id=${action?if_exists.id?if_exists}"} messages={"remote":"编码不能重复，请确认！"}/>
        <@c.FormInput name="name" label="动作名称" value="${action?if_exists.name?if_exists}" validation={"required":true,"minlength":1,"maxlength":128}/>       
        <@c.FormComboBox name="widgettype" label="动作类型"  value="${action?if_exists.widgettype?if_exists}">
	      	 <#list types as doc>
	       		<@c.Option value="${doc.doc_name?if_exists}">${doc.doc_name?if_exists}</@c.Option>
	        </#list>
        </@c.FormComboBox>
	 <#if type ??>
		 <#if type=='1'>
		  	<@c.FormInput name="widgetidname" label="资源名称" readonly=true value="${action?if_exists.refshowvalue?if_exists}"/>
		 </#if>	    
	 <#else>
	 	 
     </#if>
     <@c.Hidden name="widgetid" value="${widgetid?if_exists}" />
     
     
        <@c.FormText id="template" name="template" label="动作模板" rows=3>${action?if_exists.template?if_exists}
        </@c.FormText>
        <@c.FormText id="memo" name="memo" validation={"minlength":1,"maxlength":100} label="动作说明" rows=3>${action?if_exists.memo?if_exists}
        </@c.FormText>  
       
  	</@c.Form>
  	 
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"  action=[c.saveform("#actionForm")]>保存</@c.Button>
        <@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.opentab("#mainActionPanel")]>取消</@c.Button>
    </@c.BoxFooter>
   
</@c.Box>
