<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader>
                <@c.ButtonGroup  class="pull-left">
                    <@c.Button  size="sm"  action=[c.rpc("${ContextPath}/workflow/deployment/delete.d",{"dataloader":"#deploymentContent"},{"checker":["id","#deploymentContent"],"confirm":"确认删除选中流程？"})]>删除流程</@c.Button>
                </@c.ButtonGroup>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <@c.Search class="pull-left"  target="#processContent" conditions="code,name"/>
                
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="deploymentContent" url="${ContextPath}/workflow/deployment/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />
</@c.Tabs>
