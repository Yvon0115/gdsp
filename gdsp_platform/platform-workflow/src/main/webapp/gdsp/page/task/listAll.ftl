<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Search class="pull-right"  target="#taskAllContent" conditions="deploymentName,categoryName" placeholder="流程名称、类型"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="taskAllContent" url="${ContextPath}/workflow/task/listAllData.d">
                    <#include "listAll-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
			 	<@c.Pagination class="pull-right" target="#taskAllContent" page=tasks/>
			 </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />
</@c.Tabs>
