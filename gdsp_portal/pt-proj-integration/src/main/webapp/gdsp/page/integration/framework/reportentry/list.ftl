<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
		<div id="treeDiv" class="col-md-3 no-padding" >
		<@c.Box >
		   	 <@c.BoxHeader class="border header-bg">
		 			<@c.Button  size="sm" type="primary" icon="glyphicon glyphicon-refresh" click="toSysCognosFolder(e)"> 同步资源</@c.Button>
		 			<@c.Button  size="sm" icon="glyphicon glyphicon-remove-sign" click="deleteResource()"> 删除目录</@c.Button>
			</@c.BoxHeader>
		    <@c.BoxBody class="no-padding scrollbar"  style="width:100%;max-height:500px;min-height:400px;height:450px; ">
				
					<#include "report-tree.ftl">
				
			</@c.BoxBody>
		</@c.Box>
		</div>
		<div id="bodyid" class="col-md-9 no-padding" >
	        <@c.Box class="box-right">
	            <@c.BoxHeader  class="border header-bg">
	                    <div class="btn-group">
							  <@c.Button  size="sm" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/framework/reportentry/delete.d",{"dataloader":"#widgetName"},{"checker":["id","#widgetName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button> 
						 </div>
	                <@c.Search class="pull-right" placeholder="名称/报表类型"  target="#widgetName"  conditions="report_name,report_type"/>
	            </@c.BoxHeader>
	            <@c.BoxBody class="no-padding scrollbar" style="width:100%;max-height:500px;min-height:400px;">
	                <@c.TableLoader id="widgetName" url="${ContextPath}/framework/reportentry/listData.d">
	                    <#include "list-data.ftl">
	                </@c.TableLoader>
	            </@c.BoxBody>
	            <@c.BoxFooter>
	                  <@c.Pagination class="pull-right" target="#widgetName" page=widgets?default("") />
	            </@c.BoxFooter>
	        </@c.Box>
		</div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
 <@c.Hidden name="reportId" value="" />
 <@c.Hidden name="report_path" value="" />
 <@c.Script src="script/integration/framework/reportentry/report"  />
 