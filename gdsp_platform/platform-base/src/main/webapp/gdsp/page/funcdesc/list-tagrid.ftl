<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
		<!--<@c.Link title="删除" icon="glyphicon glyphicon-remove"  action=[c.rpc("${ContextPath}/portal/functionDec/delete.d?id=${row.id}",{"dataloader":"#functionContent1"},{"confirm":"确认删除‘${row.name?if_exists}’？"})]>删除</@c.Link>-->
		<@c.Link title="删除" icon="glyphicon glyphicon-remove"  click="deleteImage('${row.id}','${row.name}','${row.menuid}')">删除</@c.Link>
</@c.TableLinks>
</#noparse>
	</#assign>
	<@c.SimpleTable striped=false titles=["图片名称","图片描述",""] keys=["name","memo",op] data=funcDecVO/>

