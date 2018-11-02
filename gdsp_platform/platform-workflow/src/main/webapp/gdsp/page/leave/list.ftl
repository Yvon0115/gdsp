<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="leaveTest" src="script/wf_LeaveTest" />
<@c.Tabs ulclass="header-bg">
    <@c.Tab id="mainPanel2" active=true>
        <@c.Box style="height:200px;min-height:200px">
            <#include "leave.ftl">
            <@c.BoxFooter class="text-center">
				<@c.Button type="primary" click="startProcess()">发起</@c.Button>
		        <@c.Button type="canel"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
		    </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
</@c.Tabs>
