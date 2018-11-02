<#import "/gdsp/tags/castle.ftl" as c>
<#if widgets??>
<#assign op>#<#noparse>
<@c.TableOperate>
    	 <li><@c.Link title="修改"  icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/framework/reportentry/edit.d?id=${row.id}")]>修改</@c.Link> </li>
		<li><@c.Link title="删除"   icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/framework/reportentry/delete.d?id=${row.id}",{"dataloader":"#widgetName"},{"confirm":"确认删除资源‘${row.report_name?if_exists}’？"})]>删除</@c.Link></li>
		 <li><@c.Link title="报表说明" icon="fa fa-history"  click="historyChange('${row.id}',1,'${row.report_name}')" >报表说明</@c.Link></li>
		<#if  row.data_source_type??& row.data_source_type == "cognos">
		<li><@c.Link title="参数维护" icon="fa fa-file-powerpoint-o" action=[c.opentab("#detailPanel","${ContextPath}/framework/widgetParam/list.d?widget_id=${row.id}")]>参数维护</@c.Link></li>
		</#if>
		<li><@c.Link title="指标维护" icon="glyphicon glyphicon-info-sign" action=[c.opentab("#detailPanel","${ContextPath}/framework/widgetKpi/list.d?widgetId=${row.id}")]>指标维护</@c.Link></li>
		<!--<li><@c.Link title="帮助说明" icon="fa fa-question-circle" action=[c.opentab("#detailPanel","${ContextPath}/framework/reportentry/editComments.d?widget_id=${row.id}")]>帮助信息</@c.Link></li>-->
		
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["名称","报表类型", "报表来源",""] keys=["report_name","report_type","data_source_type",op] data=widgets?default({"content":[]}).content checkbox=true/>
<@c.PageData page=widgets?default("") />
</#if>
<@c.Script src="script/integration/framework/history/historyChange"  />
