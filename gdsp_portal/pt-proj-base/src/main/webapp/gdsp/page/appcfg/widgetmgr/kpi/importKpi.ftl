<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/widgetmgr/widgetmgr"/>
<modal-title>指标导入</modal-title>
<@c.Form id="importForm" class="form-horizontal validate" action="${ContextPath}/widgetmgr/kpi/doImportKpi.d?ioType=${ioType?if_exists}" method="post" isAjax=false enctype="multipart/form-data" >
<div class="autoscroll">
     <@c.Hidden id="ioType" name="ioType" value="${ioType?if_exists}" />
       <@c.FormInput id="excelFile"  style="padding-top:0px;padding-bottom:0px;"  name="excelFile" helper="(支持格式:xls)" type="file" label="选择文件" validation={"required":true}/>  
</div>
<div class="modal-footer">
	<button id="uploadButton" class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
</@c.Form>