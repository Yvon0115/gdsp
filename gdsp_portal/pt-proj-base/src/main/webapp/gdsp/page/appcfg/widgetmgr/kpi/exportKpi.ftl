<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/widgetmgr/widgetmgr"/>
<modal-title>指标导出</modal-title>
<@c.Box id="exportTreePanelId">
    <@c.BoxBody>
		<#include "cdir-tree.ftl">
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<@c.Button type="primary" icon="glyphicon glyphicon-export" click="exportKpi()">导出</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>