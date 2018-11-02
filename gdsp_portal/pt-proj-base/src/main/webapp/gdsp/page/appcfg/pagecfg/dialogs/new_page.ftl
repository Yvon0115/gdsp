<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：新增定制页面
-->
<modal-title>新建定制页面</modal-title>
<div class="modal-body" id="modalBodyId">
	<@c.Form id="pageForm" class="validate" action="${ContextPath}/appcfg/pagecfg/savePage.d" method="post" after={"pagereload()":""}>
        <@c.FormIdVersion id="${page?if_exists.id?if_exists}" version="${page?if_exists.version?default(0)}"/>
        <@c.Hidden name="dir_id" id="dir_id" value="${dir_id?default('100001')}" />
        <@c.Hidden name="layout_id" id="layout_id" value="np_layout4" />
        <@c.FormInput name="page_name" label="页面名称" value="${page?if_exists.page_name?if_exists}" validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":64,"remote":"${ContextPath}/appcfg/pagecfg/pageNameCheck.d?dir_id=${dir_id?default('100001')}" } helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（:.- _()[]（）【】）组合" messages={"remote":"同一文件夹下名字不能重复，请确认！"}/>
        <@c.FormInput name="page_desc" label="页面描述" value="${page?if_exists.page_desc?if_exists}" validation={"alphanumerSpec":true,"minlength":1,"maxlength":250} helper="1-250位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" />
    </@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#pageForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
