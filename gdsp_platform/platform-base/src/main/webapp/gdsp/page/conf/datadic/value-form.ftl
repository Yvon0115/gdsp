<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <p class="box-title" style="color:#010101;font-size:14px;padding-left:8px;"><#if dataDicValueVO??&& dataDicValueVO.id??>修改<#else>添加</#if></p>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="dicValueForm" class="validate" action="${ContextPath}/conf/datadic/saveValue.d" method="post" after={"switchtab":"#dicPanel","dataloader":"#dicContents"}>
        <@c.Hidden id="pk_dicId" name="pk_dicId" value="${pk_dicId}" />
        <@c.FormIdVersion id="${dataDicValueVO?if_exists.id?if_exists}" version="${dataDicValueVO?if_exists.version?default(0)}"/>
        <@c.FormInput name="dimvl_code" label="编码" value="${dataDicValueVO?if_exists.dimvl_code?if_exists}" validation={"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/conf/datadic/synchroCheckVal.d?id=${dataDicValueVO?if_exists.id?if_exists}&pk_dicId=${pk_dicId}"} messages={"remote":"编码不能重复，请确认！"}/>
        <@c.FormInput name="dimvl_name" label="名称" value="${dataDicValueVO?if_exists.dimvl_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/conf/datadic/synchroCheckVal.d?id=${dataDicValueVO?if_exists.id?if_exists}&pk_dicId=${pk_dicId}"} messages={"remote":"同一维度下名称不能重复，请确认！"}/>
        <#if dataDicValueVO??&& dataDicValueVO.id??>
        	<@c.FormRef  name="pk_fatherId" id="pk_fatherId" label="上级名称" value="${dataDicValueVO?if_exists.pk_fatherId?if_exists}"  showValue="${dataDicValueVO?if_exists.parentName?if_exists}" url="${ContextPath}/conf/datadic/toParentPage.d?pk_dicId=${pk_dicId}"/>
        <#else>
        	<@c.FormRef  name="pk_fatherId" id="pk_fatherId" label="上级名称" value="${dataDicValueVO?if_exists.pk_fatherId?if_exists}"  showValue="${dataDicValueVO?if_exists.dimvl_name?if_exists}" url="${ContextPath}/conf/datadic/toParentPage.d?pk_dicId=${pk_dicId}"/>
        </#if>
        <@c.FormText name="dimvl_desc" label="描述"  validation={"minlength":1,"maxlength":100} >${dataDicValueVO?if_exists.dimvl_desc?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#dicValueForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#dicPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
