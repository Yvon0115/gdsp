<#--预警类型列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
    <div style="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/schedule/alertdef/add.d")]>添加</@c.Button>
                <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/schedule/alertdef/delete.d",{"dataloader":"#alertdefsContent"},{"checker":["name","#alertdefsContent"],"confirm":"确认删除选中任务类型？"})]>删除</@c.Button>
            </@c.BoxHeader>
            
            <@c.BoxBody><!-- 用户组表格 -->
                <@c.TableLoader id="alertdefsContent" url="${ContextPath}/schedule/alertdef/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
        </div>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
    <@c.Tab  id="deployPanel" >
    </@c.Tab>    
</@c.Tabs>
