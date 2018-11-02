<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if usergroup??&& usergroup.id??>修改用户组<#else>添加用户组</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="usergroupForm" class="validate" action="${ContextPath}/grant/usergroup/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#usergroupsContent"}>
        <@c.FormIdVersion id="${usergroup?if_exists.id?if_exists}" version="${usergroup?if_exists.version?default(0)}"/>
        <@c.FormInput name="groupname" label="用户组名" value="${usergroup?if_exists.groupname?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"  validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/grant/usergroup/uniqueCheck.d?id=${usergroup?if_exists.id?if_exists}"}  messages={"remote":"用户组名不能重复，请确认！"} />
        <@c.FormText name="memo" label="描述" helper="1-50之间的字符串" validation={"minlength":1,"maxlength":50}>${usergroup?if_exists.memo?if_exists}</@c.FormText>
        <@c.Hidden name="pk_org" value="${usergroup?if_exists.pk_org?if_exists}"/>
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#usergroupForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>