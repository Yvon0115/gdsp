<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
		<div>    
        <@c.Box>
            <@c.BoxHeader class="border header-bg" >
                 <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus"  action=[c.opentab("#detailPanel","${ContextPath}/systools/ds/sqlAdd.d")]>添加</@c.Button>
                 <@c.Button  size="sm"   icon="glyphicon glyphicon-trash " action=[c.rpc("${ContextPath}/systools/ds/delete.d",{"dataloader":"#datasourcesContent"},{"checker":["id","#datasourcesContent"],"confirm":"确认删除选中数据？"})]>删除</@c.Button>
                 <@c.Hidden name="pk_org" value=pk_org/>
                <@c.Search class="pull-right"  target="#datasourcesContent" conditions="name" placeholder="数据源名称"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="datasourcesContent" url="${ContextPath}/systools/ds/reloadSqlList.d">
                    <#include "sqllist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#datasourcesContent" page=dateSourceVO/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
