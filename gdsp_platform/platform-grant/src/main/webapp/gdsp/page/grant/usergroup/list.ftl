<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/grant/usergroup/add.d")]>添加用户组</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/grant/usergroup/delete.d",{"dataloader":"#usergroupsContent"},{"checker":["id","#usergroupsContent"],"confirm":"确认删除选中用户组？"})]>删除</@c.Button>
                <@c.Search class="pull-right"  target="#usergroupsContent" conditions="groupname,memo" placeholder="用户组名/描述"/><!-- 用户组搜索框 -->
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="usergroupsContent" url="${ContextPath}/grant/usergroup/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#usergroupsContent" page=usergroups/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
    
</@c.Tabs>
