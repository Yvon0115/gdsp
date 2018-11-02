<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/indexanddim/dim"/>-->
<@c.Script src="script/indexanddim/dim" />
<modal-title>选择数据表</modal-title>
<div class="autoscroll">
	<div id="body" class="modal-body autoscroll">
		<@c.Box>
			<@c.BoxBody>
				<@c.TableLoader id="dimtable" url="${ContextPath}/indexanddim/dim/TableListData.d?type_id="+type_id>
		            <#include "dimdatatable-data.ftl">
		        </@c.TableLoader>
			</@c.BoxBody>
		</@c.Box>
	</div>
</div>
<div class="modal-footer" >
    <@c.Pagination class="pull-right" target="#dimtable" page=defDocVO/>
</div>