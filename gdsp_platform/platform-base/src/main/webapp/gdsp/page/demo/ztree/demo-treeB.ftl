<#--机构树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title></modal-title>
<div class="modal-body autocroll">
<@c.ZTreeBuilder id="zTreeb" url="/grant/ztree/listDemo.d" levels="2" menuCheckbox=false isFirstSelected=true isAsync=true allDisable=false asyncParam={"id":"orgId","name":"name"} events="{onNodeClick:nodeEvent}" nodes="[]" checkBoxEnable=true isCascade=false isSelectedExpand=false enableCheck=true >
</@c.ZTreeBuilder>
</div>