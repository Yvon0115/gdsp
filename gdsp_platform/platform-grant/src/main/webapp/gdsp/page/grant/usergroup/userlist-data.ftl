<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除关联用户" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/usergroup/deleteGroupUser.d?id=${row.id}",{"dataloader":"#userListContent"},{"confirm":"确认删除关联用户‘${row.userVO.username?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["账号","姓名","手机","邮箱","机构名",""] keys=["userVO.account","userVO.username","userVO.mobile","userVO.email","userVO.orgname",op] data=userList.content checkbox=true/>
<@c.PageData page=userList />
