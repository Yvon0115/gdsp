<#import "/gdsp/tags/castle.ftl" as c>

<@c.Tabs>
    <@c.Tab  id="categoryPanel" active=true>
        <@c.Box>
            <@c.BoxHeader>
                <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus-sign" action=[c.opentab("#detailPnl","${ContextPath}/workflow/category/add.d")]>新建类型</@c.Button>
                <@c.Button size="sm" icon="glyphicon glyphicon-trash"   action=[c.rpc("${ContextPath}/workflow/category/delete.d",{"dataloader":"#categoryContent"},{"checker":["id","#categoryContent"],"confirm":"确认删除类型？"})]>删除</@c.Button>
                <@c.Button size="sm" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#categoryContent" conditions="categoryname"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="categoryContent" url="${ContextPath}/workflow/category/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
            		<@c.Pagination class="pull-right"  target="#categoryContent" page=categorys/>
            	</@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPnl" />
</@c.Tabs>
