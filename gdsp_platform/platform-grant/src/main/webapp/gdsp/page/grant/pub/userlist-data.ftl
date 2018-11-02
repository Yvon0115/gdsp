<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTable striped=false titles=["账号","姓名","手机","邮箱","机构名"] keys=["account","username","mobile","email","orgname"] data=users.content checkbox=true/>
	<@c.PageData page=users?default("") />

