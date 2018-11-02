<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_delegate.js"/>-->
<@c.Script src="script/wf_delegate" />

<modal-title>选择代理人</modal-title>
<div class="autoscroll">
	<div id="body" class="modal-body autoscroll">
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Search class="pull-right"  target="#acceptsContent" conditions="username" placeholder="姓名"/>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.TableLoader id="acceptsContent" url="${ContextPath}/workflow/delegate/acceptListData.d">
		            <#include "acceptList-data.ftl">
		        </@c.TableLoader>
			</@c.BoxBody>
		</@c.Box>
	</div>
</div>
<div class="modal-footer" >
<#--<button type="button" class="btn btn-primary" click="saveAccept()" data-dismiss="modal"   >确定</button>
    <button type="button" class="btn btn-default"  data-dismiss="modal" >取消</button> -->
    <@c.Pagination class="pull-right" target="#acceptsContent" page=accepts/>
</div>