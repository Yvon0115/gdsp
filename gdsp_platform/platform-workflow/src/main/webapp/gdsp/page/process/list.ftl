<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_flow.js"/>-->
<#--<@c.Script src="script/wf_flow" />-->
<input type="hidden" name="categoryCode" id="categoryCode"/>
<@c.Script id="categoryCode" src="" />
<#-- 流程定义首页 -->
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true>
    
    	<!-- 流程分类树 -->
    	<div id="treeDiv" class="col-md-3 no-padding"  >
    		<@c.Box style="height:500px;">
    			<@c.BoxHeader class="border header-bg">
					<h3 class="box-title">流程分类</h3>
    				<#--<@c.Button type="primary" size="sm" action=[c.opentab("#categoryPanel","${ContextPath}/workflow/category/list.d")] >分类管理</@c.Button>-->
    			</@c.BoxHeader>
				<@c.BoxBody class="scrollbar" style="min-height:400px;">
					<#include  "category-tree.ftl">
				</@c.BoxBody>
			</@c.Box>
		</div>
		
		<!-- 流程列表 -->
		<div id="bodyDiv" class="col-md-9 no-padding" >	
			<@c.Box class="box-right" style="height:400px;">
				<@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" icon="glyphicon glyphicon-plus" size="sm"  click="openCreateWindon()">新增</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-trash" size="sm"  action=[c.rpc("${ContextPath}/workflow/process/delete.d",{"dataloader":"#processContent"},{"checker":["id","#processContent"],"confirm":"确认删除选中流程吗？"})]>删除</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-play" size="sm" action=[c.rpc("${ContextPath}/workflow/process/setup.d",{"dataloader":"#processContent"},{"checker":["id","#processContent"]})]>启用</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-stop" size="sm" action=[c.rpc("${ContextPath}/workflow/process/stop.d",{"dataloader":"#processContent"},{"checker":["id","#processContent"]})]>停用</@c.Button>
                	<@c.Search class="pull-right"  target="#processContent" conditions="deploymentCode,deploymentName"/>
            	</@c.BoxHeader>
            	<@c.BoxBody>
                	<@c.TableLoader id="processContent" url="${ContextPath}/workflow/process/listData.d">
                    	<#include "list-data.ftl">
               		</@c.TableLoader>
            	</@c.BoxBody>
            	<@c.BoxFooter>
            		<@c.Pagination class="pull-right"  target="#processContent" page=pds/>
            	</@c.BoxFooter>
			</@c.Box>
		</div>
	</@c.Tab>
	
    <@c.Tab  id="startPanel" >
    </@c.Tab>
    
    <@c.Tab  id="categoryPanel">
    </@c.Tab>
   
</@c.Tabs>
<@c.Script src="script/wf_flow" id="flowJS" />