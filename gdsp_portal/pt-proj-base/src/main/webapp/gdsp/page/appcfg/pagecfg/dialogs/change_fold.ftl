<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>更改文件夹</modal-title>
<div class="modal-body" id="modalBodyId" style="height:250px;overflow: auto;">
	<div id="folder_tree">
		<@c.EasyTreeBuilder id="folderTree" url="/appcfg/pagecfg/toFolderTree.d" levels=2 events="{onNodeSelected:selectDirNode}" >
		</@c.EasyTreeBuilder>
	</div>
	<@c.Form id="folderForm" class="validate" action="${ContextPath}/appcfg/pagecfg/saveChgFolder.d" method="post" after={"pagereload()":""}>
        <@c.Hidden name="dir_id" id="dir_id" value="${dir_id}" />
        <@c.Hidden name="page_id" id="page_id" value="${page_id}" />
    </@c.Form>
</div>
<@c.BoxFooter class="text-center">
    <@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#folderForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</@c.BoxFooter>