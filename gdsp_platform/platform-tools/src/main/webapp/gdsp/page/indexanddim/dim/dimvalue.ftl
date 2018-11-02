<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
		<@c.Box>
            <@c.BoxHeader class="border header-bg">
            <@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
            </@c.BoxHeader>
             <@c.BoxBody>
				<@c.TableLoader id="dimValueListContent" url="${ContextPath}/indexanddim/dim/dimValueTreeData.d?dimId="+dimId>
                    <#include "dimvalue-tree.ftl">
				</@c.TableLoader>
            </@c.BoxBody>
         </@c.Box>
</@c.Tabs>
		 