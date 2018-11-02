<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
   <@c.BoxHeader class="border header-bg">
      <h3 class="box-title"><#if widgetMetaVO??&& widgetMetaVO.id??>修改资源<#else>添加资源</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody class="no-padding" style="margin-top:8px;">
    <@c.Form id="widgetmetaForm" class="validate" action="${ContextPath}/appcfg/resourceManage/saveWidgetMetaVO.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#widgetmetaContent"}>
        <@c.FormIdVersion id="${widgetMetaVO?if_exists.id?if_exists}" version="${widgetMetaVO?if_exists.version?default(0)}" />
        <@c.Hidden name="dirId" value="${widgetMetaVO?if_exists.dirId?if_exists}" />
       <@c.FormInput name="name" label="名称" value="${widgetMetaVO?if_exists.name?if_exists}" validation={"required":true,"minlength":1,"maxlength":64,"remote":"${ContextPath}/appcfg/resourceManage/synchroCheck.d?dirId=${widgetMetaVO?if_exists.dirId?if_exists}&&id=${widgetMetaVO?if_exists.id?if_exists}"} messages={"remote":"资源名称不能重复，请确认！"}/>
        <@c.FormInput name="memo" validation={"minlength":1,"maxlength":50} label="描述" value="${widgetMetaVO?if_exists.memo?if_exists}"/>
        <#-- <@c.FormComboBox name="type" label="类型"  validation={"required":true} value="${widgetMetaVO?if_exists.type?if_exists}">
	      	 <#list res_type as doc>
	       		<@c.Option value="${doc.doc_code?if_exists}">${doc.doc_name?if_exists}</@c.Option>
	        </#list>
        </@c.FormComboBox>-->
        <#if editType?if_exists=="edit">
        	<@c.FormInput name="type" label="类型" value="${widgetMetaVO?if_exists.type?if_exists}" readonly=true/>
        <#else>
        	<@c.FormInput name="type" label="类型" value="${type?if_exists}" readonly=true/>
        </#if>
        <@c.FormInput name="loadUrl" label="加载URL" value="${widgetMetaVO?if_exists.loadUrl?if_exists}" validation={"required":true,"minlength":1,"maxlength":256}/>
    	<@c.FormText name="preference" label="配置信息">${widgetMetaVO?if_exists.preference?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#widgetmetaForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>


