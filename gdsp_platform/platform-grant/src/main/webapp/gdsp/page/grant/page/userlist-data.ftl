<#--<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除关联用户" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/page/deleteUsers.d?id=${row.id}",{"dataloader":"#userListContent"},{"confirm":"确认删除关联用户‘${row.userVO.username?if_exists}’？"})]>删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["账号","姓名","手机","邮箱","机构名",""] keys=["userVO.account","userVO.username","userVO.mobile","userVO.email","userVO.orgname",op] ellipsis={"userVO.account":"100px","userVO.username":"84px","userVO.email":"100px","userVO.orgname":"140px"} data=pageUsers?default({"content":[]}).content checkbox=true/>
<@c.PageData page=pageUsers?default("")/>-->
