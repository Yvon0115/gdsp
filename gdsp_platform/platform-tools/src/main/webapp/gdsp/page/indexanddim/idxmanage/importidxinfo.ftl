<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/indexanddim/idxmanage.js"/>-->
<@c.Script src="script/indexanddim/idxmanage" />
<modal-title>导入指标</modal-title>
<@c.Form id="inportForm" class="validate" action="${ContextPath}/index/indexmanager/execImportIdxInfo.d" method="post" enctype="multipart/form-data" after={"pagereload()":""}>
<div class="modal-body autoscroll">
       <@c.FormInput name="excelFile" helper="(支持格式:xls,xlsx)" type="file" label="选择文件" validation={"required":true} id="excelFile"/>  
</div>
</@c.Form>
<div class="modal-footer">
	<button class="btn btn-primary" type="submit" onclick="saveForm()"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>