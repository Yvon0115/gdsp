<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/widgetmgr/widgetmgr"/>
<@c.Tabs>
    <@c.Tab  id="mainKpiPanel" active=true>
        <@c.Box>
            <@c.BoxHeader  class="border">
            <div class="pull-left">
                	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" click="toBatchAddKpi(e,'${kpi?if_exists.id?if_exists}')">批量添加</@c.Button>
                    <@c.Button  icon="glyphicon glyphicon-plus" click="toAddKpi(e,'${kpi?if_exists.id?if_exists}')">添加</@c.Button>
                    <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/widgetmgr/kpi/delete.d?kpiId=${kpi_id?if_exists}",{"dataloader":"#kpiName"},{"checker":["id","#kpiName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                    <@c.Button  icon="glyphicon glyphicon-sort" click="toKpiSort(e,'${kpi?if_exists.id?if_exists}')">排序</@c.Button>
                	<@c.Button  icon="glyphicon glyphicon-arrow-left"   action=[c.opentab("#mainPanel")]>返回</@c.Button>
        	</div>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <@c.Search class="pull-left"  target="#kpiName" conditions="name,comments" placeholder="名称/描述"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding scrollbar" style="min-height:350px;max-height:500px">
                <@c.TableLoader id="kpiName" url="${ContextPath}/widgetmgr/kpi/listData.d?kpi_id=${kpi_id?if_exists}">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailKpiPanel" >
    </@c.Tab>
</@c.Tabs>
