<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader  class="border">
    <div class="pull-left">
        	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" click="toBatchAddKpi(e,'${widgetId?if_exists}')">批量添加</@c.Button>
            <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/framework/widgetKpi/delete.d",{"dataloader":"#kpiName"},{"checker":["id","#kpiName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
        	<@c.Button  icon="glyphicon glyphicon-arrow-left"   action=[c.opentab("#mainPanel")]>返回</@c.Button>
	</div>
         <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
        <@c.Search class="pull-left"  target="#kpiName" conditions="a.indexName" placeholder="名称"/>
    </@c.BoxHeader>
    <@c.BoxBody class="no-padding">
        <@c.TableLoader id="kpiName" url="${ContextPath}/framework/widgetKpi/listData.d?widgetId=${widgetId?if_exists}">
            <#include "list-data.ftl">
        </@c.TableLoader>
    </@c.BoxBody>
     <@c.BoxFooter>
     	<@c.Pagination class="pull-right" target="#kpiName" page=kpis?default("")/>
    </@c.BoxFooter>
</@c.Box>
<@c.Script src="script/integration/framework/reportentry/kpi"  />
