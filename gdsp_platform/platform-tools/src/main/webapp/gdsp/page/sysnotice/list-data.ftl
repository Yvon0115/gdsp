<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/sysnotice/simple-list"/>-->
<@c.Script src="script/sysnotice/simple-list" />
<#assign op>#<#noparse>
<@c.TableOperate>
<li><@c.Link title="编辑公告" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/sysnotice/edit.d?id=${row.id}")]>编辑</@c.Link></li>
<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/sysnotice/delete.d?id=${row.id}&valid_flag=${row.valid_flag?if_exists}",{"dataloader":"#noiticeVos"},{"confirm":"确认删除‘${row.title?if_exists}’？"})]>删除</@c.Link></li>
<#if row.valid_flag??>
	<#if row.valid_flag=="Y">
		<li><@c.Link title="取消发布" icon="glyphicon glyphicon-off" click="pushSysNotice('N','${row.id}');">取消发布</@c.Link></li>
	<#else>
		<li><@c.Link title="发布" icon="glyphicon glyphicon-ok" click="pushSysNotice('Y','${row.id}');" >发布</@c.Link></li>
	</#if>
<#else>
	<li><@c.Link title="发布" icon="glyphicon glyphicon-ok" click="pushSysNotice('Y','${row.id}');">发布</@c.Link></li>
</#if>
<!--<li><@c.Link title="发布范围" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/sysnotice/range.d?id=${row.id}")]>发布范围</@c.Link></li>-->
</@c.TableOperate>
</#noparse>
</#assign>
<#assign valid_flag>!<#noparse>
<#if row.valid_flag??>
	<#if row.valid_flag=="Y">
		已发布
	<#else>
		未发布
	</#if>
<#else>
	未发布
</#if>
</#noparse>
</#assign>
<#assign content>!<#noparse>
<#if row?if_exists.content?length gte 20>
${row?if_exists.content?if_exists?substring(0,20)}
<#else>
${row?if_exists.content?if_exists?substring(0,row?if_exists.content?length)}
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["公告标题","<th width='200px'>公告内容</th>","发布时间","最后变更时间","发布状态",""] keys=["title","content","publish_date","lastModifyTime",valid_flag,op] ellipsis={"title":"140px","content":"140px"} data=noticeVoPages?default({"content":[]}).content checkbox=true/>
<@c.PageData page=noticeVoPages?default("") />