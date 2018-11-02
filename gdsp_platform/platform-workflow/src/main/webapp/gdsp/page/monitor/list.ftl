<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true >
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Search class="pull-right" target="#monitorListContent" placeholder="输入流程名称、编码" conditions="deploymentCode,deploymentName"/>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.TableLoader id="monitorListContent" url="${ContextPath}/workflow/monitor/listData.d">
					<#include "list-data.ftl">
				</@c.TableLoader>
			</@c.BoxBody>
			<@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#monitorListContent" page=monitorList/>
			</@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="detailPanel" />
	<@c.Tab id="detailPanel2" />
</@c.Tabs>