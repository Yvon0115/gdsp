<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","用户名","账号","所属机构","拥有功能"] keys=["\t","username","account","orgname","funname"]  data=UserFuncPages?default({"content":[]}).content />
<@c.PageData page=UserFuncPages?default("") />


