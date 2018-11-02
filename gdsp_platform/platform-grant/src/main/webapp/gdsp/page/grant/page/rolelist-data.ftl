<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="删除关联角色" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/page/deleteRoles.d?id=${row.id}",{"dataloader":"#roleListContent"},{"confirm":"确认删除关联角色‘${row.roleVO.rolename?if_exists}’？"})]>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["角色名","机构名称",""] keys=["roleVO.rolename","roleVO.orgname",op] ellipsis={"roleVO.rolename":"140px","roleVO.orgname":"220px"} data=pageRoles?default({"content":[]}).content checkbox=true/>
<@c.PageData page=pageRoles?default("")/>
