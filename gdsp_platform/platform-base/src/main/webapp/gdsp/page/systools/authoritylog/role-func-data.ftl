<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","角色名","拥有功能"] keys=["\t","rolename","funname"] data=RoleFuncPages?default({"content":[]}).content />
<@c.PageData page=RoleFuncPages?default("") />


