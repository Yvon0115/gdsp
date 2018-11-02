<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/resManage/resManage"/>
<@c.Hidden id="type" name="type" value=""/>
<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div id="treeDiv" class="col-md-3 no-padding" >
			<@c.Box >
			   	 <@c.BoxHeader class="border header-bg">
						<!--<@c.Button  icon="glyphicon glyphicon-plus" type="primary"  action=[c.opentab("#detailPanel","${ContextPath}/appcfg/resourceManage/addDir.d")]>添加目录</@c.Button>-->
						<@c.Button  icon="glyphicon glyphicon-pencil" type="primary" click="editCommonDir()">编辑</@c.Button>
						<!--<@c.Button icon="glyphicon glyphicon-trash" click="deleteCommonDir()">删除</@c.Button>-->
				 </@c.BoxHeader>
		         <@c.BoxBody class="no-padding scrollbar"  style="width:100%;max-height:500px;min-height:400px;">
						<#include  "cdir-tree.ftl">
				 </@c.BoxBody>
			</@c.Box>
		</div>
		<div id="bodyid" class="col-md-9 no-padding" >
		    <@c.Box>
			    <@c.BoxHeader  class="border header-bg">
			            <@c.Button icon="glyphicon glyphicon-plus" type="primary"  click="toAddWidget()">添加资源</@c.Button>
			         	<@c.Button icon="glyphicon glyphicon-trash"   action=[c.rpc("${ContextPath}/appcfg/resourceManage/deleteWidgetmeta.d",{"dataloader":"#widgetmetaContent"},{"checker":["id","#widgetmetaContent"],"confirm":"确认删除选中资源？"})]>删除</@c.Button>
			         	<!--<@c.Button icon="glyphicon glyphicon-import"   action=[c.opendlg("#importDlg","${ContextPath}/widgetmgr/kpi/importKpi.d","300","600",true)]>导入指标</@c.Button>-->
			         	<!--<@c.Button icon="glyphicon glyphicon-export"  action=[c.opendlg("#exportDlg","${ContextPath}/widgetmgr/kpi/exportKpi.d","300","600",true)]>导出指标</@c.Button>-->
			    </@c.BoxHeader>
			    <@c.BoxBody class="no-padding" id="widgetMeta">
			    	<@c.TableLoader id="widgetmetaContent" url="${ContextPath}/appcfg/resourceManage/queryWidgetmeta.d">
		                    <#include "list-data.ftl">
		            </@c.TableLoader>
			    </@c.BoxBody>
			    
			     <@c.BoxFooter>
			         <@c.Pagination class="pull-right" target="#widgetmetaContent" page=widgetmetaPage?default("")/>
		         </@c.BoxFooter>
			  </@c.Box>
	     </div>
	  </@c.Tab>
	  <@c.Tab  id="detailPanel" />
	  <@c.Tab  id="widgetmetaPanel" />
</@c.Tabs>
<@c.Script id="resManagerPageJS" src="script/appcfg/resManage/resManage"/>