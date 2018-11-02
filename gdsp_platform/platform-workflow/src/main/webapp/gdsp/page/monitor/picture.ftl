<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>流程图</modal-title>
<div class="modal-body autoscroll">
	<@c.Hidden name="actName" value="${actName}"/>
	<p style="text-align: center">
		<img src="${ContextPath}/workflow/process/image.d?procInsId=${actName}"/>					
	</p>
</div>
<!--<@c.BoxHeader title="审批记录 "></@c.BoxHeader>
<div class="modal-body autoscroll">
	<@c.SimpleTable striped=true titles=["节点名称","审批人","审批结果","附加意见"] keys=["actName","userId","result","options"] data=hdetail.content/>
</div>-->