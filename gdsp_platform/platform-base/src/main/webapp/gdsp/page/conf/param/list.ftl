<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Search class="pull-right"  target="#paramContent"  placeholder="参数编码/参数名称" conditions="parcode,parname"/>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="paramContent" url="${ContextPath}/conf/param/listdata.d">
                    <#include "paramtable.ftl">
                 </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#paramContent" page=page/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >

    </@c.Tab>
</@c.Tabs>