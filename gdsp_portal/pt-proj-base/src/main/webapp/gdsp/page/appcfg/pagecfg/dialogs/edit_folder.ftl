<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：新增目录
-->
<modal-title>修改目录</modal-title>
<div class="modal-body" id="modalBodyId">
	<!--after={"switchtab":"#mainPanel","dataloader":"#usersContent"}-->
	<@c.Form id="folderForm" class="validate" action="${ContextPath}/appcfg/pagecfg/editFolder.d" method="post" after={"pagereload()":""}>
        <@c.FormIdVersion id="${dir?if_exists.id?if_exists}" version="${dir?if_exists.version?default(0)}"/>
        <@c.FormInput name="dir_name" label="目录名称"  helper="1-64位字符,仅支持汉字、字母、数字组合" value="${dir?if_exists.dir_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":64}/>
    </@c.Form>
</div>
<div class="modal-footer">
 	<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#folderForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
