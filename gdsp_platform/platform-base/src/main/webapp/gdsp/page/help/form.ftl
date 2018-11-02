<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader >
        <h3 class="box-title"><#if sysNoticeVo??&& sysNoticeVo.id??>修改附件<#else>添加附件</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="noticeForm" class="validate" action="${ContextPath}/help/save.d" method="post" enctype="multipart/form-data" after={"switchtab":"#mainPanel","dataloader":"#noiticeVos"}>
        <@c.FormIdVersion id="${sysNoticeVo?if_exists.id?if_exists?html}" version="${sysNoticeVo?if_exists.version?default(0)}"/>
        <@c.FormInput name="title" label="标题" value="${sysNoticeVo?if_exists.attach_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":128}/>
        <@c.FormText name="memo" label="附件描述" validation={"minlength":1,"maxlength":500}>${sysNoticeVo?if_exists.memo?if_exists}</@c.FormText>
    	<@c.FormInput name="fileName" style="padding-top:0px;padding-bottom:0px;" type="file" label="选择附件"/>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#noticeForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
