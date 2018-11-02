<#--任务类型定义页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/schedule/alertdef_edit.js"/>-->
<@c.Script src="script/schedule/alertdef_edit" />
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if editType??&& editType=="edit">修改任务类型<#else>添加任务类型</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="jobdefForm" class="validate" action="${ContextPath}/schedule/jobdef/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#jobdefsContent"}>
        <#if editType??&&editType=="edit">
        <@c.FormInput name="name" label="名称" value="${jobdef?if_exists.name?if_exists}" readonly=true/>
        <#else>
        <@c.FormInput name="name" label="名称" value="${jobdef?if_exists.name?if_exists}" helper="1-100位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":100,"remote":"${ContextPath}/schedule/jobdef/synchroCheck.d"} messages={"remote":"名称不能重复，请确认！"}/>
        </#if>
        <@c.FormInput name="className" label="实现类" value="${jobdef?if_exists.className?if_exists}" helper="需继承  com.gdsp.platform.schedule.service.AbstractJobImpl" validation={"required":true,"maxlength":128}/>
        <@c.FormText name="description" validation={"maxlength":250} label="描述">${jobdef?if_exists.description?if_exists}</@c.FormText>
        <hr style="display:block;width:100%;border-color:#e2e2e2;">
        <@c.FormItem >
	     <@c.ButtonGroup  class="pull-right">
	      <@c.Button icon="glyphicon glyphicon-plus" type="primary" click="addParaRow()">添加参数</@c.Button>
	 	 </@c.ButtonGroup>
         </@c.FormItem>
         <br/><br/>
        <@c.FormItem >
            <#include "/gdsp/page/schedule/pub/param-list.ftl">
         </@c.FormItem>  
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#jobdefForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>