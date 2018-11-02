<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if resource??&& resource.id??>修改资源<#else>添加资源</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="resForm" class="validate" action="${ContextPath}/func/resource/save.d" method="post" after={"switchtab":"#mainResPanel","dataloader":"#resRegisterVos"}>
        <@c.FormIdVersion id="${resource?if_exists.id?if_exists}" version="${resource?if_exists.version?default(0)}"/>
       <@c.FormInput name="url" label="URL" value="${resource?if_exists.url?if_exists}" validation={"required":true,"minlength":1,"maxlength":2048,"remote":"${ContextPath}/func/resource/synchroCheck.d?id=${resource?if_exists.id?if_exists}"} messages={"remote":"URL不能重复，请确认！"}/>
        <@c.FormText name="memo" label="描述"  validation={"minlength":1,"maxlength":100}>${resource?if_exists.memo?if_exists}</@c.FormText>
        <#if resource??&& resource.id??>
   		<@c.Hidden name="parentid"  value="${resource?if_exists.parentid?if_exists}"/> 
   		<@c.Hidden name="parenttype"  value="${resource?if_exists.parenttype?if_exists}"/>  
   		<#else>
   		<@c.Hidden name="parentid"  value=muneID/>  
   		<@c.Hidden name="parenttype" value=parenttype/>
   		</#if>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"  action=[c.saveform("#resForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainResPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
