<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ include file="/gdsp/common/global.jsp"%>

<script type="text/javascript">
jq(function() {
	jq('#endExtendEvent').val(task.endExtendEvent);
})
function saveEndTaskProperties(){
	debugger;
	task.extendEvent=jq('#endExtendEvent').val();
}
</script>
<style type="text/css">
.table{border-collapse:collapse; font-size:13px; height:24px; line-height:24px; color:black; text-align:center;}
.table tr th{color:black; font-size:13px; height:24px; line-height:24px;}
.table tr th.th_border{border-right:solid 1px #D3D3D3; border-left:solid 1px #D3D3D3;border-top:solid 1px #D3D3D3; }
.table tr td{border:solid 1px #D3D3D3;}

</style>
<table id="end-properties" style="margin-top: 10px;">
	<tr>
		<td>
			<p>结束扩展事件:</p>
		</td>
		<td>
			<input  type="text" id="endExtendEvent" class="textbox" name="endExtendEvent" onchange="saveEndTaskProperties();" />
		</td>
	</tr>
