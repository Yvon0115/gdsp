<#import "/gdsp/tags/castle.ftl" as c>

<#if kpiVOPage??>
<#assign op>#<#noparse>
<@c.TableLinks>
	    <@c.Link title="添加" icon="glyphicon glyphicon-plus" action=[c.rpc("${ContextPath}/widgetmgr/kpi/addBatchKpi.d?kpi_id=${kpi_id?if_exists}&id=${row.id}",{"dataloader":"#kpiName"},{"confirm":"确认添加‘${row.name?if_exists}’？"})]>添加</@c.Link> 
</@c.TableLinks>
</#noparse>
</#assign>
	<@c.SimpleTable striped=false titles=["<th width='150px'>指标名称</th>","指标名称","<th width='150px'></th>"] keys=["name","comments",op] data=kpiVOPage.content checkbox=true/>
	<@c.PageData page=kpiVOPage />
</#if>