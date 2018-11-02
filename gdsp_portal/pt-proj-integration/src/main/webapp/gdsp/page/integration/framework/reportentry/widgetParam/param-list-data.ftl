<#import "/gdsp/tags/castle.ftl" as c>

<#if params??>
	<@c.SimpleTable striped=false titles=["参数名称","显示名称","参数类型"] keys=["name","displayName","type"]  ellipsis={"name":"200px","comments":"200px"} data=params.content checkbox=true/>
	<#if (params.totalPages > 0)>
		<@c.PageData page=params />
    	<@c.Pagination class="pull-right" target="#ParamContent" page=params/>
    </#if>
</#if>