<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/sysnotice/simple-list" />
<#-- 新增/修改公告  表单页 -->
<#-- 隐藏富文本框的toolbar，该方式隐藏不规范，待完善   leiting 2018/2/7 -->
<style>
	.cke_reset_all {
		display: none;
	}
</style>
<#assign valid_flag>   
	<#if sysNoticeVo?? && sysNoticeVo?if_exists.valid_flag?if_exists =="Y">
已发布
	<#else>
未发布
	</#if>
</#assign>
<@c.Box>
    <@c.BoxHeader class="border header-bg" >
        <h3 class="box-title"><#if sysNoticeVo??&& sysNoticeVo.id??>修改公告<#else>添加公告</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form id="noticeForm" class="validate" action="${ContextPath}/sysnotice/save.d" method="post"  after={"switchtab":"#mainPanel","dataloader":"#noiticeVos"}>
        <@c.FormIdVersion id="${sysNoticeVo?if_exists.id?if_exists?html}" version="${sysNoticeVo?if_exists.version?default(0)}"/>
        <!--  <@c.FormInput type="hidden" name="publish_date" value="${sysNoticeVo?if_exists.publish_date?if_exists}" />-->
        <@c.FormInput name="title" label="标题" value="${sysNoticeVo?if_exists.title?if_exists}" validation={"required":true,"minlength":1,"maxlength":60}
        	helper="请输入长度为 1 至 60的字符"/>
        <@c.FormCkeditorText name="content" label="内容" 
        	helper="请输入长度为1至200的字符" 
        	>${sysNoticeVo?if_exists.content?if_exists}</@c.FormCkeditorText>
       
        <!--<@c.FormInput  name="publish_date"  label="发布日期" value="${sysNoticeVo?if_exists.publish_date?if_exists}" readonly=true /> -->
        <!-- <@c.FormInput name="publish_date" label="发布日期" value="${sysNoticeVo?if_exists.publish_date?if_exists}"/>-->
        <!--<@c.FormDate name="end_date" label="失效日期" value="${sysNoticeVo?if_exists.end_date?if_exists}" validation={"required":true}/>
        -->
        
        
        <@c.Hidden name="valid_flag" value="${sysNoticeVo?if_exists.valid_flag?if_exists}"/>
        <@c.FormInput name="valid_flag_display" label="发布状态" value="${valid_flag}" readonly=true/>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#noticeForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
