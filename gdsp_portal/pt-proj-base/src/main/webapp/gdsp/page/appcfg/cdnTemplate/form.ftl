<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if widgetCdnTemplateVO??&& widgetCdnTemplateVO.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="widgetForm" class="form-horizontal validate" action="${ContextPath}/widget/cdnTemplate/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#paramTypeContents"}>
        <@c.FormIdVersion id="${widgetCdnTemplateVO?if_exists.id?if_exists}" version="${widgetCdnTemplateVO?if_exists.version?default(0)}"/>
        <@c.FormInput name="name" label="参数类型名称" value="${widgetCdnTemplateVO?if_exists.name?if_exists}"/>
        <@c.FormInput name="code" label="参数类型编码" value="${widgetCdnTemplateVO?if_exists.code?if_exists}"/>
        <@c.FormComboBox name="type" label="类型"  validation={"required":true} value="${param?if_exists.type?if_exists}">
	      	 <#list type as doc>
	       		<@c.Option value="${doc.doc_name?if_exists}">${doc.doc_name?if_exists}</@c.Option>
	        </#list>
        </@c.FormComboBox>
        <@c.FormText name="comments" label="备注">${widgetCdnTemplateVO?if_exists.comments?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"icon="fa fa-save" action=[c.saveform("#widgetForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
