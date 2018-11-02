<#--下拉操作框(操作多余两个使用)-->
<#macro TableOperate>
<td class="text-right">
    <div class="btn-group operation">
		<@Link action=["data-toggle=\"dropdown\""] >
            操作
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
		</@Link>
        <ul role="menu" class="dropdown-menu">
           <#nested/>
        </ul>
    </div>
</td>
</#macro>
<#--操作框(操作两个内使用)-->
<#macro TableLinks>
<td class="text-right">
	<#nested/>
</td>
</#macro>