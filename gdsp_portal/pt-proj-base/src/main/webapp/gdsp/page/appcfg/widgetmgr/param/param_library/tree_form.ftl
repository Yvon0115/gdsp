<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader>
        <h3 class="box-title"><#if param??&& param.id??>修改分类<#else>添加分类</#if></h3>
	</@c.BoxHeader>
	<@c.BoxBody class="no-padding">
	    <@c.Form id="ParamLibraryForm" class="validate" action="${ContextPath}/portal/params_library/saveParamLibrarys.d" method="post" after={"switchtab":"#mainPanel","pageload":{}}>
	        <@c.FormIdVersion id="${paramMainVO?if_exists.id?if_exists}" version="${paramMainVO?if_exists.version?default(0)}"/>
           <@c.FormInput name="name" label="目录名称" value="${paramMainVO?if_exists.name?if_exists}"   validation={"required":true,"remote":"${ContextPath}/portal/params_library/synchroCheckDir.d?id=${param?if_exists.id?if_exists}"} messages={"remote":"目录名称不能重复，请确认！"}/>
	        <@c.FormText id="comments" name="comments" label="备注" validation={"minlength":1,"maxlength":100} rows=2>${paramMainVO?if_exists.comments?if_exists}</@c.FormText>
	        <@c.FormRef id="pid" name="pid" label="上级文件夹" value="${parentparamMainVO?if_exists.id?if_exists}" showValue="${parentparamMainVO?if_exists.name?if_exists}" url="${ContextPath}/portal/params_library/queryParentDir.d" />
	    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"   action=[c.saveform("#ParamLibraryForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"    action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>


