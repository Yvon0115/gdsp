<#import "/gdsp/tags/castle.ftl" as c>
<#-- 邮件服务配置 -->
<@c.Box>
    <@c.BoxHeader class="border" >
        <@c.Button type="primary" icon="glyphicon glyphicon-pencil" action=[c.opentab("#systemConfDetailPanel","${ContextPath}/func/systemconf/editMailServiceConfigs.d?")]>编辑</@c.Button>
    </@c.BoxHeader>
    <@c.BoxBody>
        <@c.Form id="mailServiceShowForm" action="" >
            <@c.FormCheckBox name="mailServiceState" label="邮件服务开关:" value="${mailServiceConfVO?if_exists.mailServiceState?if_exists}" checkValue="Y" disabled=true />
            <@c.FormItem id="mailServerRadio" label="邮件服务器配置:">
                    <input type="radio" value="0"  id="enable"  name="mailServerConf" disabled="disabled" <#if mailServiceConfVO?if_exists.mailServerConf?if_exists=='0'>checked</#if> /><span style="margin-left: 5px;">数据库</span>
                    <input type="radio" value="1"  id="disable" style="margin-left: 5px;" name="mailServerConf" disabled="disabled" <#if mailServiceConfVO?if_exists.mailServerConf?if_exists=='1'>checked</#if> /><span style="margin-left: 5px;">配置文件</span>
            </@c.FormItem>
        </@c.Form>
    </@c.BoxBody>
</@c.Box>