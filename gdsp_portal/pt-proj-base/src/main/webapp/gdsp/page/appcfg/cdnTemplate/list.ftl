<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true  title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" size="sm"  icon="glyphicon glyphicon-saved" action=[c.opentab("#detailParamsPanel","${ContextPath}/widget/cdnTemplate/add.d")]>增加模板类型</@c.Button>
                    <@c.Button  size="sm"  icon="glyphicon glyphicon-remove-sign " action=[c.rpc("${ContextPath}/widget/cdnTemplate/delete.d",{"dataloader":"#paramTypeContents"},{"checker":["id","#paramTypeContents"],"confirm":"确认删除选中记录？"})]>删除模板类型</@c.Button>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="paramTypeContents" url="${ContextPath}/widget/cdnTemplate/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination  class="pull-right" target="#paramTypeContents" page=paramsPage/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailParamsPanel" >
    </@c.Tab>
</@c.Tabs>

