<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
        <@c.Box>
            <@c.BoxHeader>
                <@c.ButtonGroup  class="pull-left">
                    <@c.Button type="primary" size="sm" action=[c.opentab("#detailPanel","${ContextPath}/workflow/model/create.d")]>新建流程</@c.Button>
                    <@c.Button  size="sm"  action=[c.opendlg("#importDlg","${ContextPath}/workflow/model/importZip.d","300","600",true)]>导入流程文件</@c.Button>
                    <@c.Button  size="sm"  action=[c.rpc("${ContextPath}/process/delete.d",{"dataloader":"#modelContent"},{"checker":["id","#modelContent"],"confirm":"确认删除选中流程？"})]>删除流程</@c.Button>
                </@c.ButtonGroup>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <@c.Search class="pull-left"  target="#modelContent" conditions="code,name"/>
                
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="modelContent" url="${ContextPath}/workflow/model/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />
</@c.Tabs>
