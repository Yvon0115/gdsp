<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>批量添加指标</modal-title>	

<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
		<@c.Box>
	         <@c.BoxBody class="scrollbar"  style="height:500px;">
	         	<#include "kpi-tree.ftl">
			 </@c.BoxBody>
		</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
			<@c.Box class="box-right" id="kpiPanelId">
			    <@c.BoxHeader class="border">
			         	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.rpc("${ContextPath}/framework/widgetKpi/batchSaveKpi.d?widgetid=${widgetid?if_exists}",{"dataloader":"#kpiName"},{"checker":["id","#KpiContent"],"confirm":"确认添加选中指标？"})]>添加</@c.Button>
			      	<@c.Search class="pull-right"  target="#KpiContent" conditions="indexName" placeholder="名称"/>
			    </@c.BoxHeader>
			    <@c.BoxBody id="files" class="scrollbar" style="width:100%;height:453.5px;">
			    	<@c.TableLoader id="KpiContent" url="${ContextPath}/framework/widgetKpi/queryKpi.d?widgetid=${widgetid?if_exists}">
			                <#include "kpi-list-data.ftl">
			        </@c.TableLoader>
			    </@c.BoxBody>
				
			</@c.Box>
		 </div>
		 <@c.Hidden name="dirId" value="" />
		 <@c.Hidden name="name" value="" />
		 </@c.Tab>
</@c.Tabs>

