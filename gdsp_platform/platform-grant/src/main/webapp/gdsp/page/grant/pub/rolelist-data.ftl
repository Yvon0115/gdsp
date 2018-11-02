<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["角色名称","机构名称","描述"] keys=["rolename","orgname","memo"] 
ellipsis={"orgname":"220px","rolename":"140px"}
data=roles.content checkbox=true/>
<@c.PageData page=roles />
