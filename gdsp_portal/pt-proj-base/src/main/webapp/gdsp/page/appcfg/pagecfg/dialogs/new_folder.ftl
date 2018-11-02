<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：新增目录
-->
<modal-title>新建目录</modal-title>
<div class="modal-body" id="modalBodyId">
	<!--after={"switchtab":"#mainPanel","dataloader":"#usersContent"}-->
	<@c.Form id="folderForm" class="validate" action="${ContextPath}/appcfg/pagecfg/saveFolder.d" method="post" after={"pagereload()":""}>
        <@c.FormIdVersion id="${folder?if_exists.id?if_exists}" version="${folder?if_exists.version?default(0)}"/>
        <@c.Hidden name="parent_id" id="parent_id" value="${parent_id?default('100001')}" />
        <@c.FormInput name="dir_name" label="目录名称" value="${folder?if_exists.dir_name?if_exists}" helper="1-64位字符,仅支持汉字、字母、数字组合"  validation={"required":true,"alphanumer":true,"minlength":1,"maxlength":64,"remote":"${ContextPath}/appcfg/pagecfg/nameCheck.d?parent_id=${parent_id?default('100001')}"}  messages={"remote":"同一文件夹下名字不能重复，请确认！"}/>
    </@c.Form>
</div>
<div class="modal-footer">
 	<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#folderForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
