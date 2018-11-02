<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if widget??&& widget.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="widgetForm" class="validate" action="${ContextPath}/widgetmgr/pub/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#widgetName"}>
        <@c.FormIdVersion id="${widget?if_exists.id?if_exists}" version="${widget?if_exists.version?default(0)}"/>
        <@c.FormInput name="widget_name" label="名称" value="${widget?if_exists.widget_name?if_exists}" validation={"required":true}/>
        <@c.FormInput name="widget_desc" label="描述" value="${widget?if_exists.widget_desc?if_exists}" />
        <@c.FormInput name="widget_link" label="链接" value="${widget?if_exists.widget_link?if_exists}" validation={"required":true}/>
        
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"icon="fa fa-save"  action=[c.saveform("#widgetForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
