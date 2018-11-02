<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>功能说明导入</modal-title>
<@c.Form id="inportForm" class="form-horizontal validate" action="${ContextPath}/portal/functionDec/doImportFunDec.d" method="post" isAjax=false  enctype="multipart/form-data" >
<div class="autoscroll">
       <@c.FormInput name="excelFile" style="padding-top:0px;padding-bottom:0px;"  helper="(支持格式:xls)" type="file" label="选择文件" validation={"required":true}/>  
</div>
<div class="modal-footer">
	<button class="btn btn-primary" icon="glyphicon glyphicon-saved"  type="submit"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.dismiss()]>取消</@c.Button>
</div>
</@c.Form>