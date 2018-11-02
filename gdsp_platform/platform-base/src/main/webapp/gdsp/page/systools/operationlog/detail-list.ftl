<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>详细信息</modal-title>	
<#-- width和margin使用百分比，保证样式 -->
<div class="modal-body autoscroll">
	<div id="operationInf" style="line-height:25px; margin-top:-15px;">	   		
		<span style="width:27%;display:inline-block;">操 作 人 ：${operationLogVO.createBy?if_exists?html}</span>
		<span style="margin-left:13%;">操作时间：${operationLogVO.createTime?if_exists?html}</span><br />
		<span  style="width:20%;display:inline-block;">操作类型：${operationLogVO.type?if_exists?html}</span>
		<span style="margin-left:20%;">操 作 表 ：${operationLogVO.table_desc?if_exists?html}(${operationLogVO.table_name?if_exists?html})</span>
	</div>
	<@c.TableLoader id="DetailOpLogPages" style="margin-top:5px;" url="${ContextPath}/systools/operationlog/toDetailDataInfo.d?id=${operationLogVO.id}" >
		<#include "detail-list-data.ftl">
	</@c.TableLoader>
</div>

<div class="modal-footer">
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>