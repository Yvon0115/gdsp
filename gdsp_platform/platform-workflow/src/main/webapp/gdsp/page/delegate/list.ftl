<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
	<@c.Tab id="mainPanel" active=true>
		<@c.Box>
			 <@c.BoxHeader class="border header-bg">
			 	<@c.Button type="primary" icon="glyphicon glyphicon-plus" size="sm" action=[c.opentab("#detailPanel","${ContextPath}/workflow/delegate/add.d")]>新增</@c.Button>
			 	<@c.Button size="sm" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/workflow/delegate/delete.d",{"dataloader":"#delegatesContent"},{"checker":["id","#delegatesContent"],"confirm":"确认删除？"})]>删除</@c.Button> 
			 	<@c.Search class="pull-right" target="#delegatesContent" placeholder="根据代理人查询..." conditions="userName"/>
			 </@c.BoxHeader>
			 <@c.BoxBody>
			 	<@c.TableLoader id="delegatesContent" url="${ContextPath}/workflow/delegate/listData.d">
			 		<#include "list-data.ftl">
			 	</@c.TableLoader>
			 </@c.BoxBody>
			 <@c.BoxFooter>
			 	<@c.Pagination class="pull-right" target="#delegatesContent" page=delegates/>
			 </@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="detailPanel"/>
</@c.Tabs>