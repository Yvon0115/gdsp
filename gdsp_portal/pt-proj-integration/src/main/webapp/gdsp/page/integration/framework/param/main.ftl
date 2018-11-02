<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/integration/param_lib"  />
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
			<@c.Box >
			   	 <@c.BoxHeader  class="border header-bg">
		   	  		<@c.Button type="primary" icon="glyphicon glyphicon-plus" size="sm" click="addParamLibDir()" >添加</@c.Button>
					<@c.Button size="sm" icon="glyphicon glyphicon-pencil" click="editParamLibDir()">编辑</@c.Button>
					<@c.Button size="sm" icon="glyphicon glyphicon-trash" click="deleteParamLibDir()">删除</@c.Button>
				 </@c.BoxHeader>
		         <@c.BoxBody class="no-padding scrollbar"  style="width:100%;max-height:500px;min-height:400px;">
						<#include "tree.ftl">
				 </@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
			  <#include "list.ftl"/>
		</div>
	 <@c.Hidden name="dirId" value="" />
	 <@c.Hidden name="name" value="" />
	</@c.Tab>
	<@c.Tab  id="detailPanel" >
	</@c.Tab>
</@c.Tabs>