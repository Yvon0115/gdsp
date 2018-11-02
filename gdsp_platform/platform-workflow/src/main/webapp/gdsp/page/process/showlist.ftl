<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_flow.js"/>-->
<@c.Script src="script/wf_flow" />
<#--<input type="hidden" name="categoryCode" id="categoryCode"/>-->
<@c.Script id="categoryCode" src="" />
<#-- 流程列表(仅查看和发起) -->
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<@c.Search class="pull-right"  target="#processContent" placeholder="流程名称" conditions="deploymentName"/>
            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="processContent" url="${ContextPath}/workflow/process/listProData.d">
                    <#include "showlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
            	<@c.Pagination class="pull-right"  target="#processContent" page=pds/>
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" />
    
    <@c.Tab  id="startPanel" />
	
</@c.Tabs>
