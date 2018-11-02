<#import "/gdsp/tags/castle.ftl" as c>
<modal-title><#if kpiDetailVO??&& kpiDetailVO.id??>修改<#else>添加</#if></modal-title>	
<@c.Box>
    <@c.BoxBody>
    <@c.Form id="kpiFieldForm" class="validate" action="${ContextPath}/widgetmgr/kpi/saveKpiField.d" method="post" after={"switchtab":"#mainBox","dataloader":"#kpiName"}>
        <@c.FormIdVersion id="${kpiDetailVO?if_exists.id?if_exists}" version="${kpiDetailVO?if_exists.version?default(0)}"/>
        <@c.FormInput name="name" label="名称" value="${kpiDetailVO?if_exists.name?if_exists}" validation={"required":true,"minlength":1,"maxlength":150}/>
        <@c.FormInput name="alias" label="别名" value="${kpiDetailVO?if_exists.alias?if_exists}" />
         <@c.FormText id="comments" name="comments" label="描述" rows=4>${kpiDetailVO?if_exists.comments?if_exists}</@c.FormText>
        <@c.Hidden name="sortnum" value="${kpiDetailVO?if_exists.sortnum?if_exists}" />
    	<@c.Hidden name="kpi_id" value="${kpi_id?if_exists}" />
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#kpiFieldForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>关闭</@c.Button>
    </@c.BoxFooter>
</@c.Box>
