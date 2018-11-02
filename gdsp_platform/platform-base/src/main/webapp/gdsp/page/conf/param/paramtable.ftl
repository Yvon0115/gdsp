<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
		<li><@c.Link title="修改参数值" icon="glyphicon glyphicon-edit" action=[c.opendlg("#editParamDlg","${ContextPath}/conf/param/editParamValue.d?id=${row.id}","440","660",true)]>修改参数</@c.Link></li>
		<li><@c.Link title="恢复默认" icon="glyphicon glyphicon-refresh" action=[c.rpc("${ContextPath}/conf/param/restoreDefault.d?id=${row.id}",{"dataloader":"#paramContent"},{"confirm":"确认恢复‘${row.parname?if_exists}’默认值？"})]>恢复默认</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>

<@c.SimpleTable striped=false titles=["参数编码","参数名称","参数类型","取值范围","默认值","参数值","使用说明",""] keys=["parcode","parname","valuetypestr","valuerange","defaultvaluestr","parvaluestr","memo",op]  ellipsis={"parcode":"150px","parname":"200px","parname":"200px","memo":"200px"} data=page.content checkbox=false/>
<@c.PageData page=page />
