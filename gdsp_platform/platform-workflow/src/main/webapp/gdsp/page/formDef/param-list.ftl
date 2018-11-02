<#--参数设置列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!
    <#noparse>
		<@c.Link title="删除参数" icon="glyphicon glyphicon-remove" click="deleteParaRow(this)">删除</@c.Link>
	</#noparse>
</#assign>

<#assign variableName>!<#noparse>
<input type="text" value ="${row.variableName?if_exists}" id="variableName" name="parameters[${row_index}].variableName"></input> 
</#noparse>
</#assign>

<#assign displayName>!<#noparse>
<input type="text" value ="${row.displayName?if_exists}" id="displayName" name="parameters[${row_index}].displayName"></input> 
</#noparse>
</#assign>

<#assign memo>!<#noparse>
<input type="text" value ="${row.memo?if_exists}" id="memo" name="parameters[${row_index}].memo"></input> 
</#noparse>
</#assign>
<@c.SimpleTable id="paraTable" checkboxName="paraname" checkboxfield="paraname" striped=false titles=["参数名","参数显示名称","描述",""] keys=[variableName,displayName,memo,op] data=parameters checkbox=false/>