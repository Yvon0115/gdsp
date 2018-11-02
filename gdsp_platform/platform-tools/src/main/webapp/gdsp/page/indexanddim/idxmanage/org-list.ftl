<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden"   name="jsRequire" value="${__scriptPath}/indexanddim/idxdim.js"/>-->
<@c.Script src="script/indexanddim/idxdim" />
<!--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/org.js"/>-->
<!--<@c.Script src="script/grant/org" />-->
<modal-title>选择部门</modal-title>
<div class="autoscroll">
	<div id="body" class="modal-body autoscroll">
		<@c.Box >
        	<@c.BoxBody class="scrollbar" style="height:200px;">
				 <@c.TableLoader id="orgContent"  url="${ContextPath}/index/indexmanager/orgList.d">
					<#include  "org-tree.ftl">
				 </@c.TableLoader>
			</@c.BoxBody>
		</@c.Box>
	</div>
</div>