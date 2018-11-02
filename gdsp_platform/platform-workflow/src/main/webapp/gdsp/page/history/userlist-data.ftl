<#import "/gdsp/tags/castle.ftl" as c>

<@c.SimpleTable striped=true titles=["账号","姓名"] keys=["account","username"] data=users.content checkbox=true/>
<@c.PageData page=users />
