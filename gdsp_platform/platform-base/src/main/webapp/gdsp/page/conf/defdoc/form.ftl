<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <p class="box-title" style="color:#010101;font-size:14px;padding-left:8px;"><#if defDocVO??&& defDocVO.id??>修改<#else>添加</#if></p>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="docForm" class="validate" action="${ContextPath}/conf/defdoc/save.d" method="post" after={"switchtab":"#docPanel","dataloader":"#docContents"}>
        <@c.FormIdVersion id="${defDocVO?if_exists.id?if_exists}" version="${defDocVO?if_exists.version?default(0)}"/>
        <@c.Hidden name="type_id" value="${type_id?if_exists}"/>
        <#if editType??&&editType=="edit">
       	<@c.FormInput name="doc_code" label="编码" readonly=true value="${defDocVO?if_exists.doc_code?if_exists}" />
       	<#else>
       	<@c.FormInput name="doc_code" label="编码" value="${defDocVO?if_exists.doc_code?if_exists}" validation={"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/conf/defdoc/synchroCheck.d?type_id=${type_id?if_exists?html}&&id=${defDocVO?if_exists.id?if_exists?html}"} messages={"remote":"编码不能重复，请确认！"}/>
       	</#if>
        <@c.FormInput name="doc_name" label="名称" value="${defDocVO?if_exists.doc_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/conf/defdoc/synchroCheck.d?type_id=${type_id?if_exists?html}&&id=${defDocVO?if_exists.id?if_exists?html}"} messages={"remote":"名称不能重复，请确认！"}/>
		<#if defDocVO??&&defDocVO.id??>
			<@c.FormRef  name="pk_fatherId" id="pk_fatherId" label="上级名称" value="${defDocVO?if_exists.pk_fatherId?if_exists}"  showValue="${defDocVO?if_exists.parentName?if_exists}" url="${ContextPath}/conf/defdoc/toSelectParentPage.d?type_id=${type_id?if_exists}"/>
		<#else>
			<@c.FormRef  name="pk_fatherId" id="pk_fatherId" label="上级名称" value="${defDocVO?if_exists.pk_fatherId?if_exists}"  showValue="${defDocVO?if_exists.doc_name?if_exists}" url="${ContextPath}/conf/defdoc/toSelectParentPage.d?type_id=${type_id?if_exists}"/>
		</#if>
        <@c.FormText name="doc_desc" label="描述"  validation={"minlength":1,"maxlength":100} >${defDocVO?if_exists.doc_desc?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#docForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#docPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
