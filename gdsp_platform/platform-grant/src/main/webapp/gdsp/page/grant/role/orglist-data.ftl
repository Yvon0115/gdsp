<#import "/gdsp/tags/castle.ftl" as c>
<#-- 已弃用的页面 -->
<#assign op>!<#noparse>
<@c.Link title="删除关联机构" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/role/deleteOrgOnRoles.d?id=${row.id}",{"dataloader":"#roleListContent2"},{"confirm":"确认删除关联机构‘${row.orgVO.orgname?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["机构编码","机构名称","上级机构编码","描述",""] keys=["orgVO.orgcode","orgVO.orgname","orgVO.pk_fatherorg","orgVO.memo",op] data=roleOrgs.content checkbox=true/>
<@c.PageData page=roleOrgs />


