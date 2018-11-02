<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if butnRegisterPages??&& butnRegisterPages.id??>修改按钮<#else>添加按钮</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="butnForm" class="validate" action="${ContextPath}/func/butn/save.d" method="post" after={"switchtab":"#mainButnPanel","dataloader":"#btnContent"}>
        <@c.FormIdVersion id="${butnRegisterPages?if_exists.id?if_exists}" version="${butnRegisterPages?if_exists.version?default(0)}"/>
        <#if butnRegisterPages??&&butnRegisterPages.id??>
        <@c.FormInput name="funcode" label="按钮编码" value="${butnRegisterPages?if_exists.funcode?if_exists}" readonly=true/>
        <#else>
        <@c.FormInput name="funcode" label="按钮编码" value="${butnRegisterPages?if_exists.funcode?if_exists}" validation={"required":true,"minlength":1,"maxlength":50}/>
        </#if>
        <@c.FormInput name="funname" label="按钮名" value="${butnRegisterPages?if_exists.funname?if_exists}" validation={"required":true,"minlength":1,"maxlength":128}/>
        <#if butnRegisterPages??&& butnRegisterPages.id??>
        <@c.Hidden name="parentid" value="${butnRegisterPages?if_exists.parentid?if_exists}"/>
        <#else>
         <@c.Hidden name="parentid" value=menuID/>
         </#if>
        <@c.FormInput name="url" label="访问URL"  validation={"minlength":1,"maxlength":2048} value="${butnRegisterPages?if_exists.url?if_exists}"/>
         <@c.FormText name="memo" label="描述"   validation={"minlength":1,"maxlength":100}>${butnRegisterPages?if_exists.memo?if_exists}</@c.FormText>
       
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#butnForm")]>保存</@c.Button>
        <@c.Button type="canel"  icon="glyphicon glyphicon-off" action=[c.opentab("#mainButnPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
