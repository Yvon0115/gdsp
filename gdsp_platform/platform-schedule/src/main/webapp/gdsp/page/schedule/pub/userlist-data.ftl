<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<@c.Link title="删除用户" icon="glyphicon glyphicon-remove" click="deleteUserFromReceivers('${row.id}','${row.username}')">删除</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable id="receiverUserTable" striped=false titles=["账号","姓名","机构","手机","邮箱",""] keys=["account","username","orgname","mobile","email",op] ellipsis={"account":"100px","username":"84px","email":"100px","orgname":"140px"} data=users checkbox=true/>

