<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable id="receiverAddUser" striped=false checkbox=true
		titles=["账号","姓名","机构","手机","邮箱"] 
		keys=["account","username","orgname","mobile","email"] 
		ellipsis={"account":"100px","username":"84px","email":"100px","orgname":"140px"}  
		data=users />
