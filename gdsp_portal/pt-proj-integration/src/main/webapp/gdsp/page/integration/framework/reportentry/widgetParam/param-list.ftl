<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>批量添加参数</modal-title>	

<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
		<@c.Box>
	         <@c.BoxBody class="scrollbar"  style="height:500px;">
	         	<#include "param-tree.ftl">
			 </@c.BoxBody>
		</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
			<@c.Box class="box-right" id="paramPanelId">
			    <@c.BoxHeader class="border">
			         	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.rpc("${ContextPath}/framework/widgetParam/batchSaveParam.d?widget_id=${widget_id?if_exists}",{"dataloader":"#paramName"},{"checker":["id","#ParamContent"],"confirm":"确认添加选中参数？"})]>添加</@c.Button>
			      	<@c.Search class="pull-right"  target="#ParamContent" conditions="name" placeholder="名称"/>
			    </@c.BoxHeader>
			    <@c.BoxBody id="files" class="scrollbar" style="width:100%;height:453.5px;">
			    	<@c.TableLoader id="ParamContent" url="${ContextPath}/framework/widgetParam/queryParam.d?widget_id=${widget_id?if_exists}">
			                <#include "param-list-data.ftl">
			        </@c.TableLoader>
			    </@c.BoxBody>
			</@c.Box>
		 </div>
		 <@c.Hidden name="dirId" value="" />
		 <@c.Hidden name="name" value="" />
		 </@c.Tab>
</@c.Tabs>

