<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Search class="pull-right"  target="#taskContent" conditions="deploymentCode,deploymentName" placeholder="编码、流程名称" />
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="taskContent" url="${ContextPath}/workflow/task/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
			 	<@c.Pagination class="pull-right" target="#taskContent" page=tasks/>
			</@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="compPanel"/>
    <@c.Tab  id="detailPanel" />
</@c.Tabs>
