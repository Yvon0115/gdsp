<#--单据注册列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
	<@c.Link title="编辑已注册的单据" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/workflow/formdef/edit.d?id=${row.id}")]>编辑</@c.Link>
	<@c.Link title="删除此注册的单据" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/workflow/formdef/delete.d?id=${row.id}",{"dataloader":"#formDefContent"},{"confirm":"确认删除‘${row.formName?if_exists}’这个注册的单据？"})]>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["编码","名称","URL",""] keys=["formCode","formName","formURL",op] data=formDefs?if_exists.content?default([]) checkbox=true/>
<@c.PageData page=formDefs?default("") />
