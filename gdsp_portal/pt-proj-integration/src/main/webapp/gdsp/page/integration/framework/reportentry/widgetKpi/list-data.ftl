<#import "/gdsp/tags/castle.ftl" as c>
<#if kpis??>
	<#assign op>#<#noparse>
		<#if  row.id?? >
			<@c.TableLinks>
				<@c.Link title="删除" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/framework/widgetKpi/delete.d?id=${row.pk_index}&widgetId=${widgetId}",{"dataloader":"#kpiName"},{"confirm":"确认删除记录‘${row.indexInfoVO.indexName?if_exists}’？"})]>删除</@c.Link>
			</@c.TableLinks>
			  <#else>
			  <@c.TableLinks>
			</@c.TableLinks>
		</#if>
	</#noparse>
	</#assign>
	<@c.SimpleTable striped=false titles=["名称","别名","<th width='150px'></th>"] checkboxfield="pk_index" keys=["indexInfoVO.indexName","indexInfoVO.indexName",op] data=kpis?default({"content":[]}).content checkbox=true/>
	<@c.PageData page=kpis?default("") />
 </#if>