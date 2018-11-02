<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>流程图</modal-title>
<div class="modal-body autoscroll">
	<@c.Hidden name="actName" value="${actName}"/>
	<p style="text-align: center">
		<img src="${ContextPath}/workflow/process/image.d?procInsId=${actName}"/>					
	</p>
</div>
