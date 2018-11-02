<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainButnPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
               	<@c.Button type="primary"  icon="glyphicon glyphicon-plus"  action=[c.opentab("#detailPanelButn","${ContextPath}/func/butn/add.d?menuID=${menuID}")]>添加</@c.Button>
                <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/func/butn/delete.d",{"dataloader":"#btnContent"},{"checker":["id","#btnContent"],"confirm":"确认删除选中按钮？"})]>删除</@c.Button>
                <@c.Button icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#btnContent" conditions="funname,funcode" placeholder="名称/编码" /><!-- 搜索框 -->
            </@c.BoxHeader>
            
            <@c.BoxBody><!-- 用户组表格 -->
                <@c.TableLoader id="btnContent" url="${ContextPath}/func/butn/butnlistData.d?menuID=${menuID}">
                    <#include "butnlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#btnContent" page=butnRegisterPages/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanelButn" >
    </@c.Tab>
    
</@c.Tabs>