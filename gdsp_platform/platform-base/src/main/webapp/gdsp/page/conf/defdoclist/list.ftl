<#import "/gdsp/tags/castle.ftl" as c>

<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/conf/defdoclist/toSava.d")]>添加数据</@c.Button>
                   <#--   <@c.Button  size="sm"  icon="glyphicon glyphicon-remove-sign" action=[c.rpc("${ContextPath}/conf/defdoclist/delete.d",{"dataloader":"#doclistContent"},{"checker":["id","#doclistContent"],"confirm":"确认删除选中数据？"})]>批量删除</@c.Button>-->
             <@c.Search class="pull-right"  target="#doclistContent" conditions="type_name" placeholder="类型名称"/>      
            </@c.BoxHeader>
            <@c.BoxBody  >
                <@c.TableLoader id="doclistContent" url="${ContextPath}/conf/defdoclist/reloadList.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#doclistContent" page=defDocListVO/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
