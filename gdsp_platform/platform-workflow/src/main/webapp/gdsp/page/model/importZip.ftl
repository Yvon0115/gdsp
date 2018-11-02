<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>导入流程文件</modal-title>
<@c.Form id="inportForm" class="validate" action="${ContextPath}/workflow/model/uploadDeploy.d" method="post" enctype="multipart/form-data" after={"pagereload()":""}>
<div class="autoscroll">
       <@c.FormInput name="zipFile" helper="(支持格式:zip)"  type="file" label="选择文件"/>  
</div>
<div class="modal-footer">
	<button class="btn btn-primary" type="submit">确定</button>
	<@c.Button type="canel"  action=[c.dismiss()]>取消</@c.Button>
</div>
</@c.Form>