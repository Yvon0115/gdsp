<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if widget??&& widget.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="widgetForm" class="validate" action="${ContextPath}/widgetmgr/page/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#widgetName"}>
        <@c.FormIdVersion id="${widget?if_exists.id?if_exists}" version="${widget?if_exists.version?default(0)}"/>
         <@c.FormInput name="parent_name" label="上级目录" value="${widget?if_exists.parent_name?if_exists}" validation={"required":true}/>
        <@c.FormInput name="res_name" label="资源名称" value="${widget?if_exists.res_name?if_exists}" validation={"required":true}/>
        <@c.FormText id="res_alias" name="res_alias" label="资源别名" rows=2>${widget?if_exists.res_alias?if_exists}
        </@c.FormText>
        <@c.FormText id="res_url" name="res_url" label="URL"  rows=3 >${widget?if_exists.res_url?if_exists}
        </@c.FormText>
        <@c.FormText id="res_desc" name="res_desc" label="资源描述"  rows=3 >${widget?if_exists.res_desc?if_exists}
        </@c.FormText>
       <@c.FormComboBox name="res_type" label="资源类型"  validation={"required":true} value="${widget?if_exists.res_type?if_exists}">
            <@c.Option value="cognos">cognos</@c.Option>
            <@c.Option value="web">web</@c.Option>
            <@c.Option value="system">system</@c.Option>
        </@c.FormComboBox>
        <@c.Hidden name="parent_id" value="${widget?if_exists.parent_id?if_exists}" />
  	</@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save" action=[c.saveform("#widgetForm")]>保存</@c.Button>
        <@c.Button type="canel"   icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
