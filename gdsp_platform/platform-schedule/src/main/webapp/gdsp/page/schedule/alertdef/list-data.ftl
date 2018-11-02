<#--预警类型列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改预警类型" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/schedule/alertdef/edit.d?name=${row.name?url}")]>修改</@c.Link></li>
	<li><@c.Link title="删除预警类型" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/schedule/alertdef/delete.d?name=${row.name?url}",{"dataloader":"#alertdefsContent"},{"confirm":"确认删除预警类型‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="部署预警类型" icon="glyphicon glyphicon-tasks" action=[c.opentab("#deployPanel","${ContextPath}/schedule/alertdef/deploy.d?name=${row.name?url}")]>部署</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>

<@c.SimpleTable striped=true checkboxName="name" checkboxfield="name" titles=["名称","实现类","描述",""] keys=["name","className","description",op] ellipsis={"name":"100px;","className":"200px;","description":"200px;"} data=jobDefs checkbox=true/>
