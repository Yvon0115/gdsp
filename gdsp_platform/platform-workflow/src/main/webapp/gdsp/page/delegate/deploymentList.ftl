<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_delegate.js"/>-->
<@c.Script src="script/wf_delegate" />

<modal-title>选择委托流程</modal-title>
<div class="autoscroll">
	<div id="body" class="modal-body autoscroll">
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Search class="pull-right"  target="#deploymentsContent" conditions="deploymentCode,deploymentName,categoryName" placeholder="搜索"/>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.TableLoader id="deploymentsContent" url="${ContextPath}/workflow/delegate/deploymentListData.d?delId=${delId}">
		            <#include "deploymentList-data.ftl">
		        </@c.TableLoader>
			</@c.BoxBody>
		</@c.Box>
	</div>
</div>
<div class="modal-footer" >
	<@c.Button type="primary" class="pull-left" click="saveDeployment()" >确定</@c.Button>
    <@c.Pagination class="pull-right" target="#deploymentsContent" page=deploymentAltcategory/>
</div>