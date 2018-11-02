<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/grant/mydemo/add.d")]>添加用户组</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/grant/mydemo/delete.d",{"dataloader":"#demoPageContent"},{"checker":["id","#demoPageContent"],"confirm":"确认删除选中用户组？"})]>删除</@c.Button>
                <@c.Search class="pull-right"  target="#demoPageContent" conditions="name,tel" placeholder="姓名/电话"/><!-- 用户组搜索框 -->
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="demoPageContent" url="${ContextPath}/grant/mydemo/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#demoPageContent"  sizeOption="xx" jumpAction="as" page=demoPage/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
    
</@c.Tabs>
