<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<#if  row.kpi_id?? >
<@c.TableLinks>
	<@c.Link title="编辑"icon="glyphicon glyphicon-pencil" click="toEditKpi(e,'${row.kpi_id?if_exists}','${row.id}')">编辑</@c.Link>
	<@c.Link title="删除" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/widgetmgr/kpi/delete.d?id=${row.id}",{"dataloader":"#kpiName"},{"confirm":"确认删除记录‘${row.name?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>
  <#else>
  <@c.TableLinks>
</@c.TableLinks>
</#if>
</#noparse>
</#assign>
<#assign source>!<#noparse>
<#if  row.kpi_id?? >
    本地
    <#else>
    指标库
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["<th width='150px'>名称</th>","<th width='150px'>别名</th>","<th width='300px'>描述</th>","来源",""] keys=["name","alias","comments",source,op] data=kpis checkbox=true class="border"/>
  