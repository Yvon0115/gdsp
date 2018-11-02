<#--机构树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title></modal-title>
<div class="modal-body autocroll">
<@c.ZTreeBuilder id="zTree" url="/grant/ztree/listDemo.d" levels="2" menuCheckbox=false isFirstSelected=true isAsync=true allDisable=false asyncParam={"id":"orgId","name":"name"} events="{onNodeClick:nodeEvent}" nodes="[]" checkBoxEnable=true isCascade=true enableCheck=false isSelectedExpand=true>
</@c.ZTreeBuilder>
<@c.ZTreeBuilder id="zTreea" url="/grant/ztree/listDemoB.d" iStyle="width:300px;" levels="10" menuCheckbox=false isFirstSelected=true isAsync=false allDisable=false asyncParam={"id":"orgId","name":"name"} events="{onNodeClick:nodeEvent}" nodes="[]" checkBoxEnable=true isCascade=true enableCheck=true isSelectedExpand=true>
</@c.ZTreeBuilder>
</div>