<#--预警信息预览页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/message/message_tools.js"/>-->
<@c.Script src="script/message/message_tools" onload="closeCurrentDialog()" />
<@c.Box style="min-height:400px;">
    <@c.BoxHeader>
        <#-- <h3 class="box-title">预警信息</h3> -->
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="messageForm" action="${ContextPath}/tools/message/lookMessageInf.d" method="post" after={"switchtab":"#mainActionPanel","dataloader":"#messageContent"}>
        <@c.FormInput name="subject" label="主题" value="${messageVO?if_exists.subject?if_exists}" readonly=true />
        <@c.FormInput name="fromuseraccount" label="发送者" value="${messageVO?if_exists.fromuseraccount?if_exists}" readonly=true/>
        <@c.FormInput name="transtime" label="发送时间" value="${messageVO?if_exists.transtime?if_exists}" readonly=true/>
        <@c.FormItem id="content" label="内容"><div id="msg" class="form-control " style="height:90px;overflow-x:hidden;word-wrap:break-word;" readonly=true>${messageVO?if_exists.content?if_exists}</div></@c.FormItem>
        <#--<@c.FormText name="content" label="内容" readonly=true> ${messageVO?if_exists.content?if_exists}</@c.FormText>-->
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
         <@c.Button type="primary" icon="glyphicon glyphicon-saved" click="lookMessageInf()" >确定</@c.Button>
    </@c.BoxFooter>
</@c.Box>