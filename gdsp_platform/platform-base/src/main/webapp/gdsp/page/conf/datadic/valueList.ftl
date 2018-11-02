<#import "/gdsp/tags/castle.ftl" as c>
<@c.Hidden name="pk_dicId" value="${pk_dicId}" />
<@c.Tabs>
    <@c.Tab  id="dicPanel" active=true>
		<div>    
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/conf/datadic/addValue.d?pk_dicId=${pk_dicId?if_exists}")]>添加数据</@c.Button>
                    <@c.Button  size="sm"  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/conf/datadic/deleteDimValue.d",{"dataloader":"#dicContents"},{"checker":["id","#dicContents"],"confirm":"确认删除选中数据？"})]>删除</@c.Button>
                    <@c.Button   size="sm" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="dicContents" url="${ContextPath}/conf/datadic/reloadValueList.d?pk_dicId=${pk_dicId?if_exists?html}">
                    <#include "valueList-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#dicContents" page=dataDicValueVO/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>   
</@c.Tabs>