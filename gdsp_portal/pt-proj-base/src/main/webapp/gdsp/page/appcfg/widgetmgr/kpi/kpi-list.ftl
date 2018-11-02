<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>批量添加指标</modal-title>	

<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
		<@c.Box>
	         <@c.BoxBody class="scrollbar"  style="height:500px;">
	         	<@c.TableLoader id="catTreeLoaderId"  url="${ContextPath}/portal/kpilibrary/loadTree.d">
					<#include "kpi-cat-tree.ftl" />
				</@c.TableLoader>
				<@c.Hidden name="catId" value=""/>
				<@c.Hidden name="catName" value=""/>
			 </@c.BoxBody>
		</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
			<@c.Box class="box-right" id="kpiPanelId">
			    <@c.BoxHeader class="border">
			         	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.rpc("${ContextPath}/widgetmgr/kpi/addBatchKpi.d?kpi_id=${kpi_id?if_exists}",{"dataloader":"#kpiName"},{"checker":["id","#kpiContent"],"confirm":"确认添加选中指标？"})]>批量添加</@c.Button>
			      	<@c.Search class="pull-right"  target="#kpiContent" conditions="name" placeholder="名称"/>
			    </@c.BoxHeader>
			    <@c.BoxBody id="files" class="scrollbar" style="width:100%;height:406px;">
			    	<@c.TableLoader id="kpiContent" url="${ContextPath}/widgetmgr/kpi/queryKpi.d?kpi_id=${kpi_id?if_exists}">
			                <#include "kpi-list-data.ftl">
			        </@c.TableLoader>
			    </@c.BoxBody>
			     <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#kpiContent" page=kpiVOPage/>
		        </@c.BoxFooter>
			</@c.Box>
		 </div>
		 </@c.Tab>
		 <@c.Tab  id="detailPanel" />
		 <@c.Tab  id="filePanel" />
</@c.Tabs>
	<@c.Hidden name="dirId" value="" />

<@c.Script id="kpiLibPageJS" src="script/portal/kpiLib"/>
