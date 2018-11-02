<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainActionPanel" active=true>
        <@c.Box>
            <@c.BoxHeader  class="border">
                <div  class="pull-left">
                    <@c.Button type="primary"  icon="glyphicon glyphicon-plus" action=[c.opentab("#detailActionPanel","${ContextPath}/widgetmgr/action/add.d?widgetid=${widgetid?if_exists?html}&type=${type?if_exists?html}")]>添加动作</@c.Button>
                    <@c.Button    icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/widgetmgr/action/delete.d",{"dataloader":"#actionName"},{"checker":["id","#actionName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                   
                   <#if widgetid ??>
                    <@c.Button   size="sm" icon="glyphicon glyphicon-arrow-left"  action=[c.opentab("#mainPanel")]>返回</@c.Button>
                   </#if>
                </div>
                 <@c.Search class="pull-right"  target="#actionName" conditions="name,code" placeholder="名称/编码"/>
            </@c.BoxHeader>
            <@c.BoxBody>
            
                <@c.TableLoader id="actionName" url="${ContextPath}/widgetmgr/action/listData.d?widgetid=${widgetid?if_exists}" >
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#actionName" page=actions/>
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailActionPanel" >
    </@c.Tab>
</@c.Tabs>
	</div>        
	
</div>
