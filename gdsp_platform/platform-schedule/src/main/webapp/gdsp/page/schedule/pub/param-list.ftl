<#--参数设置列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!
    <#noparse>
		<@c.Link  icon="glyphicon glyphicon-remove" click="deleteParaRow(this)">删除</@c.Link>
	</#noparse>
</#assign>
<#assign paraname>!<#noparse>
<input type="text" value ="${row.paraname?if_exists}" id="paraname" name="parameters[${row_index}].paraname"></input> 
</#noparse>
</#assign>
<#assign required>!<#noparse>
	<select name="parameters[${row_index}].required">      
	         <option value="N" <#if row.required??&& row.required=="N">selected="selected"</#if> >否</option>      
	         <option value="Y" <#if row.required??&& row.required=="Y">selected="selected"</#if> >是</option>              
	</select>
</#noparse>
</#assign>
<#assign description>!<#noparse>
<input type="text" value ="${row.description?if_exists}" id="description" name="parameters[${row_index}].description"></input> 
</#noparse>
</#assign>
<@c.SimpleTable id="paraTable" checkboxName="paraname" checkboxfield="paraname" striped=false titles=["名称","是否必输","描述",""] keys=[paraname,required,description,op] data=parameters checkbox=false/>