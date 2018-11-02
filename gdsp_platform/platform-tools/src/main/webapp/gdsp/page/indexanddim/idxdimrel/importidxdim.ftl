<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/indexanddim/idxdim.js"/>-->
<@c.Script src="script/indexanddim/idxdim" />
<modal-title>导入指标维度关联关系</modal-title>
<@c.Form id="inportForm" class="validate" action="${ContextPath}/indexanddim/idxdimrel/execImportIdxDim.d" method="post" enctype="multipart/form-data" after={"pagereload()":""}>
<div class="modal-body autoscroll">
       <@c.FormInput name="excelFile" helper="(支持格式:xls,xlsx)" type="file" label="选择文件" validation={"required":true}/>  
</div>
</@c.Form>
<div class="modal-footer">
	<button class="btn btn-primary" type="submit" onclick="saveForm()"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
