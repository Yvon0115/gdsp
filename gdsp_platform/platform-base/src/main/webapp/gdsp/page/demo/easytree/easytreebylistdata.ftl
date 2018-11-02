<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/plugins/easytree_example" />
<@c.Box>
	<@c.BoxBody>
		<@c.EasyTreeBuilder url="/plugins/getEasyTreeByListData.d" levels="2" id="treeview-listdata" showBorder=true searchAble=true showCheckbox=true checkOption="1" events="{onNodeSelected: easyTreeOnSelect}">
		</@c.EasyTreeBuilder>
    </@c.BoxBody>
</@c.Box>