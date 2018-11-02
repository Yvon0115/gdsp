<#import "/gdsp/tags/castle.ftl" as c>
	<#-- linkexp=r"${dataloader('#processContent',{'categoryCode':node.id},false,'init')}" -->
	<#-- events="{clickNode: selectNode}" 
			<@c.SimpleTree id="categoryTree" events="{clickNode: selectNode}"   defaultSelect="true">
				<@c.TreeNode label="流程分类" class="pcat">
					<@c.TreeListBuilder nodes=nodes nameField="categoryName" linkexp=r"${dataloader('#processContent',{'categoryCode':node.id})}" >
					</@c.TreeListBuilder>
				</@c.TreeNode>
			</@c.SimpleTree>-->
<!-- 流程分类树 -->
<@c.EasyTreeBuilder id="formDefTypeTree" url="/workflow/category/getTreeData.d" levels="2" events="{onNodeSelected:selectNode}" selectFirstNode=true >
</@c.EasyTreeBuilder>
	
