<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/page_main.js"/>-->
<@c.Script src="script/grant/page_main" />
<@c.Hidden name="pageId" value=""/>
<@c.Hidden name="menuId" value=""/>
<@c.Tabs ulclass="header-bg" id="main">
    	<@c.Tab  id="mainPanel" active=true>
		<div id="treeDiv" class="col-md-3 no-padding" >
			<@c.Box >
			   	 <@c.BoxHeader class="border header-bg">
			   	 	<@c.Button icon="glyphicon glyphicon-sort" click="sortPage()">排序</@c.Button>
			   	 	<@c.Button icon="glyphicon glyphicon-trash" click="delPage()">删除</@c.Button>
				 </@c.BoxHeader>
			     <@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
						<#include  "page-tree.ftl">
				 </@c.BoxBody>
			</@c.Box>
		</div>
		<div id="bodyid" class="col-md-9 no-padding" >
			  <#include  "page-grant.ftl">
		</div>
	    </@c.Tab>
	    <@c.Tab  id="addUserPanel" >
	    </@c.Tab>
	    <@c.Tab  id="addRolePanel" >
    </@c.Tab>
</@c.Tabs>

				