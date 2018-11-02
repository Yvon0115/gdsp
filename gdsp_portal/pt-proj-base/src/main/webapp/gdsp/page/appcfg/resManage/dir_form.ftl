<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if commonDirVO??&& commonDirVO.id??>修改目录<#else>添加目录</#if></h3>
    </@c.BoxHeader>
	<@c.BoxBody >
	    <@c.Form id="resDirForm" class="validate" action="${ContextPath}/appcfg/resourceManage/saveCommonDirVO.d" method="post" after={"switchtab":"#mainPanel","pageload":{}}>
	        <@c.FormIdVersion id="${commonDirVO?if_exists.id?if_exists}" version="${commonDirVO?if_exists.version?default(0)}"/>
	       <@c.FormInput name="dir_name" label="资源名称" value="${commonDirVO?if_exists.dir_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":85,"remote":"${ContextPath}/appcfg/resourceManage/synchroCheckDirName.d?id=${commonDirVO?if_exists.id?if_exists}"} messages={"remote":"资源名称不能重复，请确认！"} />
	        <@c.FormInput name="sortnum" label="排序号码" value="${commonDirVO?if_exists.sortnum?if_exists}" validation={"required":true,"minlength":1,"maxlength":10} events="{keyup : function(){resManagerPageJS.validateSortnum(this)}}" messages={"keyup":"排序号码只能为数字"}/>
	        <#-- <@c.FormRef id="parent_id" name="parent_id" label="上级节点" value="${parentCommonDirVO?if_exists.parent_id?if_exists}" showValue="${parentCommonDirVO?if_exists.dir_name?if_exists}" url="${ContextPath}/appcfg/resourceManage/queryParentResDir.d" />-->
	    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"   action=[c.saveform("#resDirForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"   action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>


