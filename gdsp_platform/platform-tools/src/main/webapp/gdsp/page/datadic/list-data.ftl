<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
	<@c.TableOperate>
	 <li> <@c.Link title="修改" icon="glyphicon glyphicon-edit"  action=[c.opentab("#detail","${ContextPath}/conf/datadic/editDim.d?id=${row.id}")] >修改</@c.Link> </li>
	<li><@c.Link  title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/conf/datadic/deleteDim.d?id=${row.id}",{"dataloader":"#diclistContent"},{"confirm":"确认删除记录‘${row.dic_name?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="维护"  icon="glyphicon glyphicon-th-list"  action=[c.opentab("#detail","${ContextPath}/conf/datadic/valueList.d?pk_dicId=${row.id}")]>维护</@c.Link></li>

  </@c.TableOperate>
  </#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["类型名称","类型编码","类型描述",""] keys=["dic_name","dic_code","dic_desc",op] 
ellipsis={"dic_name":"180px","dic_code":"140px","dic_desc":"200px"}
data=dataDicVO.content checkbox=true/>
<@c.PageData page=dataDicVO />