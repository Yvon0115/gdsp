<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","用户名","账号","所属机构","拥有角色"] keys=["\t","username","account","orgname","rolename"]  data=UserRolePages?default({"content":[]}).content />
<@c.PageData page=UserRolePages?default("") />


