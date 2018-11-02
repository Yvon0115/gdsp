<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","角色名","数据维度","数据维度值"] keys=["\t","rolename","dic_name","dimvl_name"]  data=RoleDataLimitPages?default({"content":[]}).content />
<@c.PageData page=RoleDataLimitPages?default("") />


