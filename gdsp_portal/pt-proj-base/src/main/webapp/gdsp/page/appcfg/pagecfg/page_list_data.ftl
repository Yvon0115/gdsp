<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/pagecfg/pagecfg" />
<#assign op>#<#noparse>
	<@c.TableOperate>
		<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" click="delPage('${row.id}');">删除</@c.Link></li>
		<li><@c.Link title="页面配置" icon="glyphicon glyphicon-cog" action=[c.opentab("#detailPanel","${ContextPath}/appcfg/pagecfg/pageConfig.d?page_id=${row.id}")]>页面配置</@c.Link></li>
		<li><@c.Link title="发布功能" icon="glyphicon glyphicon-share" click="toPublishFun('${row.id}','2');">发布功能</@c.Link></li>
		<li><@c.Link title="发布页面" icon="glyphicon glyphicon-share" click="toPublishFun('${row.id}','1');">发布页面</@c.Link></li>
		<li><@c.Link title="更改目录" icon="glyphicon glyphicon-edit" click="changeDir('${row.id}');">更改目录</@c.Link></li>
	</@c.TableOperate>
</#noparse>
</#assign>
<#-- <#assign page_name>!<#noparse>
<#if row.page_name??>
<@c.Link click=""><span style="color:black;">${row.page_name}</span></@c.Link>
</#if>
</#noparse>
</#assign>-->
<@c.SimpleTable striped=true titles=["\t","页面名称",""] keys=["\t","page_name",op] ellipsis={"page_name":"400px;"} data=(pageVOs.content)?default([])/>
<@c.PageData page=pageVOs?default("")/>