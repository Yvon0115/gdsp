<#import "/gdsp/tags/castle.ftl" as c>
<#-- 报表参数 - 添加分类  表单页 -->
<@c.Box>
	<@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if paramLib?? && paramLib.id?? && paramLib.id!="" >修改分类<#else>添加分类</#if></h3>
	</@c.BoxHeader>
	<@c.BoxBody class="no-padding" style="margin-top:8px;">
	    <@c.Form id="ParamLibraryForm" class="validate" action="${ContextPath}/param/paramLibrary/save.d" method="post" 
	    		after={"switchtab":"#mainPanel","resetDirId()":"","pageload":{}}>
	        <@c.FormIdVersion id="${paramLib?if_exists.id?if_exists}" version="${paramLib?if_exists.version?default(0)}"/>
            <@c.FormInput name="name" label="参数分类名称" value="${paramLib?if_exists.name?if_exists}"   events="{blur :function(){$Utils.validInputChar(this)}}"
            		validation={"alphanumer":true,"required":true,"minlength":1,"maxlength":64,"remote":"${ContextPath}/param/paramLibrary/checkParamLibraryName.d?id=${paramLib?if_exists.id?if_exists}"} 
            		messages={"remote":"参数类型名称不能重复，请确认！"} helper="1-64位字符,仅支持汉字、字母、数字组合" />
	        <@c.FormText id="comments" name="comments" label="参数分类说明" validation={"alphanumer":true,"minlength":1,"maxlength":100} events="{blur :function(){$Utils.validInputChar(this)}}" helper="1-100位字符,仅支持汉字、字母、数字组合" rows=2>${paramLib?if_exists.comments?if_exists}</@c.FormText>
	        <@c.FormRef id="pid" name="pid" label="上级节点" url="${ContextPath}/param/paramLibrary/queryParentDir.d?id=${paramLib?if_exists.id?if_exists}"  
	        		value="${parentParamLibrary?if_exists.id?if_exists}" showValue="${parentParamLibrary?if_exists.name?if_exists}" />
	    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"   action=[c.saveform("#ParamLibraryForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"    action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>


