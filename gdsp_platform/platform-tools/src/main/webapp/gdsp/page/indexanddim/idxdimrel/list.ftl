<#import "/gdsp/tags/castle.ftl" as c>
<input type="hidden"   name="jsRequire" value="${__scriptPath}/indexanddim/idxdim.js"/>
<@c.Hidden name="indexId" value=""/>
<@c.Hidden name="leaf" value=""/>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
    <div class="col-md-3 no-padding">
		<@c.Box>
		    <@c.BoxBody class="scrollbar" style="height:540px;">
				<#include "idx-tree.ftl">
		    </@c.BoxBody>
		</@c.Box>
	</div>
	<div class="col-md-9 no-padding">
		<@c.Box class="box-right">
		    <@c.BoxHeader class="border header-bg">
		        <@c.Search class="pull-right"  target="#dimContent" conditions="dimname" placeholder="维度名称"/>
		        <@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addDim()">添加维度</@c.Button>
		        <@c.Button icon="glyphicon glyphicon-import" action=[c.opendlg("#importDlg","${ContextPath}/indexanddim/idxdimrel/importIdxDim.d","300","600",true)] >导入</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-export" click="emportMould()">导出模板</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/indexanddim/idxdimrel/deleteDim.d?",{"dataloader":"#dimContent"},{"checker":["id","#dimContent"],"confirm":"确认删除选中维度？"})]>删除</@c.Button>
		    </@c.BoxHeader>
	        <@c.BoxBody class="scrollbar" style="height:446px;">
	            <@c.TableLoader id="dimContent" url="${ContextPath}/indexanddim/idxdimrel/listData.d">
	                <#include "list-data.ftl">
	            </@c.TableLoader>
	        </@c.BoxBody>
	        <@c.BoxFooter>
	        <@c.Pagination class="pull-right"  target="#dimContent" page=dimList?default("")/>
	        </@c.BoxFooter> 
	    </@c.Box>
	</div>
	</@c.Tab>
	<@c.Tab id="detailPanel" >
	</@c.Tab>
</@c.Tabs>