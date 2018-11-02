<#--预警类型定义页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_formDef.js"/>-->
<@c.Script src="script/wf_formDef" />
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if editType??&& editType=="edit">修改注册单据信息<#else>添加注册单据信息</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="formdefForm" class="validate" action="${ContextPath}/workflow/formdef/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#formDefContent"}>
    	<@c.FormIdVersion id="${formDefs?if_exists.id?if_exists}" version="${formDefs?if_exists.version?default(0)}"/>
    	<@c.FormInput name="pk_categoryid" type="hidden" value="${pk_categoryid?if_exists}"></@c.FormInput>
        <#if editType??&&editType=="edit">
        <@c.FormInput name="formCode" label="单据编码" value="${formDefs?if_exists.formCode?if_exists}" readonly=true/>
        <#else>
        <@c.FormInput name="formCode" label="单据编码" value="${formDefs?if_exists.formCode?if_exists}" validation={"required":true,"remote":"${ContextPath}/workflow/formdef/uniqueCodeCheck.d"} messages={"remote":"单据编码不能重复，请确认！"} />
        </#if>
        <@c.FormInput name="formName" label="单据名称" value="${formDefs?if_exists.formName?if_exists}" validation={"required":true,"remote":"${ContextPath}/workflow/formdef/uniqueNameCheck.d?id=${formDefs?if_exists.id?if_exists}"} messages={"remote":"单据名称不能重复，请确认！"}/>
        <@c.FormInput name="formURL" label="URL" value="${formDefs?if_exists.formURL?if_exists}" validation={"required":true} helper="url从跟路径开始，前面需要加'/'，如下：/workflow/leave/getForm.d url返回一个不带表单头，只有form表单项及数据的视图"/>
        <br clear="both"/>
        <h5 class="box-title">设置单据变量</h5>
        <@c.FormItem >
            <#include "param-list.ftl">
        </@c.FormItem>  
        <@c.FormItem >
	     <@c.ButtonGroup class="pull-right">
	      <@c.Button icon="glyphicon glyphicon-plus" type="primary" click="addParaRow()">增加变量</@c.Button>
	 	 </@c.ButtonGroup>
         </@c.FormItem>  
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#formdefForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
    </@c.BoxFooter>
</@c.Box>