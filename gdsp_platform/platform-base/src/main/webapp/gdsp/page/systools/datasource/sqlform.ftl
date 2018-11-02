<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if dateSourceVO??&& dateSourceVO.id??>修改<#else>添加</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="datasourceForm" class="validate" action="${ContextPath}/systools/ds/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#datasourcesContent"}>
        <@c.FormIdVersion id="${dateSourceVO?if_exists.id?if_exists}" version="${dateSourceVO?if_exists.version?default(0)}"/>
        <@c.Hidden name="type" value="SQL"/>
        <@c.FormInput name="name" label="数据源名称" validation={"required":true,"minlength":1,"maxlength":128} value="${dateSourceVO?if_exists.name?if_exists}"/>
       <@c.FormInput name="code" label="数据源编码" value="${dateSourceVO?if_exists.code?if_exists}"   validation={"required":true,"minlength":1,"maxlength":50,"remote":"${ContextPath}/systools/ds/synchroCheck.d?id=${dateSourceVO?if_exists.id?if_exists}"} messages={"remote":"编码不能重复，请确认！"}/>
        <@c.FormInput name="username" label="用户名" value="${dateSourceVO?if_exists.username?if_exists}"/>
        <@c.FormInput type="password" name="password" label="密码" value="${dateSourceVO?if_exists.password?if_exists}"/>
        <@c.FormInput name="driver" label="Driver类" value="${dateSourceVO?if_exists.name?if_exists}"/>
        <@c.FormInput name="URL" label="URL" value="${dateSourceVO?if_exists.url?if_exists}"/>
        <@c.FormText name="comments" label="描述" validation={"minlength":1,"maxlength":100}>${dateSourceVO?if_exists.comments?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"   action=[c.saveform("#datasourceForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
