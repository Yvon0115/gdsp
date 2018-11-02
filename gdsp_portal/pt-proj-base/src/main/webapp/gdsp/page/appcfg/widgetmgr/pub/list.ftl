<#import "/gdsp/tags/castle.ftl" as c>
<!-- <input type="hidden" name="jsRequire" value="${__scriptPath}/page_user.js"/> -->
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
		<div>    
        <@c.Box>
            <@c.BoxHeader  class="border">
                    <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/widgetmgr/pub/add.d")]>增加</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/widgetmgr/pub/delete.d",{"dataloader":"#widgetName"},{"checker":["id","#widgetName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <@c.Search class="pull-right"  target="#widgetName" conditions="widget_desc,widget_name"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="widgetName" url="${ContextPath}/widgetmgr/pub/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#widgetName" page=widgets/>
		        </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>

