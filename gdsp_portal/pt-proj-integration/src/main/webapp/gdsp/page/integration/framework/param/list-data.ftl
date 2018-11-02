<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
	<@c.Link title="修改" icon="glyphicon glyphicon-edit"    action=[c.opentab("#detailPanel","${ContextPath}/param/param/edit.d?id=${row.id}")]>修改</@c.Link>
	<@c.Link title="删除" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/param/param/delete.d?id=${row.id}",{"dataloader":"#param_libContent"},{"confirm":"确认删除记录‘${row.displayName?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>	
</#noparse>
</#assign>
<#if params??>
	<@c.SimpleTable striped=true titles=["参数名称","显示名称","参数类型", ""] keys=["name","displayName","type",op]  ellipsis={"name":"80px","displayName":"80px","comments":"100px"} data=params.content checkbox=true/>
	<#if (params.totalPages > 0)>
		<@c.PageData page=params />
    	<@c.Pagination class="pull-right" target="#param_libContent" page=params/>
    </#if>
</#if>

 