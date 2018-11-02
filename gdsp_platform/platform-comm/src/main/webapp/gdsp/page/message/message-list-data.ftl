<#--消息列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
	<#-- <@c.TableOperate> -->
		<#-- <li><@c.Link title="消息详情" icon="glyphicon glyphicon-scale" action=[c.opentab("#detailActionPanel","${ContextPath}/tools/message/lookMessage.d?id=${row.id}")]>详情</@c.Link></li> -->
		<#-- <li> -->
		<@c.Link title="删除消息" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/tools/message/delete.d?id=${row.id}",{"dataloader":"#messageContent"},{"confirm":"确认删除消息‘${row.subject?if_exists}’？"})]>删除</@c.Link>
		<#-- </li> -->
		<#-- 
		<#if row.iflook??&& row.iflook=="N">
		<li><@c.Link title="标为已读消息" icon="glyphicon glyphicon-tint" action=[c.rpc("${ContextPath}/tools/message/signlook.d?id=${row.id}",{"dataloader":"#messageContent"},{"confirm":"确认标为已读‘${row.subject?if_exists}’？"})]>标为已读</@c.Link></li>
		</#if>
		 -->
	<#-- </@c.TableOperate> -->
</#noparse>
</#assign>
<#assign subject>!<#noparse>
<#if row.subject??&&row.iflook??&& row.iflook=="Y">
	<@c.Link title=row.subject action=[c.opentab("#detailActionPanel","${ContextPath}/tools/message/lookMessage.d?id=${row.id}")]>${row.subject}</@c.Link>
<#else>
	<@c.Link title=row.subject action=[c.opentab("#detailActionPanel","${ContextPath}/tools/message/lookMessage.d?id=${row.id}")]><p style="font-weight:bold;">${row.subject}</p></@c.Link></p>
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=false  titles=["主题","发送者","发送时间",""] keys=[subject,"fromuseraccount","transtime",op] data=messagePages.content checkbox=true/>
<@c.PageData page=messagePages?default("")/>
