<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/user_main.js"/>-->
<@c.Script src="script/grant/user_main" />
<modal-title>导入用户</modal-title>
<@c.Form id="inportForm" class="validate" action="${ContextPath}/grant/user/execImportUser.d" method="post" enctype="multipart/form-data" after={"$.closeDialog()":""}>
<div class="modal-body autoscroll">
       <@c.FormInput name="excelFile" helper="(支持格式:xls,xlsx)" type="file" label="选择文件" validation={"required":true}/>  
</div>
<div class="modal-footer">
	<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
</@c.Form>