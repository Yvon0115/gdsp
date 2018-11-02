<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/indexanddim/dim/editDim.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/indexanddim/dim/deleteDim.d?id=${row.id}",{"dataloader":"#dimsContent"},{"confirm":"确认删除维度‘${row.dimname?if_exists}’？"})] >删除</@c.Link></li>	
</@c.TableOperate>
</#noparse>
</#assign>
<#assign wz>#<#noparse>
<@c.TableLinks>
<@c.Link title="维值" class="pull-left" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/indexanddim/dim/dimValueTree.d?dimId=${row.id}")]>维值</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["维度名称","维度字段名称","维度说明","维度类型","查看维值",""] keys=["dimname","dimfieldname","dimmemo","dimtype",wz,op]  data=dims.content checkbox=true/>
<@c.PageData page=dims />