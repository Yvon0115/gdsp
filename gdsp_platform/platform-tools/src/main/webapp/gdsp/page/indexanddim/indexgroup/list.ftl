<#import "/gdsp/tags/castle.ftl" as c>
<@c.Hidden id="indGroupId" name="indGroupId" value=""/>
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true>
    <div class="col-md-3 no-padding" >
		<@c.Box>
		     <@c.BoxHeader class="border header-bg">
		        <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/indexanddim/indexgroup/add.d")]>添加</@c.Button>
		        <@c.Button icon="glyphicon glyphicon-pencil" click="toEdit()">修改</@c.Button>
		        <@c.Button  icon="glyphicon glyphicon-trash" click="toDelete()">删除</@c.Button>		        
		    </@c.BoxHeader>
		     <@c.BoxBody class="no-padding scrollbar" style="height:505px;">
		     		<@c.TableLoader id="treeContent"  url="${ContextPath}/indexanddim/indexgroup/groupList.d">
				    <#include  "ind-tree.ftl">
				    </@c.TableLoader>
		     </@c.BoxBody>
		</@c.Box>
	</div>
	<div class="col-md-9 no-padding">
		<@c.Box class="box-right">
	
		    <@c.BoxHeader class="border header-bg">
		        <input type="hidden" value="";/>
		        <@c.Search class="pull-right"   target="#indsContent" conditions="indexName,indexCode" placeholder="指标编码/指标名称"/>
		        <@c.Button type="primary" icon="glyphicon glyphicon-plus"  click="addIndex()">关联指标</@c.Button>
		        <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/indexanddim/indexgroup/deleteRelation.d?",{"dataloader":"#indsContent"},{"checker":["id","#indsContent"],"confirm":"确认删除选中指标？"})]>删除</@c.Button>		        
		    </@c.BoxHeader>
	        <@c.BoxBody class="scrollbar" style="height:455px;">
	            <@c.TableLoader id="indsContent" url="${ContextPath}/indexanddim/indexgroup/listData.d">
	                <#include "list-data.ftl">
	            </@c.TableLoader>
	        </@c.BoxBody>
	        <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#indsContent" page=inds?default("")/>
	        </@c.BoxFooter> 
	    </@c.Box>
	</div>
	</@c.Tab>
	<@c.Tab  id="detailPanel">
	</@c.Tab>
</@c.Tabs>
<@c.Script src="script/indexanddim/indGroup" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/indexanddim/indGroup.js">-->
