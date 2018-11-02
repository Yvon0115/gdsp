<#--部署参数设置列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign required>!<#noparse>
<#if row.required??&& row.required=="Y">是<#else>否</#if>
</#noparse>
</#assign>
<#assign value>!<#noparse>
<#--<input type="text" value ="${row.value?if_exists}" id="value" name="parameters[${row_index}].value" <#if row.required??&& row.required=="Y">validation="{required:true}"</#if> ></input>-->
<div class="row">
	<div class="form-group ">
		<div class="control-container  col-xs-offset-2 col-sm-8" style="margin-left:0px">
				<#if row.required??&& row.required=="Y">

				<input type="text" value ="${row.value?if_exists}" id="value" name="parameters[${row_index}].value" validation="{required:true}"></input>
				<span class="valid-flag" style="right: 48px;height: 26px"><i class="fa fa-asterisk" normal="fa fa-asterisk"></i></span>

				<#else>

				<input type="text" value ="${row.value?if_exists}" id="value" name="parameters[${row_index}].value" ></input>
				<span class="valid-flag" style="right: 48px;height: 26px"><i class="fa icon-blank" normal="fa icon-blank"></i></span>

				</#if>
		</div>
	</div>

	<input type="hidden" value ="${row.paraname?if_exists}" id="paraname" name="parameters[${row_index}].paraname"></input>
	<input type="hidden" value ="${row.required?if_exists}" id="required" name="parameters[${row_index}].required"></input>
	<input type="hidden" value ="${row.description?if_exists}" id="description" name="parameters[${row_index}].description"></input>
</div>
</#noparse>
</#assign>
<@c.SimpleTable id="paraTable" checkboxName="paraname" checkboxfield="paraname" striped=false titles=["名称","是否必输","描述","值"] keys=["paraname",required,"description",value]  data=parameters checkbox=false/>