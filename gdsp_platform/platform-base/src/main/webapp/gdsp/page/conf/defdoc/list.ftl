<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/conf/defdoc/defdoc.js">-->
<@c.Script src="script/conf/defdoc/defdoc" />
<@c.Tabs>
    <@c.Tab  id="docPanel" active=true>
		<div>    
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel1","${ContextPath}/conf/defdoc/toSava.d?type_id=${type_id?if_exists?html}")]>添加数据</@c.Button>
                    <#--  <@c.Button  size="sm"  icon="glyphicon glyphicon-remove-sign" action=[c.rpc("${ContextPath}/conf/defdoc/delete.d",{"dataloader":"#docContents"},{"checker":["id","#docContents"],"confirm":"确认删除选中数据？"})]>删除数据</@c.Button>-->
                    <@c.Button   size="sm" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="docContents" url="${ContextPath}/conf/defdoc/docList.d?type_id=${type_id?if_exists?html}">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#docContents" page=defDocVO/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel1" >
    </@c.Tab>
   
   
</@c.Tabs>
