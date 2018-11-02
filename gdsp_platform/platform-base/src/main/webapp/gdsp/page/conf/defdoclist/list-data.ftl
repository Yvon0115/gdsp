<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
	<@c.TableOperate>
	 <li> <@c.Link title="修改" icon="glyphicon glyphicon-edit"   action=[c.opentab("#detailPanel","${ContextPath}/conf/defdoclist/toEdit.d?id=${row.id}")]>修改</@c.Link> </li>
	 <li><@c.Link title="维护"  icon="glyphicon glyphicon-th-list"  action=[c.opentab("#detailPanel","${ContextPath}/conf/defdoc/dlist.d?type_id=${row.id}")]>维护</@c.Link></li>
	<#--  <li><@c.Link  title="删除" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/conf/defdoclist/delete.d?id=${row.id}",{"dataloader":"#doclistContent"},{"confirm":"确认删除记录‘${row.type_name?if_exists}’？"})]>删除</@c.Link></li>-->
	
  </@c.TableOperate>
  </#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["类型名称","类型编码","类型描述",""] keys=["type_name","type_code","type_desc",op] 
ellipsis={"type_name":"150px","type_code":"100px","type_desc":"150px"}
data=defDocListVO.content checkbox=true/>
<@c.PageData page=defDocListVO />