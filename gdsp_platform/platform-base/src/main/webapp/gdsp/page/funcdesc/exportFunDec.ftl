<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/funcdesc/functionDec.js"/>-->
<@c.Script src="script/funcdesc/functionDec" />
<modal-title>功能说明导出</modal-title>
<@c.Box id="exportTreePanelId">
    <@c.BoxBody>
		<#include "export-ref-menu-tree.ftl">
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<@c.Button type="primary" icon="glyphicon glyphicon-export" click="exportFunDec()">导出</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>