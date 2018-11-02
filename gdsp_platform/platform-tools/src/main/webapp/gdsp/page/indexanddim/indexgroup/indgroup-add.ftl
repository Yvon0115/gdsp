<#import "/gdsp/tags/castle.ftl" as c>
<#-- <style>
	.box-title{
	color:#010101;
	}
	.boxTitle{
	font-size:14px;
	}
</style>-->
<@c.Box >
    <@c.BoxHeader class="border header-bg" >
        <h3 class="box-title"><#if editType??&&editType=="add">添加指标组<#else>修改指标组</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
        <@c.Form id="indGroupAddForm" class="validate" action="${ContextPath}/indexanddim/indexgroup/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#treeContent"}>
            <#if editType??&&editType=="add">
            <@c.FormInput name="groupCode"  label="指标组编码" value="${indTreeVO?if_exists.groupCode?if_exists}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/indexanddim/indexgroup/uniqueCheck.d?id=${indTreeVO?if_exists.id?if_exists}"} helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events ="{blur :function(){$Utils.validInputSpeChar(this)}}"  messages={"remote":"编码不能重复，请确认！"}/>
            <#else>
            <@c.FormInput name="groupCode" label="指标组编码" value="${indTreeVO?if_exists.groupCode?if_exists}"  validation={"alphanumerSpec":true,"required":true,"minlength":1, "maxlength":60,"remote":"${ContextPath}/indexanddim/indexgroup/uniqueCheck.d?id=${indTreeVO?if_exists.id?if_exists}"} helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events ="{blur :function(){$Utils.validInputSpeChar(this)}}" messages={"remote":"编码不能重复，请确认！"}/>
            </#if>     
            <@c.FormInput name="groupName" label="指标组名称" value="${indTreeVO?if_exists.groupName?if_exists}"  validation={"alphanumerSpec":true,"required":true,"minlength":1, "maxlength":60} helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events ="{blur :function(){$Utils.validInputSpeChar(this)}}"/>
            
            <#if editType??&&editType=="add">
            <@c.FormRef id="innercode" name="innercode" label="上级指标组" value="${indTreeVO?if_exists.innercode?if_exists}" showValue="${indTreeVO?if_exists.groupName?if_exists}" url="${ContextPath}/indexanddim/indexgroup/queryParentGroup.d?id=${indTreeVO?if_exists.id?if_exists}" />
            <@c.FormText name="detail" validation={"maxlength":120} label="描述">${indTreeVO?if_exists.detail?if_exists}</@c.FormText>
            <#else>
            <@c.FormText name="detail" validation={"maxlength":120} label="描述">${indTreeVO?if_exists.detail?if_exists}</@c.FormText>
            <@c.Hidden name="innercode"  value="${indTreeVO?if_exists.innercode?if_exists}" />
            <@c.Hidden name="id"  value="${indTreeVO?if_exists.id?if_exists}" />
            <@c.Hidden name="version"  value="${indTreeVO?if_exists.version?if_exists}" />
            </#if>
        </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<@c.Button  icon="fa fa-save" type="primary" action=[c.saveform("#indGroupAddForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
    
</@c.Box>