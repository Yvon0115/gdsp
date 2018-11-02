<#import "/gdsp/tags/castle.ftl" as c>
<style>
	.modal-title{
	size: 14px;
	}
	.modal-header{
	background:#f4f4f4;
	height:45px;
	}
</style>
<modal-title>选择数据表</modal-title>
<div class="autoscroll">
	<div id="body" class="modal-body autoscroll">
		<@c.Box>
			<@c.BoxBody>
				<@c.TableLoader id="idxtable" url="${ContextPath}/index/indexmanager/IdxListData.d">
		            <#include "idxdatatab-data.ftl">
		        </@c.TableLoader>
			</@c.BoxBody>
		</@c.Box>
	</div>
</div>
<div class="modal-footer" >
    <@c.Pagination class="pull-right" target="#idxtable" page=defDocVO/>
</div>