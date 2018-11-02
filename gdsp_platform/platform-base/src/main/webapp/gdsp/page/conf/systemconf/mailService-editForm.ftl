<#import "/gdsp/tags/castle.ftl" as c>
<#-- 邮件服务配置 -->
<@c.Box>
    <@c.BoxHeader class="border header-bg" >
        <h3 class="box-title">编辑权限时效配置</h3>
    </@c.BoxHeader>
    <@c.BoxBody>
        <@c.Form id="mailServiceEditForm" action="${ContextPath}/func/systemconf/saveMailServiceConfigs.d" after={"switchtab":"#systemConfMainPanel","reloadMailConf()":""}>
            <@c.FormIdVersion id="${mailServiceConfVO?if_exists.id?if_exists}" version="${mailServiceConfVO?if_exists.version?default(0)}" />
            <@c.FormCheckBox name="mailServiceState" label="邮件服务开关:" value="${mailServiceConfVO?if_exists.mailServiceState?if_exists}" checkValue="Y" />
            <@c.FormItem id="mailServerRadio" label="邮件服务器配置:">
            <input type="radio"  value="0"  id="enable"  name="mailServerConf" <#if mailServiceConfVO?if_exists.mailServerConf?if_exists=='0'>checked</#if> /><span style="margin-left: 5px;">数据库</span>
            <input type="radio"  value="1"  id="disable" style="margin-left: 5px;"  name="mailServerConf" <#if mailServiceConfVO?if_exists.mailServerConf?if_exists=='1'>checked</#if> /><span style="margin-left: 5px;">配置文件</span>
            </@c.FormItem>
        </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#mailServiceEditForm")]>保存</@c.Button>
        <@c.Button type="cancel" icon="glyphicon glyphicon-off" action=[c.opentab("#systemConfMainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>